package tools.vitruv.framework.vsum.modelsynchronization

import java.util.ArrayList
import java.util.List
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.change.description.CompositeChange
import tools.vitruv.framework.change.description.PropagatedChange
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.change.description.VitruviusChangeFactory
import tools.vitruv.framework.change.interaction.UserInteractionBase
import tools.vitruv.framework.change.processing.ChangePropagationObserver
import tools.vitruv.framework.change.processing.ChangePropagationSpecification
import tools.vitruv.framework.change.processing.ChangePropagationSpecificationProvider
import tools.vitruv.framework.domains.VitruvDomain
import tools.vitruv.framework.domains.repository.VitruvDomainRepository
import tools.vitruv.framework.userinteraction.InternalUserInteractor
import tools.vitruv.framework.userinteraction.UserInteractionFactory
import tools.vitruv.framework.userinteraction.UserInteractionListener
import tools.vitruv.framework.uuid.UuidResolver
import tools.vitruv.framework.vsum.ModelRepository
import tools.vitruv.framework.vsum.repositories.ModelRepositoryImpl

import static com.google.common.base.Preconditions.checkNotNull
import static com.google.common.base.Preconditions.checkState
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*
import tools.vitruv.framework.correspondence.CorrespondenceModel

class ChangePropagator {
	static val logger = Logger.getLogger(ChangePropagator)
	val VitruvDomainRepository domainRepository
	val ModelRepository resourceRepository
	val ChangePropagationSpecificationProvider changePropagationProvider
	val CorrespondenceModel correspondenceModel
	val ModelRepositoryImpl modelRepository
	val InternalUserInteractor userInteractor

	new(ModelRepository resourceRepository, ChangePropagationSpecificationProvider changePropagationProvider,
		VitruvDomainRepository domainRepository, ModelRepositoryImpl modelRepository,
		CorrespondenceModel correspondenceModel, InternalUserInteractor userInteractor) {
		this.resourceRepository = resourceRepository
		this.modelRepository = modelRepository
		this.changePropagationProvider = changePropagationProvider
		this.correspondenceModel = correspondenceModel
		this.domainRepository = domainRepository
		this.userInteractor = userInteractor
	}

	def List<PropagatedChange> propagateChange(VitruviusChange change) {
		change.transactionalChangeSequence.forEach [applySingleChange(it)]
		
		val changedDomain = change.changedDomain
		if (logger.isTraceEnabled) {
			logger.trace('''
				Will now propagate this input change:
					«change»
			''')
		}
		return new ChangePropagation(this, change, changedDomain, null).propagateChanges()
	}

	def private void applySingleChange(TransactionalChange change) {
		resourceRepository.executeOnUuidResolver [ UuidResolver uuidResolver |
			change.resolveBeforeAndApplyForward(uuidResolver)
			// add all affected models to the repository
			change.changedVURIs.forEach [resourceRepository.getModel(it)]
			change.affectedAndReferencedEObjects.forEach [modelRepository.addRootElement(it)]
		]
		modelRepository.cleanupRootElements()
	}
	
	@FinalFieldsConstructor
	private static class ChangePropagation implements ChangePropagationObserver, UserInteractionListener {
		extension val ChangePropagator outer
		val VitruviusChange sourceChange
		val VitruvDomain sourceDomain
		val ChangePropagation previous
		val ChangedResourcesTracker changedResourcesTracker = new ChangedResourcesTracker
		val List<EObject> createdObjects = new ArrayList
		val List<UserInteractionBase> userInteractions = new ArrayList
		
		def private propagateChanges() {
			val result = sourceChange.transactionalChangeSequence.flatMapFixed [propagateSingleChange(it)]
			handleObjectsWithoutResource()
			changedResourcesTracker.markNonSourceResourceAsChanged()
			return result
		}
		
		def private List<PropagatedChange> propagateSingleChange(TransactionalChange change) {
			checkState(!change.affectedEObjects.isNullOrEmpty, 
				"There are no objects affected by this change:%s%s", System.lineSeparator, change)

			val userInteractorChange = installUserInteractorForChange(change)
			changePropagationProvider.forEach [registerObserver(this)]
			userInteractor.registerUserInputListener(this)

			val propagationResultChanges = try {
				changePropagationProvider.getChangePropagationSpecifications(sourceDomain).mapFixed [
					targetDomain -> propagateChangeForChangePropagationSpecification(change, it)
				]
			} finally {
				userInteractor.deregisterUserInputListener(this)
				changePropagationProvider.forEach [deregisterObserver(this)]
				userInteractorChange.close()
			}
			
			if (logger.isDebugEnabled) {
				logger.debug('''Propagated «FOR p : propagationPath SEPARATOR ' -> '»«p»«ENDFOR» -> {«FOR result: propagationResultChanges SEPARATOR ", "»«result.key»«ENDFOR»}''')
			}
			if (logger.isTraceEnabled) {
				logger.trace('''
					Result changes:
						«FOR result : propagationResultChanges»
							«result.key»: «result.value»
						«ENDFOR»
				''')
			}

			change.userInteractions = userInteractions
			val propagatedChange = new PropagatedChange(change,
				VitruviusChangeFactory.instance.createCompositeChange(propagationResultChanges.flatMapFixed [value]))
			val resultingChanges = new ArrayList()
			resultingChanges += propagatedChange
	
			val nextPropagations = propagationResultChanges
				.filter [key.shouldTransitivelyPropagateChanges && value.exists[containsConcreteChange]]
				.mapFixed [
					new ChangePropagation(outer, VitruviusChangeFactory.instance.createCompositeChange(value), key, this)
				]

			for (nextPropagation : nextPropagations) {
				resultingChanges += nextPropagation.propagateChanges()
			}
	
			return resultingChanges
		}
		
		def private propagateChangeForChangePropagationSpecification(
			TransactionalChange change,
			ChangePropagationSpecification propagationSpecification
		) {
			// modelRepository.startRecording
			resourceRepository.startRecording()
			// TODO HK: Clone the changes for each synchronization! Should even be cloned for
			// each consistency repair routines that uses it,
			// or: make them read only, i.e. give them a read-only interface!
			propagationSpecification.propagateChange(change, correspondenceModel, resourceRepository)
			modelRepository.cleanupRootElements()
			val changes = resourceRepository.endRecording() /* + modelRepository.endRecording() */
	
			// Store modification information
			changes.flatMap [affectedEObjects].forEach [changedResourcesTracker.addInvolvedModelResource(eResource)]
			changedResourcesTracker.addSourceResourceOfChange(change)
			
			return changes
		}
		
		def private AutoCloseable installUserInteractorForChange(VitruviusChange change) {
			// retrieve user inputs from past changes, construct a UserInteractor which tries to reuse them:
			val pastUserInputsFromChange = change.userInteractions
	
			if (!pastUserInputsFromChange.nullOrEmpty) {
				userInteractor.replaceUserInteractionResultProvider[ currentProvider |
					UserInteractionFactory.instance.
						createPredefinedInteractionResultProvider(currentProvider, pastUserInputsFromChange)
				]
			} else []
		}
		
		
		def private void handleObjectsWithoutResource() {
			modelRepository.cleanupRootElementsWithoutResource
			// Find created objects without resource
			for (createdObjectWithoutResource : createdObjects.filter[eResource === null]) {
				checkState(!correspondenceModel.hasCorrespondences(List.of(createdObjectWithoutResource)),
					"This object is part of a correspondence but not in any resource: %s", createdObjectWithoutResource)
				logger.warn("Object was created but has no correspondence and is thus lost: " 
					+ createdObjectWithoutResource)
			}
		}
		
		override objectCreated(EObject createdObject) {
			createdObjects += createdObject
			modelRepository.addRootElement(createdObject)
		}
		
		override onUserInteractionReceived(UserInteractionBase interaction) {
			userInteractions += interaction
		}
		
		override toString() '''propagate «FOR p : propagationPath SEPARATOR ' -> '»«p»«ENDFOR»: «sourceChange»'''
		
		def private Iterable<String> getPropagationPath() {
			if (previous === null) List.of("<input change> in " + sourceDomain.toString)
			else previous.propagationPath + List.of(sourceDomain.toString)
		}
	}
	
	def private Iterable<TransactionalChange> getTransactionalChangeSequence(VitruviusChange change) {
		switch (change) {
			case !change.containsConcreteChange: emptyList()
			TransactionalChange: List.of(change)
			CompositeChange<?>: change.changes.flatMap [transactionalChangeSequence]
			default: throw new IllegalStateException("Unexpected change type: " + change.class.simpleName)
		}
	}

	def private VitruvDomain getChangedDomain(VitruviusChange change) {
		val changeDomain = change.changedVURIs.fold(null as VitruvDomain) [changeDomain, changedVuri |
			val resourceDomain = domainRepository.getDomain(changedVuri.fileExtension)
			if (changeDomain === null) {
				resourceDomain
			} else if (resourceDomain === null || resourceDomain == changeDomain) {
				changeDomain
			} else {
				throw new IllegalStateException('''
					This change affects multiple domains («changeDomain» and «resourceDomain»):
						«change»
				''')
			}
		]
		checkState(changeDomain !== null, "Cannot determine the domain of this change:%s%s", System.lineSeparator, change)
		changeDomain
	}
}
