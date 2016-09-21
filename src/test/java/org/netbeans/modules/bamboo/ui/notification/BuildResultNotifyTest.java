package org.netbeans.modules.bamboo.ui.notification;

import static java.util.Collections.singletonList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.BDDMockito.given;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.netbeans.modules.bamboo.glue.BambooInstance;
import org.netbeans.modules.bamboo.model.PlanVo;
import org.netbeans.modules.bamboo.model.ProjectVo;
import org.netbeans.modules.bamboo.model.ResultVo;
import org.netbeans.modules.bamboo.model.State;

/**
 *
 * @author spindizzy
 */
@RunWith(MockitoJUnitRunner.class)
public class BuildResultNotifyTest {
    @Mock
    private BambooInstance instance;
    
    @Spy
    private NotifyDelegator delegator;
    
    private BuildResultNotify classUnderTest;
    
    private PlanVo plan;
    
    @Before
    public void setUp() {
        plan = new PlanVo();
        ResultVo resultVo = new ResultVo();
        resultVo.setNumber(1);
        plan.setResult(resultVo);
        ProjectVo project = new ProjectVo();
        project.setPlans(singletonList(plan));
        given(instance.getProjects()).willReturn(singletonList(project));
        classUnderTest = new BuildResultNotify(instance);
        classUnderTest.setDelegator(delegator);
    }

    /**
     * Test of propertyChange method, of class BuildResultNotify.
     */
    @Test
    public void testPropertyChange() {
        ResultVo resultVo = new ResultVo();
        resultVo.setNumber(2);
        resultVo.setState(State.Failed);
        plan.setResult(resultVo);
        verify(delegator).notify(plan);
    }
    
}
