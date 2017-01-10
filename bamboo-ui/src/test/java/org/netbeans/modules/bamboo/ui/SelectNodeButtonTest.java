package org.netbeans.modules.bamboo.ui;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.BDDMockito.given;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openide.explorer.ExplorerManager;

/**
 *
 * @author spindizzy
 */
@RunWith(MockitoJUnitRunner.class)
public class SelectNodeButtonTest {
    

    private ExplorerManager explorerManager;
    
    @Mock
    private ExplorerManager.Provider servicesTab;
    
    private SelectNodeButton classUnderTest;
    
    @Before
    public void setUp() {
        explorerManager = new ExplorerManager();
        
        classUnderTest = new SelectNodeButton() {
            @Override
            ExplorerManager.Provider findServicesTab() {
                return servicesTab;
            }
        };
        
        given(servicesTab.getExplorerManager()).willReturn(explorerManager);
    }

    /**
     * Test of actionPerformed method, of class SelectNodeButton.
     */
    @Test
    public void testActionPerformed() {
        classUnderTest.actionPerformed(null);
    }

    
}
