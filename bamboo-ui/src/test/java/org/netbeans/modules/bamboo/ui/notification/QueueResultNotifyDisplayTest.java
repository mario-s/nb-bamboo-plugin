/* 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.netbeans.modules.bamboo.ui.notification;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.netbeans.modules.bamboo.model.rcp.PlanVo;
import org.netbeans.modules.bamboo.model.event.QueueEvent;
import org.openide.awt.NotificationDisplayer;
import org.openide.awt.NotificationDisplayer.Category;
import org.openide.awt.NotificationDisplayer.Priority;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;

/**
 *
 * @author Mario Schroeder
 */
@ExtendWith(MockitoExtension.class)
class QueueResultNotifyDisplayTest {

    private static final String FOO = "foo";
    @Mock
    private Icon instanceIcon;

    @Mock
    private NotificationDisplayer notificationDisplayer;

    private QueueResultNotifyDisplay classUnderTest;

    private QueueEvent event;

    @BeforeEach
    void setUp() {
        event = new QueueEvent();
        PlanVo plan = new PlanVo(FOO);
        event.setPlan(plan);
        classUnderTest = new QueueResultNotifyDisplay(instanceIcon, event) {
            @Override
            NotificationDisplayer getNotificationDisplayer() {
                return notificationDisplayer;
            }
        };
    }

    /**
     * Test of run method, of class QueueResultNotifyDisplay.
     */
    @Test
    void testRun_ResponseOk_ExpectNotify() {
        event.setResponse(Response.ok().build());
        classUnderTest.run();
        verify(notificationDisplayer).notify(anyString(), any(Icon.class), isA(JLabel.class), isA(
                ResultDetailsPanel.class), eq(Priority.NORMAL), eq(Category.INFO));
    }
    
     /**
     * Test of run method, of class QueueResultNotifyDisplay.
     */
    @Test
    void testRun_ResponseError_ExpectNotify() {
        event.setResponse(Response.serverError().build());
        classUnderTest.run();
        verify(notificationDisplayer).notify(anyString(), any(Icon.class), isA(JLabel.class), isA(
                ResultDetailsPanel.class), eq(Priority.HIGH), eq(Category.ERROR));
    }

}
