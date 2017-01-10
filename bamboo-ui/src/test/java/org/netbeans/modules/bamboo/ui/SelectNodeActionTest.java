package org.netbeans.modules.bamboo.ui;

import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import org.mockito.runners.MockitoJUnitRunner;
import org.netbeans.modules.bamboo.model.rcp.PlanVo;
import org.openide.explorer.ExplorerManager;
import org.openide.windows.TopComponent;


/**
 *
 * @author spindizzy
 */
@RunWith(MockitoJUnitRunner.class)
public class SelectNodeActionTest {

    private static final String FOO = "foo";
    private PlanVo plan;

    private ExplorerManager explorerManager;
    
    @Mock
    private TopComponent servicesTab;
    
    private SelectNodeAction classUnderTest;
    
    @Before
    public void setUp() {
        plan = new PlanVo(FOO);
        explorerManager = new ExplorerManager();
        
        classUnderTest = new SelectNodeAction(plan) {
            @Override
            Optional<TopComponent> findServicesTab() {
                return Optional.of(servicesTab);
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
