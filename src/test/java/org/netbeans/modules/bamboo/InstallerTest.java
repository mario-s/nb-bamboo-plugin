package org.netbeans.modules.bamboo;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.openide.util.LookupEvent;

/**
 *
 */
public class InstallerTest {
    
    private Installer classUnderTest;
    
    @Before
    public void setUp() {
        classUnderTest = new Installer();
    }

    /**
     * Test of run method, of class Installer.
     */
    @Test
    public void testRun() {
        classUnderTest.run();
    }
}
