package org.netbeans.modules.bamboo;

import org.netbeans.modules.bamboo.mock.MockInstanceManager;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import static org.mockito.BDDMockito.given;

import org.mockito.Mock;

import static org.mockito.Mockito.verify;

import org.mockito.runners.MockitoJUnitRunner;

import org.netbeans.modules.bamboo.glue.InstanceManageable;
import org.netbeans.modules.bamboo.rest.DefaultBambooInstance;

import static org.openide.util.Lookup.getDefault;

import static java.util.Collections.singletonList;


/**
 */
@RunWith(MockitoJUnitRunner.class)
public class InstallerTest {
    @Mock
    private InstanceManageable delegate;

    @Mock
    private DefaultBambooInstance instance;

    private Installer classUnderTest;

    @Before
    public void setUp() {
        MockInstanceManager manager =
            (MockInstanceManager) getDefault().lookup(InstanceManageable.class);
        manager.setDelegate(delegate);

        given(delegate.loadInstances()).willReturn(singletonList(instance));

        classUnderTest = new Installer();
    }

    /**
     * Test of run method, of class Installer.
     */
    @Test
    public void testRun() {
        classUnderTest.run();
        verify(delegate).addInstance(instance);
    }
}
