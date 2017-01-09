package org.netbeans.modules.bamboo.ui;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openide.windows.TopComponent;

/**
 *
 * @author spindizzy
 */
@RunWith(MockitoJUnitRunner.class)
public class SelectNodeButtonTest {
    
    @Mock
    private TopComponent servicesTab;
    
    private SelectNodeButton classUnderTest;
    
    @Before
    public void setUp() {
        classUnderTest = new SelectNodeButton() {
            @Override
            TopComponent findServicesTab() {
                return servicesTab;
            }
            
        };
    }

    /**
     * Test of actionPerformed method, of class SelectNodeButton.
     */
    @Test
    public void testActionPerformed() {
        classUnderTest.actionPerformed(null);
    }

    
}
