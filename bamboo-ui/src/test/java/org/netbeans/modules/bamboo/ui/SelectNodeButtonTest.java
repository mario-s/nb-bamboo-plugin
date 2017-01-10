package org.netbeans.modules.bamboo.ui;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.BDDMockito.given;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.netbeans.modules.bamboo.model.rcp.PlanVo;
import org.openide.explorer.ExplorerManager;

/**
 *
 * @author spindizzy
 */
@RunWith(MockitoJUnitRunner.class)
public class SelectNodeButtonTest {

    private static final String FOO = "foo";
    private PlanVo plan;

    private ExplorerManager explorerManager;
    
    @Mock
    private ExplorerManager.Provider servicesTab;
    
    private SelectNodeButton classUnderTest;
    
    @Before
    public void setUp() {
        plan = new PlanVo(FOO);
        explorerManager = new ExplorerManager();
        
        classUnderTest = new SelectNodeButton(plan) {
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
