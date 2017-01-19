package org.netbeans.modules.bamboo.ui.wizard;

import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import static org.mockito.BDDMockito.given;

import org.mockito.InOrder;

import static org.mockito.Matchers.anyString;

import org.mockito.Mock;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;

import static org.mockito.internal.util.reflection.Whitebox.setInternalState;

import org.mockito.runners.MockitoJUnitRunner;

import org.netbeans.modules.bamboo.model.rcp.BambooInstance;
import org.netbeans.modules.bamboo.client.glue.InstanceManageable;
import org.netbeans.modules.bamboo.mock.MockInstanceFactory;
import org.netbeans.modules.bamboo.client.glue.BambooInstanceProduceable;

import static org.openide.util.Lookup.getDefault;

import org.openide.util.Task;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.netbeans.modules.bamboo.model.rcp.DefaultInstanceValues;

import static org.mockito.Mockito.atLeast;

import org.openide.NotifyDescriptor;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.never;

/**
 * @author Mario Schroeder
 */
@RunWith(MockitoJUnitRunner.class)
public class AddInstanceWorkerTest {

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

    @Before
    public void setUp() {
        MockInstanceFactory factory
                = (MockInstanceFactory) getDefault().lookup(BambooInstanceProduceable.class);
        factory.setDelegate(producer);

        given(action.getInstanceManager()).willReturn(instanceManager);
        given(form.getInstanceName()).willReturn("test");

        classUnderTest = new AddInstanceWorker(action) {
            @Override
            Runner newRunner(DefaultInstanceValues values) {
                return runner;
            }
        };
    }

    @Test
    public void testExecute_Cancel() {
        classUnderTest.execute(form);
        classUnderTest.cancel();
        verify(runner).addPropertyChangeListener(isA(PropertyChangeListener.class));
        verify(form).getPassword();
    }

    @Test
    public void testPropertyChangeEvent_InstanceCreated() {
        String eventName = WorkerEvents.INSTANCE_CREATED.name();
        classUnderTest.propertyChange(newEvent(eventName));

        InOrder order = inOrder(instanceManager, action);
        order.verify(instanceManager).addInstance(instance);
        order.verify(action).firePropertyChange(eventName, null, NotifyDescriptor.OK_OPTION);
    }
    
    @Test
    public void testPropertyChangeEvent_InvalidUrl() {
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
    public void testTaskFinished_Cancel() {
        classUnderTest.execute(form);
        setInternalState(classUnderTest, "cancel", true);

        Task task = new Task(null);
        classUnderTest.taskFinished(task);
        verify(instanceManager, atLeast(1)).removeInstance(anyString());
    }
}
