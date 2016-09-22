package org.netbeans.modules.bamboo.ui.notification;

import java.awt.event.ActionListener;
import javax.swing.Icon;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isNull;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import org.mockito.runners.MockitoJUnitRunner;
import org.netbeans.modules.bamboo.model.PlanVo;
import org.netbeans.modules.bamboo.model.ResultVo;
import org.netbeans.modules.bamboo.model.State;
import org.openide.awt.NotificationDisplayer;
import org.openide.awt.NotificationDisplayer.Category;
import org.openide.awt.NotificationDisplayer.Priority;

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

    private PlanVo plan;

    private NotifyDisplayer classUnderTest;

    @Before
    public void setUp() {
        plan = new PlanVo();
        plan.setName("test");
        classUnderTest = new NotifyDisplayer(instanceIcon, plan) {
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
    public void testRunResultNormal_ExpectNotifyNormal() {
        classUnderTest.run();
        verify(notificationDisplayer).notify(anyString(), any(Icon.class), anyString(), isNull(ActionListener.class), eq(Priority.NORMAL), eq(Category.INFO));
    }

    /**
     * Test of run method, of class NotifyDisplayer.
     */
    @Test
    public void testRunResultFailed_ExpectNotifyHigh() {
        ResultVo result = new ResultVo();
        result.setState(State.Failed);
        plan.setResult(result);
        classUnderTest.run();
        verify(notificationDisplayer).notify(anyString(), any(Icon.class), anyString(), isNull(ActionListener.class), eq(Priority.HIGH), eq(Category.ERROR));
    }

}
