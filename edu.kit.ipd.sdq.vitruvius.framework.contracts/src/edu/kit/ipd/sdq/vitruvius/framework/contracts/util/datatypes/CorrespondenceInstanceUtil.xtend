package edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes

import com.google.common.collect.Sets
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.Correspondence
import java.util.Set
import org.eclipse.emf.ecore.EObject

import static extension edu.kit.ipd.sdq.vitruvius.framework.util.bridges.CollectionBridge.*

class CorrespondenceInstanceUtil {
	private new (){
	}
	
	/**
     * Returns all corresponding objects for the specified object and an empty set if the object has
     * no correspondences. Should never return {@link null}.
     *
     * @param eObject
     *            the object for which corresponding objects are to be returned
     * @return all corresponding objects for the specified object and an empty set if the object has
     *         no correspondences.
     */
    //FIXME ML is this method correct? Is there some cool Xtend feature which makes this method shorter? 
	def public static Set<EObject> getCorrespondingEObjects(CorrespondenceInstance ci, EObject eObject) {
		val correspondingEObjects = ci.getCorrespondingEObjects(eObject.toList)
		val eObjects = Sets.newHashSet
		correspondingEObjects.forEach(list|eObjects.addAll(list))
		return eObjects
	}
	
	/**
     * Returns the M2NCorrespondence for the given eObject
     * {@link RuntimeException} if there is no such correspondence. Note that a has to be an element
     * of metamodel a and b an instance of metamodel b.
     *
     * @param a
     * @param b
     * @return
     */
	def public static Correspondence claimUniqueCorrespondence(CorrespondenceInstance ci, EObject eObject) {
		return ci.getCorrespondences(eObject.toList).claimOne
	}
	
	 /**
     * Returns the correspondences for the specified object and throws a
     * {@link RuntimeException} if no correspondence exists.
     *
     * @param eObject
     *            the object for which correspondences are to be returned
     * @return the correspondences for the specified object
     */
	def public static Set<Correspondence> claimCorrespondences(CorrespondenceInstance ci, EObject eObject) {
		return ci.getCorrespondences(eObject.toList).claimNotEmpty as Set<Correspondence>
	}
	
	def public static Correspondence createAndAddCorrespondence(CorrespondenceInstance ci, EObject a, EObject b) {
		return ci.createAndAddCorrespondence(a.toList,b.toList)
	}

	/**
     * Returns the corresponding object of the specified type for the specified object if there is
     * exactly one corresponding object of this type and throws a {@link RuntimeException}
     * otherwise.
     *
     * @param eObject
     *            the object for which the corresponding object is to be returned
     * @param type
     *            the class of which an instance is to be returned
     * @return the corresponding object of the specified type for the specified object if there is
     *         exactly one corresponding object of this type
     */
	def public static <T> Set<T> getCorrespondingEObjectsByType(CorrespondenceInstance ci, EObject eObject, Class<T> type) {
		//return getCorrespondingEObjects(ci, eObject).filter[eObj | type.isInstance(eObj)].toSet
		val Set<T> retSet = Sets.newHashSet
		getCorrespondingEObjects(ci, eObject).forEach[if(type.isInstance(it)){retSet.add(it as T)}]
		return retSet
	}
	
	
	/**
     * Returns all eObjects that have some correspondence and are an instance of the given class.
     *
     * @param type
     *            the class for which instances should be returned
     * @return a set containing all eObjects of the given type that have a correspondence
     */
	def public static <T> Set<T> getAllEObjectsOfTypeInCorrespondences(CorrespondenceInstance ci, Class<T> type) {
		val tuidSet = ci.allCorrespondencesWithoutDependencies.map[(it.ATUIDs + it.BTUIDs)].flatten.toSet
		val eObjectSet = Sets.newHashSet
		tuidSet.forEach[try{eObjectSet.add(ci.resolveEObjectFromTUID(it))}catch(RuntimeException e){ }]
		return eObjectSet.filter(type).toSet
		//return ci.allCorrespondencesWithoutDependencies.map[(it.^as + it.bs).filter(type)].flatten.toSet
	}
	
	def public static Set<Correspondence> getCorrespondencesBetweenEObjects(CorrespondenceInstance ci, Set<EObject> aS, Set<EObject> bS) {
		val correspondencesThatInvolveAs = ci.getCorrespondencesThatInvolveAtLeast(aS)
		val atuids = aS.mapFixed[ci.calculateTUIDFromEObject(it)]
		val btuids = bS.mapFixed[ci.calculateTUIDFromEObject(it)]
		val correspondencesBetweenEObjects = correspondencesThatInvolveAs.filter[(it.ATUIDs.containsAll(atuids) && it.BTUIDs.containsAll(btuids)) || (it.ATUIDs.containsAll(btuids) && it.BTUIDs.containsAll(atuids))]
		return correspondencesBetweenEObjects.toSet  
	}
	
}