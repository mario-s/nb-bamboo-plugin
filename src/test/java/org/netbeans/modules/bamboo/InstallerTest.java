package org.netbeans.modules.bamboo;

import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import org.mockito.Mock;

import static org.mockito.Mockito.verify;

import static org.mockito.internal.util.reflection.Whitebox.setInternalState;

import org.mockito.runners.MockitoJUnitRunner;

import org.netbeans.modules.bamboo.glue.InstanceManageable;


/**
 */
@RunWith(MockitoJUnitRunner.class)
public class InstallerTest {
    @Mock
    private InstanceManageable manager;

    private Installer classUnderTest;

    @Before
    public void setUp() {
        classUnderTest = new Installer();
    }

    /**
     * Test of run method, of class Installer. //TODO update the test
     */
    @Test
    public void testRun() {
        classUnderTest.run();
    }
}
