/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.mir.mIR;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Feature Mapping</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.FeatureMapping#getMappedElements <em>Mapped Elements</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.FeatureMapping#getWhens <em>Whens</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.FeatureMapping#getWheres <em>Wheres</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.FeatureMapping#getWiths <em>Withs</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRPackage#getFeatureMapping()
 * @model
 * @generated
 */
public interface FeatureMapping extends EObject
{
  /**
   * Returns the value of the '<em><b>Mapped Elements</b></em>' containment reference list.
   * The list contents are of type {@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.TypedElement}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Mapped Elements</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Mapped Elements</em>' containment reference list.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRPackage#getFeatureMapping_MappedElements()
   * @model containment="true"
   * @generated
   */
  EList<TypedElement> getMappedElements();

  /**
   * Returns the value of the '<em><b>Whens</b></em>' containment reference list.
   * The list contents are of type {@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.When}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Whens</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Whens</em>' containment reference list.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRPackage#getFeatureMapping_Whens()
   * @model containment="true"
   * @generated
   */
  EList<When> getWhens();

  /**
   * Returns the value of the '<em><b>Wheres</b></em>' containment reference list.
   * The list contents are of type {@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Where}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Wheres</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Wheres</em>' containment reference list.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRPackage#getFeatureMapping_Wheres()
   * @model containment="true"
   * @generated
   */
  EList<Where> getWheres();

  /**
   * Returns the value of the '<em><b>Withs</b></em>' containment reference list.
   * The list contents are of type {@link edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.FeatureMapping}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Withs</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Withs</em>' containment reference list.
   * @see edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRPackage#getFeatureMapping_Withs()
   * @model containment="true"
   * @generated
   */
  EList<FeatureMapping> getWiths();

} // FeatureMapping
