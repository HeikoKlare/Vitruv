package edu.kit.ipd.sdq.vitruvius.dsls.response.jvmmodel.classgenerators

import org.eclipse.xtext.common.types.JvmGenericType
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.common.types.JvmVisibility
import org.eclipse.xtext.common.types.JvmOperation
import edu.kit.ipd.sdq.vitruvius.dsls.response.environment.SimpleTextXBlockExpression
import java.io.IOException
import org.eclipse.xtend2.lib.StringConcatenationClient
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ExecutionCodeBlock
import org.eclipse.xtext.common.types.JvmFormalParameter
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.CollectionBridge
import static extension edu.kit.ipd.sdq.vitruvius.dsls.response.helper.ResponseLanguageHelper.*;
import org.eclipse.xtext.common.types.JvmMember
import org.eclipse.xtext.common.types.JvmConstructor
import static edu.kit.ipd.sdq.vitruvius.dsls.response.helper.ResponseLanguageConstants.*;
import edu.kit.ipd.sdq.vitruvius.dsls.response.helper.ResponseClassNamesGenerator.ClassNameGenerator
import static extension edu.kit.ipd.sdq.vitruvius.dsls.response.helper.ResponseClassNamesGenerator.*;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ExistingElementReference
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.CreateCorrespondence
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.Taggable
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.Routine
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.RetrieveModelElement
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.CreateElement
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.DeleteElement
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.Matching

abstract class RoutineClassGenerator extends ClassGenerator {
	protected final Routine routine;
	protected final boolean hasExecutionBlock;
	protected extension ResponseElementsCompletionChecker _completionChecker;
	protected final Iterable<RetrieveModelElement> retrievedElements;
	protected final Iterable<CreateElement> createElements;
	protected final Iterable<DeleteElement> deleteElements;
	private final String routineUserExecutionQualifiedClassName;
	private final ClassNameGenerator routineClassNameGenerator;
	private final ClassNameGenerator routinesFacadeClassNameGenerator;
	private int elementMethodCounter;
	private int tagMethodCounter;
	
	public new(Routine routine, TypesBuilderExtensionProvider typesBuilderExtensionProvider) {
		super(typesBuilderExtensionProvider);
		this.routine = routine;
		this.hasExecutionBlock = routine.effect.codeBlock != null;
		this._completionChecker = new ResponseElementsCompletionChecker();
		this.retrievedElements = routine.matching?.retrievedElements?.filter[complete]?:newArrayList();
		this.createElements = routine.effect.elementCreation;
		this.deleteElements = routine.effect.elementDeletion;
		this.routineClassNameGenerator = routine.routineClassNameGenerator;
		this.routinesFacadeClassNameGenerator = routine.responsesSegment.routinesFacadeClassNameGenerator;
		this.routineUserExecutionQualifiedClassName = routineClassNameGenerator.qualifiedName + "." + EFFECT_USER_EXECUTION_SIMPLE_NAME;
		this.elementMethodCounter = 0;
		this.tagMethodCounter = 0;
	}
	
	protected abstract def Iterable<JvmFormalParameter> generateInputParameters(EObject sourceObject);
	
	protected def Iterable<JvmFormalParameter> generateInputAndRetrievedElementsParameters(EObject sourceObject) {
		return generateInputParameters(sourceObject) + retrievedElements.map[if (it.element != null) sourceObject.generateModelElementParameter(it.element) else null].filterNull;
	}
	
	protected def Iterable<JvmFormalParameter> generateAccessibleElementsParameters(EObject sourceObject) {
		return generateInputAndRetrievedElementsParameters(sourceObject) + createElements.map[sourceObject.generateModelElementParameter(it.element)];
	}
	
	protected def Iterable<String> getInputAndRetrievedElementsParameterNames() {
		return inputParameterNames + retrievedElements.map[it.element.name].filterNull;
	}
	
	protected def Iterable<String> getAccessibleElementsParameterNames() {
		return inputAndRetrievedElementsParameterNames + createElements.map[it.element.name];
	}
	
	protected abstract def Iterable<String> getInputParameterNames();
	
	protected def getParameterCallListWithModelInputAndAccesibleElements(String... parameterStrings) {
		getParameterCallList(accessibleElementsParameterNames + parameterStrings);
	}
	
	protected def getParameterCallListWithModelInputAndRetrievedElements(String... parameterStrings) {
		getParameterCallList(inputAndRetrievedElementsParameterNames + parameterStrings);
	}
	
	protected def getParameterCallListWithModelInput(String... parameterStrings) {
		getParameterCallList(inputParameterNames + parameterStrings);
	}
	
	protected def getParameterCallList(String... parameterStrings) '''
		«FOR parameterName : parameterStrings SEPARATOR ', '»«parameterName»«ENDFOR»'''
		
	public override JvmGenericType generateClass() {
		if (routine == null) {
			return null;
		}
		
		generateMethodExecuteEffect();
		generateMethodAllParametersSet();
		routine.toClass(routineClassNameGenerator.qualifiedName) [
			visibility = JvmVisibility.PUBLIC;
			superTypes += typeRef(AbstractEffectRealization);
			members += generateConstructor();
			members += generateInputFieldsAndSetterMethods();
			members += generatedMethods;
			members += generateEffectUserExecutionClass();
		];
	}
	
	private def JvmGenericType generateEffectUserExecutionClass() {
		return routine.toClass(routineUserExecutionQualifiedClassName) [
			visibility = JvmVisibility.PRIVATE;
			static = true;
			superTypes += typeRef(AbstractEffectRealization.UserExecution);
			val routinesFacadeField = toField(EFFECT_FACADE_FIELD_NAME,  typeRef(routinesFacadeClassNameGenerator.qualifiedName)) [
					annotations += annotationRef(Extension);
				]
			members += #[routinesFacadeField];
			members += toConstructor() [
				val responseExecutionStateParameter = generateResponseExecutionStateParameter();
				val calledByParameter = generateParameter("calledBy", typeRef(CallHierarchyHaving));
				parameters += responseExecutionStateParameter;
				parameters += calledByParameter;
				body = '''
					super(«responseExecutionStateParameter.name»);
					this.«routinesFacadeField.simpleName» = new «typeRef(routinesFacadeClassNameGenerator.qualifiedName)»(«responseExecutionStateParameter.name», «calledByParameter.name»);
					'''
			]
			if (hasExecutionBlock) {
				members += generateMethodExecuteUserOperations(routine.effect.codeBlock, retrievedElements);
			}
		]
	}
	
	protected def generateMethodExecuteUserOperations(ExecutionCodeBlock executionBlock, RetrieveModelElement... retrieveElements) {
		if (!hasExecutionBlock) {
			return null;
		}
		
		val methodName = EFFECT_USER_EXECUTION_EXECUTE_METHOD_NAME;
		return executionBlock.toMethod(methodName, typeRef(Void.TYPE)) [
			visibility = JvmVisibility.PRIVATE;
			parameters += routine.generateInputParameters;
			parameters += retrieveElements.map[routine.generateModelElementParameter(element)];
			parameters += createElements.map[routine.generateModelElementParameter(it.element)];
			val code = executionBlock.code;
			if (code instanceof SimpleTextXBlockExpression) {
				body = code.text;
			} else {
				body = code;
			}
		];	
		
	}	
	
	protected def void generateMethodAllParametersSet() {
		routine.getOrGenerateMethod("allParametersSet", typeRef(Boolean.TYPE)) [
			visibility = JvmVisibility.PUBLIC
			body = '''
				return «FOR inputParameter : inputParameterNames SEPARATOR '&&'
					»is«inputParameter.toFirstUpper»Set«ENDFOR»;'''		
		]
	}
	
	protected def Iterable<JvmMember> generateInputFieldsAndSetterMethods() {
		val inputParameters = routine.generateInputParameters()
		return inputParameters.map[toField(name, parameterType)]
			+ inputParameters.map[toField("is" + name.toFirstUpper + "Set", typeRef(Boolean.TYPE))]
			+ inputParameters.map[param | param.toMethod("set" + param.name.toFirstUpper, typeRef(Void.TYPE)) [
				visibility = JvmVisibility.PUBLIC;
				parameters += param;
				body = '''
					this.«param.name» = «param.name»;
					this.is«param.name.toFirstUpper»Set = true;''';
			]]
	}
	
	protected def JvmConstructor generateConstructor(JvmGenericType clazz) {
		return clazz.toConstructor [
			visibility = JvmVisibility.PUBLIC;
			val executionStateParameter = generateResponseExecutionStateParameter();
			val calledByParameter = generateParameter("calledBy", typeRef(CallHierarchyHaving));
			parameters += executionStateParameter;
			parameters += calledByParameter;
			body = '''super(«executionStateParameter.name», «calledByParameter.name»);'''
		]
	}
	
	var retrieveTagCounter = 0;
	
	protected def JvmOperation generateMethodGetRetrieveTag(Taggable taggable) {
		val methodName = "getRetrieveTag" + retrieveTagCounter++;
		
		return taggable.tag.getOrGenerateMethod(methodName, typeRef(String)) [
			visibility = JvmVisibility.PRIVATE;
			parameters += generateInputParameters();
			body = taggable.tag.code;
		];		
	}
	
	protected def JvmOperation generateMethodGetCreateTag(Taggable taggable) {
		val methodName = "getTag" + tagMethodCounter++;
		
		return taggable.tag.getOrGenerateMethod(methodName, typeRef(String)) [
			visibility = JvmVisibility.PRIVATE;
			parameters += generateAccessibleElementsParameters();
			body = taggable.tag.code;
		];		
	}
	
	protected def generateMethodCorrespondencePrecondition(RetrieveModelElement elementRetrieve) {
		val methodName = "getCorrespondingModelElementsPrecondition" + elementRetrieve.element.name.toFirstUpper;
		return elementRetrieve.precondition.getOrGenerateMethod(methodName, typeRef(Boolean.TYPE)) [
			visibility = JvmVisibility.PRIVATE;
			val inputParameters = generateInputParameters();
			val elementParameter = generateModelElementParameter(elementRetrieve.element);
			parameters += inputParameters;
			parameters += elementParameter;
			body = elementRetrieve.precondition.code;
		];
	}
	
	protected def generateMethodGetCorrespondenceSource(RetrieveModelElement elementRetrieve) {
		val methodName = "getCorrepondenceSource" + elementRetrieve.element.name.toFirstUpper;
		
		return elementRetrieve.correspondenceSource.getOrGenerateMethod(methodName, typeRef(EObject)) [
			visibility = JvmVisibility.PRIVATE;
			parameters += generateInputParameters();
			val correspondenceSourceBlock = elementRetrieve.correspondenceSource?.code;
			if (correspondenceSourceBlock instanceof SimpleTextXBlockExpression) {
				body = correspondenceSourceBlock.text;
			} else {
				body = correspondenceSourceBlock;
			}
		];
	}
	
	protected def generateMethodGetElement(ExistingElementReference reference) {
		val methodName = "getElement" + elementMethodCounter++;
		
		return reference.code.getOrGenerateMethod(methodName, typeRef(EObject)) [
			visibility = JvmVisibility.PRIVATE;
			parameters += generateAccessibleElementsParameters();
			val correspondenceSourceBlock = reference?.code;
			if (correspondenceSourceBlock instanceof SimpleTextXBlockExpression) {
				body = correspondenceSourceBlock.text;
			} else {
				body = correspondenceSourceBlock;
			}
		];
	}
	
	protected def generateMethodMatcherPrecondition(Matching matching) {
		if (matching?.condition == null) {
			return null;
		}
		val methodName = "checkMatcherPrecondition";
		return matching.condition.getOrGenerateMethod(methodName, typeRef(Boolean.TYPE)) [
			visibility = JvmVisibility.PRIVATE;
			parameters += generateInputAndRetrievedElementsParameters();
			body = matching.condition.code;
		];
	}
	
	
	protected def generateMethodExecuteEffect() {
		val methodName = "executeEffect";
		val inputParameters = routine.generateInputParameters();
		val modelElementInitialization = <RetrieveModelElement, StringConcatenationClient>newHashMap();
		CollectionBridge.mapFixed(retrievedElements, 
				[modelElementInitialization.put(it, getInitializationCode(it, getParameterCallListWithModelInput()))]);
		
		val matcherMethodPrecondition = generateMethodMatcherPrecondition(routine.matching);
		
		val elementReferences = <EObject, String>newHashMap();
		CollectionBridge.mapFixed((routine.effect.correspondenceCreation.map[firstElement] + routine.effect.correspondenceCreation.map[secondElement]
			+ routine.effect.correspondenceDeletion.map[firstElement] + routine.effect.correspondenceDeletion.map[secondElement]).filter(ExistingElementReference),
			[elementReferences.put(it, generateMethodGetElement(it).simpleName + '''(«getParameterCallListWithModelInputAndAccesibleElements()»)''')]);
		CollectionBridge.mapFixed(routine.effect.elementDeletion.map[element],
			[elementReferences.put(it, generateMethodGetElement(it).simpleName + '''(«getParameterCallListWithModelInputAndAccesibleElements()»)''')]);
			
		val tagMethodCalls = <CreateCorrespondence, String>newHashMap();
		CollectionBridge.mapFixed(routine.effect.correspondenceCreation,
			[tagMethodCalls.put(it, if (it.tag != null) {generateMethodGetCreateTag(it).simpleName + '''(«getParameterCallListWithModelInputAndAccesibleElements()»)'''} else {'''""'''})]);

		return getOrGenerateMethod(methodName, typeRef(Void.TYPE)) [
			visibility = JvmVisibility.PROTECTED;
			exceptions += typeRef(IOException);
			body = '''
				getLogger().debug("Called routine «routineClassNameGenerator.simpleName» with input:");
				«FOR inputParameter : inputParameters»
					getLogger().debug("   «inputParameter.parameterType.type.simpleName»: " + this.«inputParameter.name»);
				«ENDFOR»
				
				«FOR element : retrievedElements»
					«modelElementInitialization.get(element)»
				«ENDFOR»
				if (isAborted()) {
					return;
				}
				«IF matcherMethodPrecondition != null»
					if (!«matcherMethodPrecondition.simpleName»(«getParameterCallListWithModelInputAndRetrievedElements()»)) {
						return;
					}
				«ENDIF»
				«FOR createElement : createElements»
					«getElementCreationCode(createElement)»
				«ENDFOR»
				«FOR deleteElement : deleteElements»
					markObjectDelete(«elementReferences.get(deleteElement.element)»);
				«ENDFOR»

				«FOR correspondenceCreate : routine.effect.correspondenceCreation»
					addCorrespondenceBetween(«elementReferences.get(correspondenceCreate.firstElement)», «
						elementReferences.get(correspondenceCreate.secondElement)», «tagMethodCalls.get(correspondenceCreate)»);
				«ENDFOR»
				«FOR correspondenceDelete : routine.effect.correspondenceDeletion»
					removeCorrespondenceBetween(«elementReferences.get(correspondenceDelete.firstElement)», «
						elementReferences.get(correspondenceDelete.secondElement)»);
				«ENDFOR»				
				preProcessElements();
				«IF hasExecutionBlock»
					new «routineUserExecutionQualifiedClassName»(getExecutionState(), this).«EFFECT_USER_EXECUTION_EXECUTE_METHOD_NAME»(
						«getParameterCallListWithModelInputAndAccesibleElements()»);
				«ENDIF»
				postProcessElements();
				'''
		];	
	}
		
	private def StringConcatenationClient getCorrespondenceSourceSupplier(RetrieveModelElement retrieveElement) {
		val correspondenceSourceMethod = generateMethodGetCorrespondenceSource(retrieveElement);
		return '''() -> «correspondenceSourceMethod.simpleName»(«getParameterCallListWithModelInput()»)'''	
	}
	
	private def StringConcatenationClient getTagSupplier(RetrieveModelElement retrieveElement) {
		if (retrieveElement.tag != null) {
			val tagMethod = generateMethodGetRetrieveTag(retrieveElement);
			return '''() -> «tagMethod.simpleName»(«getParameterCallListWithModelInput()»)'''
		} else {
			return '''() -> null'''
		}
	}
	
	private def StringConcatenationClient getPreconditionChecker(RetrieveModelElement retrieveElement) {
		val affectedElementClass = retrieveElement.element.element.javaClass;
		if (retrieveElement.precondition == null) {
			return '''(«affectedElementClass» _element) -> true''';
		}
		val preconditionMethod = generateMethodCorrespondencePrecondition(retrieveElement);
		return '''(«affectedElementClass» _element) -> «preconditionMethod.simpleName»(«getParameterCallListWithModelInput("_element")»)'''	
	}
	
	private def StringConcatenationClient getInitializationCode(RetrieveModelElement retrieveElement, CharSequence parameterCallList) {
		val affectedElementClass = retrieveElement.element.element;
		val correspondingElementPreconditionChecker = getPreconditionChecker(retrieveElement);
		val correspondenceSourceSupplier = getCorrespondenceSourceSupplier(retrieveElement);
		val tagSupplier = getTagSupplier(retrieveElement);
		return '''
			«IF !retrieveElement.element.name.nullOrEmpty»«affectedElementClass.javaClass» «retrieveElement.element.name» = «
			ENDIF»initializeRetrieveElementState(
				«correspondenceSourceSupplier», // correspondence source supplier
				«correspondingElementPreconditionChecker», // correspondence precondition checker
				«tagSupplier», // tag supplier
				«affectedElementClass.javaClass».class,
				«retrieveElement.optional», «retrieveElement.required», «retrieveElement.abscence»);
		'''	
	}
	
	private def StringConcatenationClient getElementCreationCode(CreateElement elementCreate) {
		val affectedElementClass = elementCreate.element.element;
		val createdClassFactory = affectedElementClass.EPackage.EFactoryInstance.class;
		return '''
			«affectedElementClass.instanceClass» «elementCreate.element.name» = «createdClassFactory».eINSTANCE.create«affectedElementClass.name»();
			initializeCreateElementState(«elementCreate.element.name»);'''
	}
	
}