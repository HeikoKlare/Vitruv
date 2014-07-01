package edu.kit.ipd.sdq.vitruvius.tests.framework.run.SynchronisationTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.emftext.language.java.classifiers.Classifier;
import org.emftext.language.java.classifiers.impl.ClassImpl;
import org.emftext.language.java.classifiers.impl.InterfaceImpl;
import org.emftext.language.java.containers.CompilationUnit;
import org.junit.BeforeClass;
import org.junit.Test;

import de.uka.ipd.sdq.pcm.repository.BasicComponent;
import de.uka.ipd.sdq.pcm.repository.OperationInterface;
import de.uka.ipd.sdq.pcm.repository.Repository;
import de.uka.ipd.sdq.pcm.repository.RepositoryFactory;
import de.uka.ipd.sdq.pcm.util.PcmResourceFactoryImpl;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.PCMJavaUtils;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.FileChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.FileChange.FileChangeKind;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ModelInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.metarepository.MetaRepositoryImpl;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emf.MonitoredEmfEditorImpl;
import edu.kit.ipd.sdq.vitruvius.framework.run.syncmanager.SyncManagerImpl;
import edu.kit.ipd.sdq.vitruvius.tests.mockup.MetaRepositoryTest;

public class SynchronisationTest extends MetaRepositoryTest {

    private static final Logger logger = Logger.getLogger(SynchronisationTest.class.getSimpleName());

    private static final String MODEL_PATH = "testModels";
    private static final String MODEL_PATH_TMP = MODEL_PATH + "Tmp";
    private static final String PCM_REPOSITORY_FILE_NAME = MODEL_PATH + "/" + "pcmRepoTest.repository";
    private static final String PCM_REPOSITORY_NAME = "testRepository";
    private static final String PCM_INTERFACE_CREATION_NAME = "TestCreateInterface";
    private static final String PCM_REPOSITORY_CREATION_NAME = "TestCreateComponent";
    private static final String PCM_MM_URI = "http://sdq.ipd.uka.de/PalladioComponentModel/5.0";

    private static MonitoredEmfEditorImpl monitor;
    private static VURI sourceModelURI;
    private static SyncManagerImpl syncManager;
    private static MetaRepositoryImpl metaRepository;
    private static Repository repository;

    @BeforeClass
    public static void setUp() throws Exception {
        // set up syncManager, monitor and metaRepostitory
        metaRepository = PCMJavaUtils.createPCMJavaMetarepository();
        SyncManagerImpl.setMetaRepositoryImpl(metaRepository);
        syncManager = SyncManagerImpl.getSyncManagerInstance();
        monitor = new MonitoredEmfEditorImpl(syncManager, syncManager.getModelProviding());
        // create pcm model instance
        repository = RepositoryFactory.eINSTANCE.createRepository();
        repository.setEntityName(PCM_REPOSITORY_NAME);
        sourceModelURI = VURI.getInstance(MetaRepositoryTest.PROJECT_URI + "/" + PCM_REPOSITORY_FILE_NAME);

        ResourceSet resourceSet = new ResourceSetImpl();
        resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap()
        .put("repository", new PcmResourceFactoryImpl());
        Resource resource = resourceSet.createResource(sourceModelURI.getEMFUri());
        if (null == resource) {
            fail("Could not create resource with URI: " + sourceModelURI);
            return;
        }
        resource.getContents().add(repository);
        Map<Object, Object> options = new HashMap<Object, Object>();
        options.put(XMLResource.OPTION_ENCODING, "UTF-8");
        try {
            resource.save(options);
        } catch (IOException e) {
            logger.warn("createRepositoryFile failed: Could not save resource: " + resource.getURI() + ". Exception: "
                    + e.getMessage());
            e.printStackTrace();
            throw e;
        }

        createAndSyncFileChange(sourceModelURI);
        addContentAdapter(repository);
        createInterface(repository);
    }

    /**
     * create an OperaitonInterface
     *
     * @param repository
     */
    private static void createInterface(final Repository repository) {
        OperationInterface opInterface = RepositoryFactory.eINSTANCE.createOperationInterface();
        opInterface.setEntityName(PCM_INTERFACE_CREATION_NAME);
        opInterface.setRepository__Interface(repository);
    }

    /**
     * add monitor to root object of new file
     *
     * @param resource
     *            resource containing the root EObject of the new file
     * @throws NoSuchFieldException
     * @throws SecurityException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    private static void addContentAdapter(final EObject eObject) throws SecurityException, NoSuchFieldException,
    IllegalArgumentException, IllegalAccessException {
        Field f = monitor.getClass().getDeclaredField("emfMonitorAdapter");
        f.setAccessible(true);
        EContentAdapter contentAdapter = (EContentAdapter) f.get(monitor);
        eObject.eAdapters().add(contentAdapter);
    }

    /**
     * synchronize file create, which internally creates a emf change for creation of root object in
     * repository model
     */
    private static void createAndSyncFileChange(final VURI vuri) {
        FileChange fileChange = new FileChange(FileChangeKind.CREATE);
        List<Change> changes = new ArrayList<Change>(1);
        changes.add(fileChange);
        syncManager.synchronizeChanges(changes, vuri);
    }

    /**
     * Triggers the synchronisation of the model. Should synchronize the creation of the interface.
     */
    @Test
    public void testSyncInterface() {
        createInterface(repository);

        monitor.triggerSynchronisation(sourceModelURI);

        // a java interface in the package "testRepository" should have been created
        String javaInterfaceLocation = getSrcPath() + PCM_REPOSITORY_NAME + "/" + PCM_INTERFACE_CREATION_NAME + ".java";
        VURI interfaceVURI = VURI.getInstance(javaInterfaceLocation);
        ModelInstance newInterface = syncManager.getModelProviding().getModelInstanceOriginal(interfaceVURI);
        CompilationUnit cu = newInterface.getUniqueRootEObjectIfCorrectlyTyped(CompilationUnit.class);
        assertClassifierInJaMoPPCompilationUnit(cu, InterfaceImpl.class, PCM_INTERFACE_CREATION_NAME, true);
    }

    @Test
    public void testSyncBasicComponent() {
        createBasicComponent(repository);

        monitor.triggerSynchronisation(sourceModelURI);

        // a package and an interface should have been created in the package "testRepository
        String javaClassLocation = getSrcPath() + PCM_REPOSITORY_NAME + "/" + PCM_REPOSITORY_CREATION_NAME + "/"
                + PCM_REPOSITORY_CREATION_NAME + "Impl" + ".java";
        VURI vuri = VURI.getInstance(javaClassLocation);
        ModelInstance newClass = syncManager.getModelProviding().getModelInstanceOriginal(vuri);
        CompilationUnit cu = newClass.getUniqueRootEObjectIfCorrectlyTyped(CompilationUnit.class);
        Classifier classifier = assertClassifierInJaMoPPCompilationUnit(cu, ClassImpl.class,
                PCM_REPOSITORY_CREATION_NAME + "Impl", true);
        if (null != classifier) {
            assertNamespaceOfClassifier(classifier, PCM_REPOSITORY_NAME + "." + PCM_REPOSITORY_CREATION_NAME);
        }
    }

    private void assertNamespaceOfClassifier(final Classifier classifier, final String expectedNamespace) {
        String namespace = classifier.getContainingCompilationUnit().getNamespacesAsString();
        if (namespace.endsWith(".")) {
            namespace = namespace.substring(0, namespace.length() - 1);
        }
        assertEquals("Namespace of classifier not equal expected namespace", expectedNamespace, namespace);
    }

    private void createBasicComponent(final Repository repository) {
        BasicComponent bc = RepositoryFactory.eINSTANCE.createBasicComponent();
        bc.setEntityName(PCM_REPOSITORY_CREATION_NAME);
        bc.setRepository__RepositoryComponent(repository);
    }

    private String getSrcPath() {
        return MetaRepositoryTest.PROJECT_URI + "/src/";
    }

    private Classifier assertClassifierInJaMoPPCompilationUnit(final CompilationUnit cu, final Class<?> classifierType,
            final String classifierName, final boolean failIfNotFound) {
        for (Classifier classifier : cu.getClassifiers()) {
            if (classifier.getClass().equals(classifierType) && classifier.getName().equals(classifierName)) {
                logger.debug("Found classifier with type " + classifierType.getSimpleName() + " and name "
                        + classifierName + " in CompilationUnit " + cu.getName());
                return classifier;
            }
        }
        if (failIfNotFound) {
            fail("Classifier with type " + classifierType.getSimpleName() + "  and name " + classifierName
                    + " not found in CompilationUnit " + cu.getName());
        }
        return null;
    }
}
