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
package org.netbeans.modules.bamboo.client.rest;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collection;

import java.util.prefs.Preferences;
import org.junit.After;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.netbeans.modules.bamboo.model.rcp.ProjectVo;

import java.util.Optional;
import javax.ws.rs.core.Response;
import org.mockito.InOrder;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;

import org.openide.util.RequestProcessor.Task;
import org.netbeans.modules.bamboo.client.glue.InstanceConstants;

import static org.mockito.Mockito.inOrder;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.internal.util.reflection.Whitebox.setInternalState;

import org.netbeans.modules.bamboo.model.rcp.PlanVo;
import org.netbeans.modules.bamboo.model.event.QueueEvent;

import static java.util.Collections.singletonList;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import org.mockito.internal.util.reflection.Whitebox;

import org.netbeans.modules.bamboo.model.rcp.ResultVo;

import static org.netbeans.modules.bamboo.model.rcp.ResultExpandParameter.Changes;

/**
 *
 * @author Mario Schroeder
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultBambooInstanceTest {

    private static final String FOO = "foo";

    @Mock
    private BambooInstanceProperties properties;

    @Mock
    private Preferences preferences;

    @Mock
    private AbstractBambooClient client;

    private PlanVo plan;

    private ProjectVo project;

    private final PropertyChangeListener listener;

    private DefaultBambooInstance classUnderTest;

    private Collection<ProjectVo> projects;

    public DefaultBambooInstanceTest() {
        listener = mock(PropertyChangeListener.class);
    }

    @Before
    public void setUp() {
        classUnderTest = newInstance();

        plan = new PlanVo(FOO);
        project = new ProjectVo(FOO);
        
        reset(properties, preferences, client);

        given(properties.getPreferences()).willReturn(preferences);
        given(client.existsService()).willReturn(true);
        given(client.queue(plan)).willReturn(Response.ok().build());

        projects = new ArrayList<>();
    }

    private DefaultBambooInstance newInstance() {
        DefaultBambooInstance instance = new DefaultBambooInstance(properties);

        instance.setSyncInterval(5);
        instance.addPropertyChangeListener(listener);
        setInternalState(instance, "client", client);

        return instance;
    }

    @After
    public void shutDown() {
        classUnderTest.removePropertyChangeListener(listener);
    }

    private void waitForListener() throws InterruptedException {
        waitForListener(1000);
    }

    private void waitForListener(long timeout) throws InterruptedException {
        synchronized (listener) {
            listener.wait(timeout);
        }
    }

    @Test
    public void isAvailable_ShouldBeTrue() {
        boolean available = classUnderTest.isAvailable();
        assertThat(available, is(true));
    }

    /**
     * Test of getPreferences method, of class DefaultBambooInstance.
     */
    @Test
    public void getPreferences() {
        DefaultBambooInstance instance = new DefaultBambooInstance(properties);
        Preferences result = instance.getPreferences();
        assertNotNull(result);
    }

    /**
     * Test of applyProperties method, of class DefaultBambooInstance.
     */
    @Test
    public void setProperties_WithSync() {
        given(properties.get(InstanceConstants.PROP_SYNC_INTERVAL)).willReturn("5");

        DefaultBambooInstance instance = new DefaultBambooInstance(properties);
        assertEquals(5, instance.getSyncInterval());
    }

    /**
     * Test of setChildren method, of class DefaultBambooInstance.
     */
    @Test
    public void setChildren_ShouldCreateTask() {
        given(properties.get(InstanceConstants.PROP_SYNC_INTERVAL)).willReturn("5");

        DefaultBambooInstance instance = new DefaultBambooInstance(properties);
        instance.setChildren(projects);
        assertThat(instance.getSynchronizationTask().isPresent(), is(true));
    }

    /**
     * Test of setChildren method, of class DefaultBambooInstance.
     */
    @Test
    public void setChildren_WithSuppressedPlans_ExpectPlanNotifyFalse() {
        given(properties.get(BambooInstanceConstants.INSTANCE_SUPPRESSED_PLANS)).willReturn(FOO);
        DefaultBambooInstance instance = new DefaultBambooInstance(properties);

        project.setChildren(singletonList(plan));
        projects.add(project);

        instance.setChildren(projects);

        assertThat(plan.isNotify(), is(false));
    }

    /**
     * Test of setChildren method, of class DefaultBambooInstance.
     */
    @Test
    public void setChildren_ExpectProjectsHaveParent() {
        given(properties.get(InstanceConstants.PROP_SYNC_INTERVAL)).willReturn("5");

        DefaultBambooInstance instance = new DefaultBambooInstance(properties);
        instance.setChildren(projects);
        projects.forEach(pr -> {
            assertThat(pr.getParent().get(), is(instance));
        });
    }

    @Test
    public void synchronize_ProjectsAreEmpty_ListenerShouldBeCalled() throws InterruptedException {
        projects.add(project);
        given(client.getProjects()).willReturn(projects);
        classUnderTest.synchronize(false);
        waitForListener();
        
        InOrder order = inOrder(client, listener);
        order.verify(client).getVersionInfo();
        order.verify(client).getProjects();
        order.verify(listener).propertyChange(any(PropertyChangeEvent.class));
    }
    
    @Test
    public void synchronize_ProjectsAreNotEmpty_ShouldUpdateProjects() throws InterruptedException {
        projects.add(project);
        Whitebox.setInternalState(classUnderTest, "projects", projects);
        classUnderTest.synchronize(false);
        waitForListener();
        
        InOrder order = inOrder(client, listener);
        order.verify(client).getVersionInfo();
        order.verify(client).updateProjects(projects);
    }

    @Test
    public void synchronize_ServiceNotExisting_ExpectAvailableFalse() throws InterruptedException {
        given(client.existsService()).willReturn(false);
        classUnderTest.synchronize(false);
        waitForListener();
        boolean available = classUnderTest.isAvailable();
        assertThat(available, is(false));
    }

    @Test
    public void updateSyncInterval() {
        DefaultBambooInstance instance = new DefaultBambooInstance(properties);
        instance.updateSyncInterval(1);
        Optional<Task> task = instance.getSynchronizationTask();
        assertThat(task.get().isFinished(), is(false));
    }

    @Test
    public void queue_Once_ExpectOneEventInLookup() throws InterruptedException {
        project.setChildren(singletonList(plan));
        classUnderTest.setChildren(singletonList(project));
        classUnderTest.queue(plan);
        waitForListener();

        assertOneEvent();
    }
    
    @Test
    public void queue_Twice_ExpectOneEventInLookup() throws InterruptedException {
        project.setChildren(singletonList(plan));
        classUnderTest.setChildren(singletonList(project));
        classUnderTest.queue(plan);
        waitForListener();
        classUnderTest.queue(plan);
        waitForListener();

        assertOneEvent();
    }

    private void assertOneEvent() {
        Collection<? extends QueueEvent> events = classUnderTest.getLookup().lookupAll(QueueEvent.class);
        assertThat(events.size(), is(1));
    }

    @Test
    public void updateNotify_NoNotify_ExpectSurpressed() {
        plan.setNotify(false);
        classUnderTest.updateNotify(plan);

        Collection<String> surpressed = classUnderTest.getSuppressedPlans();
        assertThat(surpressed.isEmpty(), is(false));
        verify(listener).propertyChange(any(PropertyChangeEvent.class));
    }

    @Test
    public void updateNotify_Notify_ExpectEmptySurpressed() {
        classUnderTest.updateNotify(plan);

        Collection<String> surpressed = classUnderTest.getSuppressedPlans();
        assertThat(surpressed.isEmpty(), is(true));
        verify(listener, never()).propertyChange(any(PropertyChangeEvent.class));
    }

    @Test
    public void attachChanges_ExpectClientCall() {
        ResultVo result = new ResultVo();
        classUnderTest.expand(result, Changes);
        verify(client).attach(result, Changes);
    }
}
