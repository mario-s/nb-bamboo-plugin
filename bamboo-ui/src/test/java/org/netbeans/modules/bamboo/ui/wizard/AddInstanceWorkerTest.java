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
package org.netbeans.modules.bamboo.ui.wizard;

import org.mockito.InOrder;

import org.mockito.Mock;

import org.netbeans.modules.bamboo.model.rcp.BambooInstance;
import org.netbeans.modules.bamboo.client.glue.InstanceManageable;
import org.netbeans.modules.bamboo.mock.MockInstanceFactory;
import org.netbeans.modules.bamboo.client.glue.BambooInstanceProduceable;

import org.openide.util.Task;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.netbeans.modules.bamboo.model.rcp.DefaultInstanceValues;
import org.openide.NotifyDescriptor;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.openide.util.Lookup.getDefault;

/**
 * @author Mario Schroeder
 */
@ExtendWith(MockitoExtension.class)
class AddInstanceWorkerTest {

    @Mock
    private AbstractDialogAction action;
    @Mock
    private InstanceManageable instanceManager;
    @Mock
    private InstancePropertiesForm form;
    @Mock
    private BambooInstance instance;
    @Mock
    private BambooInstanceProduceable producer;
    @Mock
    private Runner runner;

    private AddInstanceWorker classUnderTest;

    @BeforeEach
    void setUp() {
        MockInstanceFactory factory
                = (MockInstanceFactory) getDefault().lookup(BambooInstanceProduceable.class);
        factory.setDelegate(producer);

        given(action.getInstanceManager()).willReturn(instanceManager);

        classUnderTest = new AddInstanceWorker(action) {
            @Override
            Runner newRunner(DefaultInstanceValues values) {
                return runner;
            }
        };
    }

    @Test
    void testExecute_Cancel() {
        classUnderTest.execute(form);
        classUnderTest.cancel();
        verify(runner).addPropertyChangeListener(isA(PropertyChangeListener.class));
    }

    @Test
    void testPropertyChangeEvent_InstanceCreated() {
        String eventName = WorkerEvents.INSTANCE_CREATED.name();
        classUnderTest.propertyChange(newEvent(eventName));

        InOrder order = inOrder(instanceManager, action);
        order.verify(instanceManager).addInstance(instance);
        order.verify(action).firePropertyChange(eventName, null, NotifyDescriptor.OK_OPTION);
    }
    
    @Test
    void testPropertyChangeEvent_InvalidUrl() {
        String eventName = WorkerEvents.INVALID_URL.name();
        PropertyChangeEvent event = newEvent(eventName);
        classUnderTest.propertyChange(event);

        verify(instanceManager, never()).addInstance(instance);
        verify(action).firePropertyChange(event);
    }

    private PropertyChangeEvent newEvent(String eventName) {
        return new PropertyChangeEvent(this, eventName, null, instance);
    }

    @Test
    void testTaskFinished_Cancel() {
        given(form.getInstanceName()).willReturn("test");
        
        classUnderTest.execute(form);
        ReflectionTestUtils.setField(classUnderTest, "cancel", true);

        Task task = new Task(null);
        classUnderTest.taskFinished(task);
        verify(instanceManager, atLeast(1)).removeInstance(anyString());
    }
}
