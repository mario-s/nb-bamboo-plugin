package org.netbeans.modules.bamboo.ui.actions;

import java.awt.event.ActionEvent;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.netbeans.modules.bamboo.model.BambooInstance;
import org.netbeans.modules.bamboo.model.PlanVo;
import org.netbeans.modules.bamboo.model.ProjectVo;

/**
 *
 * @author spindizzy
 */
@RunWith(MockitoJUnitRunner.class)
public class QueuePlanActionTest {
    private static final String FOO = "foo";
    
    @Mock
    private BambooInstance instance;
    
    private ProjectVo project;
    
    private PlanVo plan;
    
    private QueuePlanAction classUnderTest;
    
    @Before
    public void setUp() {
        plan = new PlanVo(FOO);
        project = new ProjectVo(FOO);
        plan.setParent(project);
        project.setParent(instance);
        
        classUnderTest = new QueuePlanAction(plan);
    }

    /**
     * Test of actionPerformed method, of class QueuePlanAction.
     */
    @Test
    @Ignore
    public void testActionPerformed() {
        classUnderTest.actionPerformed(null);
    }
    
}
