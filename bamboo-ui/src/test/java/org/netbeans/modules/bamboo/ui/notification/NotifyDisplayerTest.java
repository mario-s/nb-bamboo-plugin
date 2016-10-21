package org.netbeans.modules.bamboo.ui.notification;

import javax.swing.Icon;
import javax.swing.JLabel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;

import org.mockito.Mock;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.mockito.runners.MockitoJUnitRunner;
import org.netbeans.modules.bamboo.model.PlanVo;
import org.netbeans.modules.bamboo.model.ResultVo;
import org.netbeans.modules.bamboo.model.State;
import org.openide.awt.NotificationDisplayer;
import org.openide.awt.NotificationDisplayer.Category;
import org.openide.awt.NotificationDisplayer.Priority;

import static org.mockito.Matchers.isA;
import org.netbeans.modules.bamboo.model.LifeCycleState;

/**
 *
 * @author spindizzy
 */
@RunWith(MockitoJUnitRunner.class)
public class NotifyDisplayerTest {

    @Mock
    private Icon instanceIcon;

    @Mock
    private NotificationDisplayer notificationDisplayer;
    
    private BuildResult buildResult;
    
    private PlanVo plan;
    
    private ResultVo oldResult;
    
    private ResultVo newResult;

    private NotifyDisplayer classUnderTest;

    @Before
    public void setUp() {
        oldResult = new ResultVo();
        newResult = new ResultVo();
        plan = new PlanVo("test", "test");
        buildResult = new BuildResult(plan, oldResult, newResult);
        classUnderTest = new NotifyDisplayer(instanceIcon, buildResult) {
            @Override
            NotificationDisplayer getNotificationDisplayer() {
                return notificationDisplayer;
            }
        };
    }

    /**
     * Test of run method, of class NotifyDisplayer.
     */
    @Test
    public void testRun_ResultNormal_ExpectNotifyNormal() {
        classUnderTest.run();
        verify(notificationDisplayer).notify(anyString(), any(Icon.class), isA(JLabel.class), isA(ResultDetailsPanel.class), eq(Priority.NORMAL), eq(Category.INFO));
    }

    /**
     * Test of run method, of class NotifyDisplayer.
     */
    @Test
    public void testRun_ResultFailed_ExpectNotifyHigh() {
        ResultVo result = new ResultVo();
        result.setState(State.Failed);
        plan.setResult(result);
        classUnderTest.run();
        verify(notificationDisplayer).notify(anyString(), any(Icon.class), isA(JLabel.class), isA(ResultDetailsPanel.class), eq(Priority.HIGH), eq(Category.ERROR));
    }
    
    /**
     * Test of run method, of class NotifyDisplayer.
     */
    @Test
    public void testRun_ResultStillNormal_ExpectNoNotify() {
        oldResult.setState(State.Successful);
        newResult.setState(State.Successful);
        classUnderTest.run();
        verify(notificationDisplayer, never()).notify(anyString(), any(Icon.class), isA(JLabel.class), isA(ResultDetailsPanel.class), eq(Priority.NORMAL), eq(Category.INFO));
    }
    
    /**
     * Test of run method, of class NotifyDisplayer.
     */
    @Test
    public void testRun_ResultQueued_ExpectNotify() {
        oldResult.setState(State.Unknown);
        newResult.setState(State.Successful);
        plan.getResult().setLifeCycleState(LifeCycleState.Queued);
        classUnderTest.run();
        verify(notificationDisplayer).notify(anyString(), any(Icon.class), isA(JLabel.class), isA(ResultDetailsPanel.class), eq(Priority.NORMAL), eq(Category.INFO));
    }

}
