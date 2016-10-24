package org.netbeans.modules.bamboo.ui.notification;

import org.junit.After;

import static java.util.Collections.singletonList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;

import org.mockito.Mock;

import static org.mockito.Mockito.verify;

import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.netbeans.modules.bamboo.model.BambooInstance;
import org.netbeans.modules.bamboo.model.LookupContext;
import org.netbeans.modules.bamboo.model.PlanVo;
import org.netbeans.modules.bamboo.model.ProjectVo;
import org.netbeans.modules.bamboo.model.event.QueueEvent;
import org.netbeans.modules.bamboo.model.ResultVo;
import org.netbeans.modules.bamboo.model.State;
import org.openide.util.Lookup;

import static org.mockito.Matchers.eq;

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

    /**
     * Test of propertyChange method, of class PlanResultNotify.
     */
    @Test
    public void testPropertyChange_ExpectNotify() {
        ResultVo resultVo = new ResultVo();
        resultVo.setNumber(2);
        resultVo.setState(State.Failed);
        plan.setResult(resultVo);
        verify(delegator).notify(any(BuildResult.class));
    }
    
    @Test
    public void testResultChanged_ExpectNotify() {
        LookupContext.Instance.add(event);
        verify(delegator).notify(eq(event));
    }
    
}
