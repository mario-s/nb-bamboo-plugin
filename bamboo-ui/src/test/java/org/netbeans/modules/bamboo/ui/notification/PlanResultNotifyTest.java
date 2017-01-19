package org.netbeans.modules.bamboo.ui.notification;

import org.junit.After;

import static java.util.Collections.singletonList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.BDDMockito.given;

import org.mockito.Mock;

import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.netbeans.modules.bamboo.model.rcp.BambooInstance;
import org.netbeans.modules.bamboo.LookupContext;
import org.netbeans.modules.bamboo.model.rcp.PlanVo;
import org.netbeans.modules.bamboo.model.rcp.ProjectVo;
import org.netbeans.modules.bamboo.model.event.QueueEvent;
import org.netbeans.modules.bamboo.model.rcp.ResultVo;
import org.netbeans.modules.bamboo.model.State;
import org.openide.util.Lookup;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;

/**
 *
 * @author Mario Schroeder
 */
@RunWith(MockitoJUnitRunner.class)
public class PlanResultNotifyTest {

    @Mock
    private BambooInstance instance;

    @Spy
    private NotifyDelegator delegator;

    private PlanResultNotify classUnderTest;

    private PlanVo plan;

    private QueueEvent event;

    @Before
    public void setUp() {
        plan = new PlanVo("a");
        plan.setName("test");
        ResultVo resultVo = new ResultVo();
        resultVo.setNumber(1);
        plan.setResult(resultVo);

        event = new QueueEvent();

        ProjectVo project = new ProjectVo("");
        project.setChildren(singletonList(plan));

        given(instance.getChildren()).willReturn(singletonList(project));

        Lookup lookup = LookupContext.Instance.getLookup();
        given(instance.getLookup()).willReturn(lookup);

        classUnderTest = new PlanResultNotify(instance);
        classUnderTest.setDelegator(delegator);
    }

    @After
    public void shutDown() {
        LookupContext.Instance.remove(event);
    }

    private ResultVo newFailedResult() {
        ResultVo resultVo = new ResultVo();
        resultVo.setNumber(2);
        resultVo.setState(State.Failed);
        return resultVo;
    }

    /**
     * Test of propertyChange method, of class PlanResultNotify.
     */
    @Test
    public void testPropertyChange_ExpectNotify() {
        ResultVo resultVo = newFailedResult();
        plan.setResult(resultVo);
        verify(delegator).notify(any(BuildResult.class));
    }
    
     /**
     * Test of propertyChange method, of class PlanResultNotify.
     */
    @Test
    public void testPropertyChange_PlanIgnore_ExpectNoNotify() {
        plan.setNotify(false);
        ResultVo resultVo = newFailedResult();
        plan.setResult(resultVo);
        verify(delegator, never()).notify(any(BuildResult.class));
    }

    @Test
    public void testResultChanged_ExpectNotify() {
        LookupContext.Instance.add(event);
        verify(delegator).notify(eq(event));
    }

}
