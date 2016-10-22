package org.netbeans.modules.bamboo.ui.notification;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.ws.rs.core.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.netbeans.modules.bamboo.model.PlanVo;
import org.netbeans.modules.bamboo.model.QueueEvent;
import org.openide.awt.NotificationDisplayer;
import org.openide.awt.NotificationDisplayer.Category;
import org.openide.awt.NotificationDisplayer.Priority;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.verify;

/**
 *
 * @author spindizzy
 */
@RunWith(MockitoJUnitRunner.class)
public class QueueResultNotifyDisplayerTest {

    private static final String FOO = "foo";
    @Mock
    private Icon instanceIcon;

    @Mock
    private NotificationDisplayer notificationDisplayer;

    private QueueResultNotifyDisplayer classUnderTest;

    private QueueEvent event;

    @Before
    public void setUp() {
        event = new QueueEvent();
        PlanVo plan = new PlanVo(FOO);
        event.setPlan(plan);
        classUnderTest = new QueueResultNotifyDisplayer(instanceIcon, event) {
            @Override
            NotificationDisplayer getNotificationDisplayer() {
                return notificationDisplayer;
            }
        };
    }

    /**
     * Test of run method, of class QueueResultNotifyDisplayer.
     */
    @Test
    public void testRun_ResponseOk_ExpectNotify() {
        event.setResponse(Response.ok().build());
        classUnderTest.run();
        verify(notificationDisplayer).notify(anyString(), any(Icon.class), isA(JLabel.class), isA(
                ResultDetailsPanel.class), eq(Priority.NORMAL), eq(Category.INFO));
    }
    
     /**
     * Test of run method, of class QueueResultNotifyDisplayer.
     */
    @Test
    public void testRun_ResponseError_ExpectNotify() {
        event.setResponse(Response.serverError().build());
        classUnderTest.run();
        verify(notificationDisplayer).notify(anyString(), any(Icon.class), isA(JLabel.class), isA(
                ResultDetailsPanel.class), eq(Priority.HIGH), eq(Category.ERROR));
    }

}
