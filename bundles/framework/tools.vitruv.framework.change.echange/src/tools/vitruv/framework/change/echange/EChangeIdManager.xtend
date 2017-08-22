package tools.vitruv.framework.change.echange

import tools.vitruv.framework.change.echange.eobject.EObjectAddedEChange
import tools.vitruv.framework.change.echange.eobject.EObjectSubtractedEChange
import tools.vitruv.framework.change.echange.eobject.EObjectExistenceEChange
import tools.vitruv.framework.change.echange.feature.FeatureEChange
import tools.vitruv.framework.change.echange.compound.CompoundEChange
import tools.vitruv.framework.change.echange.eobject.DeleteEObject
import tools.vitruv.framework.change.echange.eobject.CreateEObject
import tools.vitruv.framework.change.uuid.UuidGeneratorAndResolver

/**
 * Provides logic for initializing the IDs within changes and for updating
 * the object references in the {@link UuidProviderAndResolver}. 
 */
class EChangeIdManager {
	val UuidGeneratorAndResolver uuidProviderAndResolver;
	val boolean strictMode;

	/**
	 * Initializes the manager with a {@link UuidProviderAndResolver}.
	 * 
	 * @param uuidProviderAndResolver -
	 * 		the {@link UuidProviderAndResolver} to use for ID management
	 * @param strictMode -
	 * 		defines if the manager should run in strict mode, which throws {@link IllegalStateExceptions} 
	 * 		if an element that should already have an ID does no have one. Using non-strict mode can be 
	 * 		necessary if model changes are not recorded from beginning of model creation
	 */
	new(UuidGeneratorAndResolver uuidProviderAndResolver, boolean strictMode) {
		this.uuidProviderAndResolver = uuidProviderAndResolver;
		this.strictMode = strictMode;
	}

	def void setOrGenerateIds(EChange eChange) {
		switch eChange {
			EObjectExistenceEChange<?>,
			FeatureEChange<?,?>:
				setOrGenerateAffectedEObjectId(eChange)
			CompoundEChange:
				eChange.atomicChanges.forEach[setOrGenerateIds]
		}
		switch eChange {
			EObjectSubtractedEChange<?>:
				setOrGenerateOldValueId(eChange)
		}
		switch eChange {
			EObjectAddedEChange<?>:
				setOrGenerateNewValueId(eChange)
		}
	}

	private def void setOrGenerateNewValueId(EObjectAddedEChange<?> addedEChange) {
		if(addedEChange.newValue === null) {
			return;
		}
		if(!uuidProviderAndResolver.hasUuid(addedEChange.newValue)) {
			if(strictMode) {
				throw new IllegalStateException();
			}
			addedEChange.newValueID = uuidProviderAndResolver.registerEObject(addedEChange.newValue);
		} else {
			addedEChange.newValueID = uuidProviderAndResolver.getUuid(addedEChange.newValue);
		}
	}

	private def void setOrGenerateOldValueId(EObjectSubtractedEChange<?> subtractedEChange) {
		if(subtractedEChange.oldValue === null) {
			return;
		}
		subtractedEChange.oldValueID = uuidProviderAndResolver.getOrRegisterUuid(subtractedEChange.oldValue)
	}

	private def dispatch void setOrGenerateAffectedEObjectId(CreateEObject<?> createChange) {
		if(createChange.affectedEObject === null) {
			throw new IllegalStateException();
		}
		createChange.affectedEObjectID = uuidProviderAndResolver.getOrRegisterUuid(createChange.affectedEObject);
	}
	
	private def dispatch void setOrGenerateAffectedEObjectId(DeleteEObject<?> deleteChange) {
		if(deleteChange.affectedEObject === null) {
			throw new IllegalStateException();
		}
		deleteChange.affectedEObjectID = uuidProviderAndResolver.getOrRegisterUuid(deleteChange.affectedEObject);
		deleteChange.consequentialRemoveChanges.forEach[setOrGenerateIds]
	}

	private def dispatch void setOrGenerateAffectedEObjectId(FeatureEChange<?, ?> featureChange) {
		if(featureChange.affectedEObject === null) {
			throw new IllegalStateException();
		}
		if(!uuidProviderAndResolver.hasUuid(featureChange.affectedEObject)) {
			if(strictMode) {
				throw new IllegalStateException();
			}
			featureChange.affectedEObjectID = uuidProviderAndResolver.registerEObject(featureChange.affectedEObject);
		} else {
			featureChange.affectedEObjectID = uuidProviderAndResolver.getUuid(featureChange.affectedEObject);
		}
	}

	def void updateRegisteredObject(EChange eChange) {
		switch eChange {
			EObjectAddedEChange<?>:
				updateNewValueId(eChange)
			EObjectSubtractedEChange<?>:
				updateOldValueId(eChange)
			EObjectExistenceEChange<?>:
				updateAffectedEObjectId(eChange)
			FeatureEChange<?,?>:
				updateAffectedEObjectId(eChange)
			CompoundEChange:
				eChange.atomicChanges.forEach[updateRegisteredObject]
		}
	}

	private def updateNewValueId(EObjectAddedEChange<?> addedEChange) {
		if(addedEChange.newValue === null) {
			return;
		}
		uuidProviderAndResolver.registerEObject(addedEChange.newValueID, addedEChange.newValue);
	}

	private def updateOldValueId(EObjectSubtractedEChange<?> subtractedEChange) {
		if(subtractedEChange.oldValue === null) {
			return;
		}
		uuidProviderAndResolver.registerEObject(subtractedEChange.oldValueID, subtractedEChange.oldValue);
	}

	private def updateAffectedEObjectId(EObjectExistenceEChange<?> existenceChange) {
		if(existenceChange.affectedEObject === null) {
			throw new IllegalStateException();
		}
		uuidProviderAndResolver.registerEObject(existenceChange.affectedEObjectID, existenceChange.affectedEObject);
	}

	private def updateAffectedEObjectId(FeatureEChange<?, ?> featureChange) {
		if(featureChange.affectedEObject === null) {
			throw new IllegalStateException();
		}
		uuidProviderAndResolver.registerEObject(featureChange.affectedEObjectID, featureChange.affectedEObject);
	}
}
