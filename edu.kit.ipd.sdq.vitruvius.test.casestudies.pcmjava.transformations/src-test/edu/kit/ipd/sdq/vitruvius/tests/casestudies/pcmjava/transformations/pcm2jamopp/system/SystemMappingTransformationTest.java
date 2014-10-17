package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.pcm2jamopp.system;

import static org.junit.Assert.fail;

import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.containers.Package;
import org.junit.Test;

import de.uka.ipd.sdq.pcm.system.System;
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.EcoreResourceBridge;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.pcm2jamopp.PCM2JaMoPPTransformationTest;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.utils.PCM2JaMoPPUtils;

public class SystemMappingTransformationTest extends PCM2JaMoPPTransformationTest {

    @Test
    public void testCreateSystem() throws Throwable {
        final System system = super.createAndSyncSystem(PCM2JaMoPPUtils.SYSTEM_NAME);

        this.assertSystem(system);
    }

    @Test
    public void testRenameSystem() throws Throwable {
        final System system = super.createAndSyncSystem(PCM2JaMoPPUtils.SYSTEM_NAME);

        system.setEntityName(PCM2JaMoPPUtils.SYSTEM_NAME + PCM2JaMoPPUtils.RENAME);
        EcoreResourceBridge.saveResource(system.eResource());
        super.triggerSynchronization(system);

        this.assertSystem(system);
    }

    @Test
    public void testRemoveSystem() throws Throwable {
        final System system = PCM2JaMoPPUtils.createSystem(this.resourceSet, PCM2JaMoPPUtils.SYSTEM_NAME);

        fail("Not yet implemented");
    }

    /**
     * a system should correspond to a Package with its name and a class with its name "Impl"
     *
     * @param system
     * @throws Throwable
     */
    @SuppressWarnings("unchecked")
    private void assertSystem(final System system) throws Throwable {
        final String expectedName = system.getEntityName();
        this.assertCorrespondnecesAndCompareNames(system, 3, new Class[] { Package.class, CompilationUnit.class,
                org.emftext.language.java.classifiers.Class.class }, new String[] { expectedName,
                expectedName + "Impl", expectedName + "Impl" });
    }
}
