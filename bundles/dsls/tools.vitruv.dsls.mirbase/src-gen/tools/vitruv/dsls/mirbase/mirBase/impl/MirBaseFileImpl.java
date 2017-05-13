/**
 * generated by Xtext 2.12.0-SNAPSHOT
 */
package tools.vitruv.dsls.mirbase.mirBase.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import tools.vitruv.dsls.mirbase.mirBase.MetamodelImport;
import tools.vitruv.dsls.mirbase.mirBase.MirBaseFile;
import tools.vitruv.dsls.mirbase.mirBase.MirBasePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>File</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.dsls.mirbase.mirBase.impl.MirBaseFileImpl#getMetamodelImports <em>Metamodel Imports</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MirBaseFileImpl extends MinimalEObjectImpl.Container implements MirBaseFile
{
  /**
   * The cached value of the '{@link #getMetamodelImports() <em>Metamodel Imports</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMetamodelImports()
   * @generated
   * @ordered
   */
  protected EList<MetamodelImport> metamodelImports;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected MirBaseFileImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass()
  {
    return MirBasePackage.Literals.MIR_BASE_FILE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<MetamodelImport> getMetamodelImports()
  {
    if (metamodelImports == null)
    {
      metamodelImports = new EObjectContainmentEList<MetamodelImport>(MetamodelImport.class, this, MirBasePackage.MIR_BASE_FILE__METAMODEL_IMPORTS);
    }
    return metamodelImports;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
  {
    switch (featureID)
    {
      case MirBasePackage.MIR_BASE_FILE__METAMODEL_IMPORTS:
        return ((InternalEList<?>)getMetamodelImports()).basicRemove(otherEnd, msgs);
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType)
  {
    switch (featureID)
    {
      case MirBasePackage.MIR_BASE_FILE__METAMODEL_IMPORTS:
        return getMetamodelImports();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @SuppressWarnings("unchecked")
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case MirBasePackage.MIR_BASE_FILE__METAMODEL_IMPORTS:
        getMetamodelImports().clear();
        getMetamodelImports().addAll((Collection<? extends MetamodelImport>)newValue);
        return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eUnset(int featureID)
  {
    switch (featureID)
    {
      case MirBasePackage.MIR_BASE_FILE__METAMODEL_IMPORTS:
        getMetamodelImports().clear();
        return;
    }
    super.eUnset(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean eIsSet(int featureID)
  {
    switch (featureID)
    {
      case MirBasePackage.MIR_BASE_FILE__METAMODEL_IMPORTS:
        return metamodelImports != null && !metamodelImports.isEmpty();
    }
    return super.eIsSet(featureID);
  }

} //MirBaseFileImpl
