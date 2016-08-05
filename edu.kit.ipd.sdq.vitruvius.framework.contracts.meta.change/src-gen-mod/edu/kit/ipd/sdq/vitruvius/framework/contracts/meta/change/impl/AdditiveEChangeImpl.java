/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AdditiveEChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.ChangePackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Additive EChange</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public abstract class AdditiveEChangeImpl<T extends Object> extends AtomicEChangeImpl implements AdditiveEChange<T> {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected AdditiveEChangeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ChangePackage.Literals.ADDITIVE_ECHANGE;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public T getNewValue() {
        throw new UnsupportedOperationException();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public Object eInvoke(final int operationID, final EList<?> arguments) throws InvocationTargetException {
        switch (operationID) {
        case ChangePackage.ADDITIVE_ECHANGE___GET_NEW_VALUE:
            return getNewValue();
        }
        return super.eInvoke(operationID, arguments);
    }

} // AdditiveEChangeImpl
