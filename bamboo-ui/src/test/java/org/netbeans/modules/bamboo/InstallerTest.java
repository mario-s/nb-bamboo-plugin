package org.netbeans.modules.bamboo;

import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import static org.mockito.BDDMockito.given;

import org.mockito.InOrder;
import org.mockito.Mock;

import static org.mockito.Mockito.inOrder;

import org.mockito.runners.MockitoJUnitRunner;

import org.netbeans.modules.bamboo.model.rcp.BambooInstance;
import org.netbeans.modules.bamboo.client.glue.InstanceManageable;
import org.netbeans.modules.bamboo.mock.MockInstanceManager;

import static org.openide.util.Lookup.getDefault;

import org.openide.util.Task;

import static java.util.Collections.singletonList;


/**
 */
@RunWith(MockitoJUnitRunner.class)
public class InstallerTest {
    @Mock
    private InstanceManageable delegate;

    @Mock
    private BambooInstance instance;

    private Task task;

    private Installer classUnderTest;

    @Before
    public void setUp() {
        MockInstanceManager manager =
            (MockInstanceManager) getDefault().lookup(InstanceManageable.class);
        manager.setDelegate(delegate);

        task = new Task(null);

        given(delegate.loadInstances()).willReturn(singletonList(instance));
        given(instance.synchronize()).willReturn(task);

        classUnderTest = new Installer();
    }

    /**
     * Test of run method, of class Installer.
     */
    @Test
    public void testRun() {
        classUnderTest.run();

        InOrder order = inOrder(instance, delegate);
        order.verify(instance).synchronize();
        order.verify(delegate).addInstance(instance);
    }
}
