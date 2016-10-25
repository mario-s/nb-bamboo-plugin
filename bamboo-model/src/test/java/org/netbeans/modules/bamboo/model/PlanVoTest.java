package org.netbeans.modules.bamboo.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 *
 * @author spindizzy
 */
@RunWith(MockitoJUnitRunner.class)
public class PlanVoTest {

    private static final String FOO = "foo";
    @Mock
    private BambooInstance instance;

    @Mock
    private PropertyChangeListener listener;

    private PlanVo classUnderTest;

    @Before
    public void setUp() {
        classUnderTest = new PlanVo(FOO);
        ProjectVo project = new ProjectVo(FOO);
        project.setParent(instance);
        classUnderTest.setParent(project);
        ResultVo resultVo = newResult();
        classUnderTest.setResult(resultVo);

        classUnderTest.addPropertyChangeListener(listener);
    }

    private ResultVo newResult() {
        ResultVo resultVo = new ResultVo(FOO);
        resultVo.setNumber(1);
        return resultVo;
    }

    /**
     * Test of setResult method, of class PlanVo.
     */
    @Test
    public void testSetResult_NewLifeCycleState_ExpectEventFired() {
        ResultVo result = newResult();
        result.setLifeCycleState(LifeCycleState.Queued);
        classUnderTest.setResult(result);
        verify(listener).propertyChange(any(PropertyChangeEvent.class));
    }

    /**
     * Test of setResult method, of class PlanVo.
     */
    @Test
    public void testSetResult_SmallerNumber_ExpectEventNotFired() {
        ResultVo result = newResult();
        result.setNumber(0);
        result.setLifeCycleState(LifeCycleState.Queued);
        classUnderTest.setResult(result);
        verify(listener, never()).propertyChange(any(PropertyChangeEvent.class));
    }

    @Test
    public void testSetEnable_ExpectListenerCalled() {
        classUnderTest.setEnabled(true);
        verify(listener).propertyChange(any(PropertyChangeEvent.class));
    }
    
    @Test
    public void testQueue_ExpectCallToInstance() {
        classUnderTest.queue();
        verify(instance).queue(classUnderTest);
    }
    
     @Test
    public void testIsAvailable_ParentNotPresent_ExpectFalse() {
        boolean result = classUnderTest.isAvailable();
        assertThat(result, is(false));
    }
}
