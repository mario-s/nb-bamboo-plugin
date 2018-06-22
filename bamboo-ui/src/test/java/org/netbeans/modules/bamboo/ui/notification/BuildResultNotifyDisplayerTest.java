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
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.Matchers.anyString;

import org.mockito.Mock;

import static org.mockito.Mockito.never;

import org.mockito.runners.MockitoJUnitRunner;
import org.netbeans.modules.bamboo.model.rcp.PlanVo;
import org.netbeans.modules.bamboo.model.rcp.ResultVo;
import org.netbeans.modules.bamboo.model.State;
import org.openide.awt.NotificationDisplayer;
import org.openide.awt.NotificationDisplayer.Category;
import org.openide.awt.NotificationDisplayer.Priority;

import static org.mockito.Matchers.isA;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

/**
 *
 * @author Mario Schroeder
 */
@RunWith(MockitoJUnitRunner.class)
public class BuildResultNotifyDisplayerTest {

    @Mock
    private Icon instanceIcon;

    @Mock
    private NotificationDisplayer notificationDisplayer;
    
    private BuildResult buildResult;
    
    private PlanVo plan;
    
    private ResultVo oldResult;
    
    private ResultVo newResult;

    private BuildResultNotifyDisplayer classUnderTest;

    @Before
    public void setUp() {
        oldResult = new ResultVo();
        newResult = new ResultVo();
        plan = new PlanVo("test", "test");
        buildResult = new BuildResult(plan, oldResult, newResult);
        classUnderTest = new BuildResultNotifyDisplayer(instanceIcon, buildResult) {
            @Override
            NotificationDisplayer getNotificationDisplayer() {
                return notificationDisplayer;
            }
        };
    }

    /**
     * Test of run method, of class BuildResultNotifyDisplayer.
     */
    @Test
    public void testRun_ResultNormal_ExpectNotifyNormal() {
        classUnderTest.run();
        verify(notificationDisplayer).notify(anyString(), any(Icon.class), isA(ResultDetailsPanel.class), isA(ResultDetailsPanel.class), eq(Priority.NORMAL), eq(Category.INFO));
    }

    /**
     * Test of run method, of class BuildResultNotifyDisplayer.
     */
    @Test
    public void testRun_ResultFailed_ExpectNotifyHigh() {
        ResultVo result = new ResultVo();
        result.setState(State.Failed);
        plan.setResult(result);
        classUnderTest.run();
        verify(notificationDisplayer).notify(anyString(), any(Icon.class), isA(ResultDetailsPanel.class), isA(ResultDetailsPanel.class), eq(Priority.HIGH), eq(Category.ERROR));
    }
    
    /**
     * Test of run method, of class BuildResultNotifyDisplayer.
     */
    @Test
    public void testRun_ResultStillNormal_ExpectNoNotify() {
        oldResult.setState(State.Successful);
        newResult.setState(State.Successful);
        classUnderTest.run();
        verify(notificationDisplayer, never()).notify(anyString(), any(Icon.class), isA(ResultDetailsPanel.class), isA(ResultDetailsPanel.class), eq(Priority.NORMAL), eq(Category.INFO));
    }

}
