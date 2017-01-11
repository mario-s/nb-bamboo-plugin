package org.netbeans.modules.bamboo.ui;

import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.BDDMockito.given;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;

import org.mockito.runners.MockitoJUnitRunner;
import org.netbeans.modules.bamboo.model.rcp.PlanVo;
import org.netbeans.modules.bamboo.model.rcp.ProjectVo;
import org.openide.explorer.ExplorerManager;
import org.openide.windows.TopComponent;


/**
 *
 * @author spindizzy
 */
@RunWith(MockitoJUnitRunner.class)
public class SelectNodeListenerTest {

    private static final String FOO = "foo";
    private PlanVo plan;

    private ExplorerManager explorerManager;
    
    @Mock
    private ExplorerManager.Provider provider;
    
    @Mock
    private TopComponent servicesTab;
    
    private SelectNodeListener classUnderTest;
    
    @Before
    public void setUp() {
        plan = new PlanVo(FOO);
        ProjectVo project = new ProjectVo("FOO");
        plan.setParent(project);
        
        
        explorerManager = new ExplorerManager();
        
        classUnderTest = new SelectNodeListener(plan) {
            @Override
            Optional<TopComponent> findServicesTab() {
                return Optional.of(servicesTab);
            }
        };
        
        given(provider.getExplorerManager()).willReturn(explorerManager);
    }

    /**
     * Test of actionPerformed method, of class SelectNodeButton.
     */
    @Test
    public void testActionPerformed() {
        classUnderTest.actionPerformed(null);
        verify(servicesTab).requestActive();
    }

    @Test
    public void testSelectNodes() {
        classUnderTest.selectNodes(provider, plan);
    }
}
