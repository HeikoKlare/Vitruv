/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.impl;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.impl.UpdateMultiValuedFeatureEChangeImpl;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.ListPackage;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.UpdateSingleListEntryEChange;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Update Single List Entry EChange</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.impl.UpdateSingleListEntryEChangeImpl#getIndex <em>Index</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class UpdateSingleListEntryEChangeImpl<A extends EObject, F extends EStructuralFeature> extends UpdateMultiValuedFeatureEChangeImpl<A, F> implements UpdateSingleListEntryEChange<A, F> {
    /**
     * The default value of the '{@link #getIndex() <em>Index</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getIndex()
     * @generated
     * @ordered
     */
    protected static final int INDEX_EDEFAULT = 0;

    /**
     * The cached value of the '{@link #getIndex() <em>Index</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getIndex()
     * @generated
     * @ordered
     */
    protected int index = INDEX_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected UpdateSingleListEntryEChangeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ListPackage.Literals.UPDATE_SINGLE_LIST_ENTRY_ECHANGE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public int getIndex() {
        return index;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setIndex(int newIndex) {
        int oldIndex = index;
        index = newIndex;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ListPackage.UPDATE_SINGLE_LIST_ENTRY_ECHANGE__INDEX, oldIndex, index));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case ListPackage.UPDATE_SINGLE_LIST_ENTRY_ECHANGE__INDEX:
                return getIndex();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case ListPackage.UPDATE_SINGLE_LIST_ENTRY_ECHANGE__INDEX:
                setIndex((Integer)newValue);
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
    public void eUnset(int featureID) {
        switch (featureID) {
            case ListPackage.UPDATE_SINGLE_LIST_ENTRY_ECHANGE__INDEX:
                setIndex(INDEX_EDEFAULT);
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
    public boolean eIsSet(int featureID) {
        switch (featureID) {
            case ListPackage.UPDATE_SINGLE_LIST_ENTRY_ECHANGE__INDEX:
                return index != INDEX_EDEFAULT;
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String toString() {
        if (eIsProxy()) return super.toString();

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (index: ");
        result.append(index);
        result.append(')');
        return result.toString();
    }

} //UpdateSingleListEntryEChangeImpl
