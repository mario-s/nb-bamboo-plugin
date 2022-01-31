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
package org.netbeans.modules.bamboo.model.rcp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.netbeans.modules.bamboo.model.LifeCycleState;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 *
 * @author Mario Schroeder
 */
@ExtendWith(MockitoExtension.class)
class PlanVoTest {

    private static final String FOO = "foo";
    @Mock
    private BambooInstance instance;

    @Mock
    private PropertyChangeListener listener;

    private PlanVo classUnderTest;

    @BeforeEach
    void setUp() {
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
    void testSetResult_NewLifeCycleState_ExpectEventFired() {
        ResultVo result = newResult();
        result.setLifeCycleState(LifeCycleState.Queued);
        classUnderTest.setResult(result);
        verify(listener).propertyChange(any(PropertyChangeEvent.class));
    }

    /**
     * Test of setResult method, of class PlanVo.
     */
    @Test
    void testSetResult_NewLifeCycleState_ExpectResultParentKeyIsFOO() {
        ResultVo result = newResult();
        result.setLifeCycleState(LifeCycleState.Queued);
        classUnderTest.setResult(result);
        assertEquals(result.getParent().get().getKey(), FOO);
    }

    /**
     * Test of setResult method, of class PlanVo.
     */
    @Test
    void testSetResult_SmallerNumber_ExpectEventNotFired() {
        ResultVo result = newResult();
        result.setNumber(0);
        result.setLifeCycleState(LifeCycleState.Queued);
        classUnderTest.setResult(result);
        verify(listener, never()).propertyChange(any(PropertyChangeEvent.class));
    }

    @Test
    void testSetEnable_ExpectListenerCalled() {
        classUnderTest.setEnabled(true);
        verify(listener).propertyChange(any(PropertyChangeEvent.class));
    }

    @Test
    void testSetNotify_False_ExpectListenerCalled() {
        classUnderTest.setNotify(false);
        verify(listener).propertyChange(any(PropertyChangeEvent.class));
        verify(instance).updateNotify(classUnderTest);
    }

    @Test
    void testQueue_ExpectCallToInstance() {
        classUnderTest.queue();
        verify(instance).queue(classUnderTest);
    }

    @Test
    void testIsAvailable_ParentNotPresent_ExpectFalse() {
        assertFalse(classUnderTest.isAvailable());
    }
}
