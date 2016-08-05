/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.ChangePackage;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.CompoundEChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.CompoundFactory;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.CompoundPackage;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.ExplicitUnsetEFeature;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.MoveEObject;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.ReplaceInEList;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.FeaturePackage;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.AttributePackage;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.impl.AttributePackageImpl;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.impl.FeaturePackageImpl;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.ListPackage;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.impl.ListPackageImpl;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.ReferencePackage;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.impl.ReferencePackageImpl;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.impl.ChangePackageImpl;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.root.RootPackage;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.root.impl.RootPackageImpl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.ETypeParameter;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class CompoundPackageImpl extends EPackageImpl implements CompoundPackage {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass compoundEChangeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass moveEObjectEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass replaceInEListEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass explicitUnsetEFeatureEClass = null;

    /**
     * Creates an instance of the model <b>Package</b>, registered with
     * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
     * package URI value.
     * <p>Note: the correct way to create the package is via the static
     * factory method {@link #init init()}, which also performs
     * initialization of the package, or returns the registered package,
     * if one already exists.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.emf.ecore.EPackage.Registry
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.CompoundPackage#eNS_URI
     * @see #init()
     * @generated
     */
    private CompoundPackageImpl() {
        super(eNS_URI, CompoundFactory.eINSTANCE);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static boolean isInited = false;

    /**
     * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
     * 
     * <p>This method is used to initialize {@link CompoundPackage#eINSTANCE} when that field is accessed.
     * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #eNS_URI
     * @see #createPackageContents()
     * @see #initializePackageContents()
     * @generated
     */
    public static CompoundPackage init() {
        if (isInited) return (CompoundPackage)EPackage.Registry.INSTANCE.getEPackage(CompoundPackage.eNS_URI);

        // Obtain or create and register package
        CompoundPackageImpl theCompoundPackage = (CompoundPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof CompoundPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new CompoundPackageImpl());

        isInited = true;

        // Obtain or create and register interdependencies
        ChangePackageImpl theChangePackage = (ChangePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ChangePackage.eNS_URI) instanceof ChangePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ChangePackage.eNS_URI) : ChangePackage.eINSTANCE);
        FeaturePackageImpl theFeaturePackage = (FeaturePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(FeaturePackage.eNS_URI) instanceof FeaturePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(FeaturePackage.eNS_URI) : FeaturePackage.eINSTANCE);
        ListPackageImpl theListPackage = (ListPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ListPackage.eNS_URI) instanceof ListPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ListPackage.eNS_URI) : ListPackage.eINSTANCE);
        AttributePackageImpl theAttributePackage = (AttributePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(AttributePackage.eNS_URI) instanceof AttributePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(AttributePackage.eNS_URI) : AttributePackage.eINSTANCE);
        ReferencePackageImpl theReferencePackage = (ReferencePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ReferencePackage.eNS_URI) instanceof ReferencePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ReferencePackage.eNS_URI) : ReferencePackage.eINSTANCE);
        RootPackageImpl theRootPackage = (RootPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(RootPackage.eNS_URI) instanceof RootPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(RootPackage.eNS_URI) : RootPackage.eINSTANCE);

        // Create package meta-data objects
        theCompoundPackage.createPackageContents();
        theChangePackage.createPackageContents();
        theFeaturePackage.createPackageContents();
        theListPackage.createPackageContents();
        theAttributePackage.createPackageContents();
        theReferencePackage.createPackageContents();
        theRootPackage.createPackageContents();

        // Initialize created meta-data
        theCompoundPackage.initializePackageContents();
        theChangePackage.initializePackageContents();
        theFeaturePackage.initializePackageContents();
        theListPackage.initializePackageContents();
        theAttributePackage.initializePackageContents();
        theReferencePackage.initializePackageContents();
        theRootPackage.initializePackageContents();

        // Mark meta-data to indicate it can't be changed
        theCompoundPackage.freeze();

  
        // Update the registry and return the package
        EPackage.Registry.INSTANCE.put(CompoundPackage.eNS_URI, theCompoundPackage);
        return theCompoundPackage;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getCompoundEChange() {
        return compoundEChangeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EOperation getCompoundEChange__GetAtomicChanges() {
        return compoundEChangeEClass.getEOperations().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getMoveEObject() {
        return moveEObjectEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getMoveEObject_SubtractWhereChange() {
        return (EReference)moveEObjectEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getMoveEObject_SubtractWhatChange() {
        return (EReference)moveEObjectEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getMoveEObject_AddWhereChange() {
        return (EReference)moveEObjectEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getMoveEObject_AddWhatChange() {
        return (EReference)moveEObjectEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getReplaceInEList() {
        return replaceInEListEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getReplaceInEList_RemoveChange() {
        return (EReference)replaceInEListEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getReplaceInEList_InsertChange() {
        return (EReference)replaceInEListEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getExplicitUnsetEFeature() {
        return explicitUnsetEFeatureEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getExplicitUnsetEFeature_SubtractiveChanges() {
        return (EReference)explicitUnsetEFeatureEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public CompoundFactory getCompoundFactory() {
        return (CompoundFactory)getEFactoryInstance();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private boolean isCreated = false;

    /**
     * Creates the meta-model objects for the package.  This method is
     * guarded to have no affect on any invocation but its first.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void createPackageContents() {
        if (isCreated) return;
        isCreated = true;

        // Create classes and their features
        compoundEChangeEClass = createEClass(COMPOUND_ECHANGE);
        createEOperation(compoundEChangeEClass, COMPOUND_ECHANGE___GET_ATOMIC_CHANGES);

        moveEObjectEClass = createEClass(MOVE_EOBJECT);
        createEReference(moveEObjectEClass, MOVE_EOBJECT__SUBTRACT_WHERE_CHANGE);
        createEReference(moveEObjectEClass, MOVE_EOBJECT__SUBTRACT_WHAT_CHANGE);
        createEReference(moveEObjectEClass, MOVE_EOBJECT__ADD_WHERE_CHANGE);
        createEReference(moveEObjectEClass, MOVE_EOBJECT__ADD_WHAT_CHANGE);

        replaceInEListEClass = createEClass(REPLACE_IN_ELIST);
        createEReference(replaceInEListEClass, REPLACE_IN_ELIST__REMOVE_CHANGE);
        createEReference(replaceInEListEClass, REPLACE_IN_ELIST__INSERT_CHANGE);

        explicitUnsetEFeatureEClass = createEClass(EXPLICIT_UNSET_EFEATURE);
        createEReference(explicitUnsetEFeatureEClass, EXPLICIT_UNSET_EFEATURE__SUBTRACTIVE_CHANGES);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private boolean isInitialized = false;

    /**
     * Complete the initialization of the package and its meta-model.  This
     * method is guarded to have no affect on any invocation but its first.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void initializePackageContents() {
        if (isInitialized) return;
        isInitialized = true;

        // Initialize package
        setName(eNAME);
        setNsPrefix(eNS_PREFIX);
        setNsURI(eNS_URI);

        // Obtain other dependent packages
        ChangePackage theChangePackage = (ChangePackage)EPackage.Registry.INSTANCE.getEPackage(ChangePackage.eNS_URI);
        ReferencePackage theReferencePackage = (ReferencePackage)EPackage.Registry.INSTANCE.getEPackage(ReferencePackage.eNS_URI);
        ListPackage theListPackage = (ListPackage)EPackage.Registry.INSTANCE.getEPackage(ListPackage.eNS_URI);
        FeaturePackage theFeaturePackage = (FeaturePackage)EPackage.Registry.INSTANCE.getEPackage(FeaturePackage.eNS_URI);

        // Create type parameters
        ETypeParameter moveEObjectEClass_A = addETypeParameter(moveEObjectEClass, "A");
        ETypeParameter moveEObjectEClass_B = addETypeParameter(moveEObjectEClass, "B");
        ETypeParameter moveEObjectEClass_T = addETypeParameter(moveEObjectEClass, "T");
        ETypeParameter replaceInEListEClass_A = addETypeParameter(replaceInEListEClass, "A");
        ETypeParameter replaceInEListEClass_F = addETypeParameter(replaceInEListEClass, "F");
        ETypeParameter replaceInEListEClass_T = addETypeParameter(replaceInEListEClass, "T");
        ETypeParameter replaceInEListEClass_R = addETypeParameter(replaceInEListEClass, "R");
        ETypeParameter replaceInEListEClass_I = addETypeParameter(replaceInEListEClass, "I");
        ETypeParameter explicitUnsetEFeatureEClass_A = addETypeParameter(explicitUnsetEFeatureEClass, "A");
        ETypeParameter explicitUnsetEFeatureEClass_F = addETypeParameter(explicitUnsetEFeatureEClass, "F");
        ETypeParameter explicitUnsetEFeatureEClass_T = addETypeParameter(explicitUnsetEFeatureEClass, "T");
        ETypeParameter explicitUnsetEFeatureEClass_S = addETypeParameter(explicitUnsetEFeatureEClass, "S");

        // Set bounds for type parameters
        EGenericType g1 = createEGenericType(ecorePackage.getEObject());
        moveEObjectEClass_A.getEBounds().add(g1);
        g1 = createEGenericType(ecorePackage.getEObject());
        moveEObjectEClass_B.getEBounds().add(g1);
        g1 = createEGenericType(ecorePackage.getEObject());
        moveEObjectEClass_T.getEBounds().add(g1);
        g1 = createEGenericType(ecorePackage.getEObject());
        replaceInEListEClass_A.getEBounds().add(g1);
        g1 = createEGenericType(ecorePackage.getEStructuralFeature());
        replaceInEListEClass_F.getEBounds().add(g1);
        g1 = createEGenericType(ecorePackage.getEObject());
        replaceInEListEClass_T.getEBounds().add(g1);
        g1 = createEGenericType(theListPackage.getRemoveFromListEChange());
        EGenericType g2 = createEGenericType(replaceInEListEClass_A);
        g1.getETypeArguments().add(g2);
        g2 = createEGenericType(replaceInEListEClass_F);
        g1.getETypeArguments().add(g2);
        replaceInEListEClass_R.getEBounds().add(g1);
        g1 = createEGenericType(theFeaturePackage.getFeatureEChange());
        g2 = createEGenericType(replaceInEListEClass_A);
        g1.getETypeArguments().add(g2);
        g2 = createEGenericType(replaceInEListEClass_F);
        g1.getETypeArguments().add(g2);
        replaceInEListEClass_R.getEBounds().add(g1);
        g1 = createEGenericType(theChangePackage.getSubtractiveEChange());
        g2 = createEGenericType(replaceInEListEClass_T);
        g1.getETypeArguments().add(g2);
        replaceInEListEClass_R.getEBounds().add(g1);
        g1 = createEGenericType(theListPackage.getInsertInListEChange());
        g2 = createEGenericType(replaceInEListEClass_A);
        g1.getETypeArguments().add(g2);
        g2 = createEGenericType(replaceInEListEClass_F);
        g1.getETypeArguments().add(g2);
        replaceInEListEClass_I.getEBounds().add(g1);
        g1 = createEGenericType(theFeaturePackage.getFeatureEChange());
        g2 = createEGenericType(replaceInEListEClass_A);
        g1.getETypeArguments().add(g2);
        g2 = createEGenericType(replaceInEListEClass_F);
        g1.getETypeArguments().add(g2);
        replaceInEListEClass_I.getEBounds().add(g1);
        g1 = createEGenericType(theChangePackage.getAdditiveEChange());
        g2 = createEGenericType(replaceInEListEClass_T);
        g1.getETypeArguments().add(g2);
        replaceInEListEClass_I.getEBounds().add(g1);
        g1 = createEGenericType(ecorePackage.getEObject());
        explicitUnsetEFeatureEClass_A.getEBounds().add(g1);
        g1 = createEGenericType(ecorePackage.getEStructuralFeature());
        explicitUnsetEFeatureEClass_F.getEBounds().add(g1);
        g1 = createEGenericType(ecorePackage.getEJavaObject());
        explicitUnsetEFeatureEClass_T.getEBounds().add(g1);
        g1 = createEGenericType(theFeaturePackage.getFeatureEChange());
        g2 = createEGenericType(explicitUnsetEFeatureEClass_A);
        g1.getETypeArguments().add(g2);
        g2 = createEGenericType(explicitUnsetEFeatureEClass_F);
        g1.getETypeArguments().add(g2);
        explicitUnsetEFeatureEClass_S.getEBounds().add(g1);
        g1 = createEGenericType(theChangePackage.getSubtractiveEChange());
        g2 = createEGenericType(explicitUnsetEFeatureEClass_T);
        g1.getETypeArguments().add(g2);
        explicitUnsetEFeatureEClass_S.getEBounds().add(g1);

        // Add supertypes to classes
        compoundEChangeEClass.getESuperTypes().add(theChangePackage.getEChange());
        moveEObjectEClass.getESuperTypes().add(this.getCompoundEChange());
        replaceInEListEClass.getESuperTypes().add(this.getCompoundEChange());
        explicitUnsetEFeatureEClass.getESuperTypes().add(this.getCompoundEChange());

        // Initialize classes, features, and operations; add parameters
        initEClass(compoundEChangeEClass, CompoundEChange.class, "CompoundEChange", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        initEOperation(getCompoundEChange__GetAtomicChanges(), theChangePackage.getAtomicEChange(), "getAtomicChanges", 1, -1, IS_UNIQUE, IS_ORDERED);

        initEClass(moveEObjectEClass, MoveEObject.class, "MoveEObject", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        g1 = createEGenericType(theReferencePackage.getUpdateReferenceEChange());
        g2 = createEGenericType(moveEObjectEClass_A);
        g1.getETypeArguments().add(g2);
        initEReference(getMoveEObject_SubtractWhereChange(), g1, null, "subtractWhereChange", null, 0, 1, MoveEObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        g1 = createEGenericType(theChangePackage.getEObjectSubtractedEChange());
        g2 = createEGenericType(moveEObjectEClass_T);
        g1.getETypeArguments().add(g2);
        initEReference(getMoveEObject_SubtractWhatChange(), g1, null, "subtractWhatChange", null, 1, 1, MoveEObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        g1 = createEGenericType(theReferencePackage.getUpdateReferenceEChange());
        g2 = createEGenericType(moveEObjectEClass_B);
        g1.getETypeArguments().add(g2);
        initEReference(getMoveEObject_AddWhereChange(), g1, null, "addWhereChange", null, 0, 1, MoveEObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        g1 = createEGenericType(theChangePackage.getEObjectAddedEChange());
        g2 = createEGenericType(moveEObjectEClass_T);
        g1.getETypeArguments().add(g2);
        initEReference(getMoveEObject_AddWhatChange(), g1, null, "addWhatChange", null, 1, 1, MoveEObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(replaceInEListEClass, ReplaceInEList.class, "ReplaceInEList", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        g1 = createEGenericType(replaceInEListEClass_R);
        initEReference(getReplaceInEList_RemoveChange(), g1, null, "removeChange", null, 1, 1, ReplaceInEList.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        g1 = createEGenericType(replaceInEListEClass_I);
        initEReference(getReplaceInEList_InsertChange(), g1, null, "insertChange", null, 1, 1, ReplaceInEList.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(explicitUnsetEFeatureEClass, ExplicitUnsetEFeature.class, "ExplicitUnsetEFeature", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        g1 = createEGenericType(explicitUnsetEFeatureEClass_S);
        initEReference(getExplicitUnsetEFeature_SubtractiveChanges(), g1, null, "subtractiveChanges", null, 1, -1, ExplicitUnsetEFeature.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    }

} //CompoundPackageImpl
