/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Additive EChange</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.ChangePackage#getAdditiveEChange()
 * @model abstract="true" TBounds="org.eclipse.emf.ecore.EJavaObject"
 * @generated
 */
public interface AdditiveEChange<T extends Object> extends AtomicEChange {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @model kind="operation" required="true"
     * @generated
     */
    T getNewValue();

} // AdditiveEChange
