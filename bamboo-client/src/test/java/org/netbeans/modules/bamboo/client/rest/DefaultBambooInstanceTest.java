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

import org.mockito.Mock;
import org.netbeans.modules.bamboo.model.rcp.ProjectVo;

import java.util.Optional;
import javax.ws.rs.core.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InOrder;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.given;

import org.openide.util.RequestProcessor.Task;
import org.netbeans.modules.bamboo.client.glue.InstanceConstants;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;

import org.netbeans.modules.bamboo.model.rcp.PlanVo;
import org.netbeans.modules.bamboo.model.event.QueueEvent;
import org.netbeans.modules.bamboo.model.rcp.BambooInstance;

import static java.util.Collections.singletonList;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.netbeans.modules.bamboo.model.rcp.ResultVo;

import static org.netbeans.modules.bamboo.model.rcp.ResultExpandParameter.Changes;

import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 *
 * @author Mario Schroeder
 */
@ExtendWith(MockitoExtension.class)
class DefaultBambooInstanceTest {

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

    DefaultBambooInstanceTest() {
        listener = mock(PropertyChangeListener.class);
    }

    @BeforeEach
    void setUp() throws IllegalAccessException {
        classUnderTest = newInstance();

        plan = new PlanVo(FOO);
        project = new ProjectVo(FOO);

        projects = new ArrayList<>();
    }

    private DefaultBambooInstance newInstance() {
        DefaultBambooInstance instance = new DefaultBambooInstance(properties);

        instance.setSyncInterval(5);
        instance.addPropertyChangeListener(listener);
        ReflectionTestUtils.setField(instance, "client", client);

        return instance;
    }

    @AfterEach
    void shutDown() {
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

    private void trainProperties() {
        given(properties.get(ArgumentMatchers.anyString())).willReturn(FOO);
        given(properties.get(InstanceConstants.PROP_SYNC_INTERVAL)).willReturn("5");
    }

    @Test
    void isAvailable_ShouldBeTrue() {
        assertTrue(classUnderTest.isAvailable());
    }

    /**
     * Test of getPreferences method, of class DefaultBambooInstance.
     */
    @Test
    void getPreferences() {
        given(properties.getPreferences()).willReturn(preferences);

        DefaultBambooInstance instance = new DefaultBambooInstance(properties);
        assertNotNull(instance.getPreferences());
    }

    /**
     * Test of applyProperties method, of class DefaultBambooInstance.
     */
    @Test
    void setProperties_WithSync() {
        trainProperties();

        DefaultBambooInstance instance = new DefaultBambooInstance(properties);
        assertEquals(5, instance.getSyncInterval());
    }

    /**
     * Test of setChildren method, of class DefaultBambooInstance.
     */
    @Test
    void setChildren_ShouldCreateTask() {
        trainProperties();

        DefaultBambooInstance instance = new DefaultBambooInstance(properties);
        instance.setChildren(projects);
        assertTrue(instance.getSynchronizationTask().isPresent());
    }

    /**
     * Test of setChildren method, of class DefaultBambooInstance.
     */
    @Test
    void setChildren_WithSuppressedPlans_ExpectPlanNotifyFalse() {
        trainProperties();
        DefaultBambooInstance instance = new DefaultBambooInstance(properties);

        project.setChildren(singletonList(plan));
        projects.add(project);

        instance.setChildren(projects);

        assertFalse(plan.isNotify());
    }

    /**
     * Test of setChildren method, of class DefaultBambooInstance.
     */
    @Test
    void setChildren_ExpectProjectsHaveParent() {
        trainProperties();

        DefaultBambooInstance instance = new DefaultBambooInstance(properties);
        instance.setChildren(projects);
        projects.forEach(pr -> {
            BambooInstance parent = pr.getParent().get();
            assertTrue(parent instanceof DefaultBambooInstance);
        });
    }

    @Test
    void synchronize_ProjectsAreEmpty_ListenerShouldBeCalled() throws InterruptedException {
        given(client.existsService()).willReturn(true);
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
    void synchronize_ProjectsAreNotEmpty_ShouldUpdateProjects() throws InterruptedException, IllegalAccessException {
        given(client.existsService()).willReturn(true);
        projects.add(project);
        ReflectionTestUtils.setField(classUnderTest, "projects", projects);
        classUnderTest.synchronize(false);
        waitForListener();

        InOrder order = inOrder(client, listener);
        order.verify(client).getVersionInfo();
        order.verify(client).updateProjects(projects);
    }

    @Test
    void synchronize_ServiceNotExisting_ExpectAvailableFalse() throws InterruptedException {
        given(client.existsService()).willReturn(false);
        classUnderTest.synchronize(false);
        waitForListener();
        assertFalse(classUnderTest.isAvailable());
    }

    @Test
    void updateSyncInterval() {
        DefaultBambooInstance instance = new DefaultBambooInstance(properties);
        instance.updateSyncInterval(1);
        Optional<Task> task = instance.getSynchronizationTask();
        assertFalse(task.get().isFinished());
    }

    @Test
    void queue_Once_ExpectOneEventInLookup() throws InterruptedException {
        given(client.existsService()).willReturn(true);
        project.setChildren(singletonList(plan));
        classUnderTest.setChildren(singletonList(project));
        classUnderTest.queue(plan);
        waitForListener();

        assertOneEvent();
    }

    @Test
    void queue_Twice_ExpectOneEventInLookup() throws InterruptedException {
        given(client.existsService()).willReturn(true);
        given(client.queue(plan)).willReturn(Response.ok().build());

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
        assertEquals(1, events.size());
    }

    @Test
    void updateNotify_NoNotify_ExpectSurpressed() {
        plan.setNotify(false);
        classUnderTest.updateNotify(plan);

        Collection<String> surpressed = classUnderTest.getSuppressedPlans();
        assertFalse(surpressed.isEmpty());
        verify(listener).propertyChange(any(PropertyChangeEvent.class));
    }

    @Test
    void updateNotify_Notify_ExpectEmptySurpressed() {
        classUnderTest.updateNotify(plan);

        Collection<String> surpressed = classUnderTest.getSuppressedPlans();
        assertTrue(surpressed.isEmpty());
        verify(listener, never()).propertyChange(any(PropertyChangeEvent.class));
    }

    @Test
    void attachChanges_ExpectClientCall() {
        given(client.existsService()).willReturn(true);
        ResultVo result = new ResultVo();
        classUnderTest.expand(result, Changes);
        verify(client).attach(result, Changes);
    }
}
