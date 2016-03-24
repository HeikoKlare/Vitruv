package edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.reference

import edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.ChangeDescription2ChangeTransformationTest
import org.junit.Test
import static extension edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.util.ChangeAssertHelper.*
import org.eclipse.emf.ecore.EReference
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.PermuteEReferences

class ChangeDescription2PermuteEReferenceTest extends ChangeDescription2ChangeTransformationTest{
	
	@Test
	def public void testPermuteContainmentEReference(){
		//prepare
		createAndAddNonRootToContainment(false)
		createAndAddNonRootToContainment(true)
		
		//test permute
		this.rootElement.multiValuedNonContainmentEReference.add(0, this.rootElement.multiValuedContainmentEReference.remove(1))
		
		val changes = getChanges
		val expectedList = #[1, 0]
		changes.assertPermuteListTest(this.rootElement, expectedList, MULTI_VALUED_CONTAINMENT_E_REFERENCE_NAME, PermuteEReferences)
	}
	
	@Test
	def public void testPermuteManyContainmentEReference(){
		//prepare
		createAndAddNonRootToContainment(false)
		createAndAddNonRootToContainment(false)
		createAndAddNonRootToContainment(true)
		
		//test permute
		this.rootElement.multiValuedNonContainmentEReference.add(0, this.rootElement.multiValuedContainmentEReference.remove(2))
		
		val changes = getChanges
		val expectedList = #[2, 1, 0]
		changes.assertPermuteListTest(this.rootElement, expectedList, MULTI_VALUED_CONTAINMENT_E_REFERENCE_NAME, PermuteEReferences)
	}
	
	@Test
	def public void testPermuteNonContainmentEReference(){
		//prepare
		val nonRoot = createAndAddNonRootToContainment(false)
		val nonRoot2 = createAndAddNonRootToContainment(false)
		this.rootElement.multiValuedNonContainmentEReference.addAll(nonRoot, nonRoot2)
		startRecording
		
		//test permute
		this.rootElement.multiValuedNonContainmentEReference.add(0, this.rootElement.multiValuedNonContainmentEReference.remove(1))
		
		val changes = getChanges
		val expectedList = #[1, 0]
		changes.assertPermuteListTest(this.rootElement, expectedList, MULTI_VALUED_NON_CONTAINMENT_E_REFERENCE_NAME, PermuteEReferences)
	}
	
	@Test
	def public void testPermuteManyNonContainmentEReference(){
		//prepare
		val nonRoot = createAndAddNonRootToContainment(false)
		val nonRoot2 = createAndAddNonRootToContainment(false)
		val nonRoot3 = createAndAddNonRootToContainment(false)
		this.rootElement.multiValuedNonContainmentEReference.addAll(nonRoot, nonRoot2, nonRoot3)
		startRecording
		
		//test permute
		this.rootElement.multiValuedNonContainmentEReference.add(0, this.rootElement.multiValuedNonContainmentEReference.remove(2))
		
		val changes = getChanges
		val expectedList = #[2, 1, 0]
		changes.assertPermuteListTest(this.rootElement, expectedList, MULTI_VALUED_NON_CONTAINMENT_E_REFERENCE_NAME, PermuteEReferences)
	}
}