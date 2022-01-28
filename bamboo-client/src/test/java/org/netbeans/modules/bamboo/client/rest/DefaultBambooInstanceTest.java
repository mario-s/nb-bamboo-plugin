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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.given;

import org.openide.util.RequestProcessor.Task;
import org.netbeans.modules.bamboo.client.glue.InstanceConstants;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Matchers.any;

import org.netbeans.modules.bamboo.model.rcp.PlanVo;
import org.netbeans.modules.bamboo.model.event.QueueEvent;
import org.netbeans.modules.bamboo.model.rcp.BambooInstance;

import static java.util.Collections.singletonList;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.netbeans.modules.bamboo.model.rcp.ResultVo;


import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.netbeans.modules.bamboo.model.rcp.ResultExpandParameter.CHANGES;

/**
 *
 * @author Mario Schroeder
 */
@ExtendWith(MockitoExtension.class)
class DefaultBambooInstanceTest {
    
    private static final long TIMEOUT = 1000;

    private static final String FOO = "foo";

    @Mock
    private BambooInstanceProperties properties;

    @Mock
    private Preferences preferences;

    @Mock
    private AbstractBambooClient client;
    
    @Mock
    private PropertyChangeListener listener;

    private PlanVo plan;

    private ProjectVo project;

    private DefaultBambooInstance classUnderTest;

    private Collection<ProjectVo> projects;
    

    @BeforeEach
    void setUp() throws IllegalAccessException {
        given(properties.get(anyString())).willReturn(FOO);
        given(properties.get(BambooInstanceConstants.INSTANCE_USE_TOKEN)).willReturn("true");
        given(properties.get(InstanceConstants.PROP_SYNC_INTERVAL)).willReturn("1");
        
        classUnderTest = new DefaultBambooInstance(properties);
        classUnderTest.addPropertyChangeListener(listener);
        ReflectionTestUtils.setField(classUnderTest, "client", client);

        plan = new PlanVo(FOO);
        project = new ProjectVo(FOO);
        projects = new ArrayList<>();
    }

    @AfterEach
    void shutDown() {
        classUnderTest.removePropertyChangeListener(listener);
    }
    
    @Test
    @DisplayName("It should copy token from properties.")
    void copyToken() {
        assertAll(
                () -> assertTrue(classUnderTest.isUseToken(), "expected to use token"),
                () -> assertNotNull(classUnderTest.getToken()),
                () -> assertFalse(classUnderTest.getToken().length == 0, "expected characters")
        );
    }

    @Test
    @DisplayName("It should return true when instance is available.")
    void isAvailable_ShouldBeTrue() {
        assertTrue(classUnderTest.isAvailable());
    }

    /**
     * Test of getPreferences method, of class DefaultBambooInstance.
     */
    @Test
    @DisplayName("It should return preferences based on the properties.")
    void getPreferences() {
        given(properties.getPreferences()).willReturn(preferences);

        DefaultBambooInstance instance = new DefaultBambooInstance(properties);
        assertNotNull(instance.getPreferences());
    }

    /**
     * Test of applyProperties method, of class DefaultBambooInstance.
     */
    @Test
    @DisplayName("It should return syncintervall based on the properties.")
    void setProperties_WithSync() {
        given(properties.get(InstanceConstants.PROP_SYNC_INTERVAL)).willReturn(null);
        DefaultBambooInstance instance = new DefaultBambooInstance(properties);
        assertEquals(0, instance.getSyncInterval());
    }

    /**
     * Test of setChildren method, of class DefaultBambooInstance.
     */
    @Test
    @DisplayName("It should create tasks when children are set.")
    void setChildren_ShouldCreateTask() {
        DefaultBambooInstance instance = new DefaultBambooInstance(properties);
        instance.setChildren(projects);
        assertTrue(instance.getSynchronizationTask().isPresent());
    }

    /**
     * Test of setChildren method, of class DefaultBambooInstance.
     */
    @Test
    @DisplayName("It should not notify about a plan if it is disabled.")
    void setChildren_WithSuppressedPlans_ExpectPlanNotifyFalse() {
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
    @DisplayName("It should return parents for projects.")
    void setChildren_ExpectProjectsHaveParent() {

        DefaultBambooInstance instance = new DefaultBambooInstance(properties);
        instance.setChildren(projects);
        projects.forEach(pr -> {
            BambooInstance parent = pr.getParent().get();
            assertTrue(parent instanceof DefaultBambooInstance);
        });
    }

    @Test
    @DisplayName("It should call listener when empty projects have been synchronized.")
    void synchronize_ProjectsAreEmpty_ListenerShouldBeCalled() throws InterruptedException {
        given(client.existsService()).willReturn(true);
        projects.add(project);
        given(client.getProjects()).willReturn(projects);
        classUnderTest.synchronize(false).waitFinished(TIMEOUT);
        
        InOrder order = inOrder(client, listener);
        order.verify(client).getVersionInfo();
        order.verify(client).getProjects();
        order.verify(listener).propertyChange(any(PropertyChangeEvent.class));
    }

    @Test
    @DisplayName("It should call listener when not empty projects have been synchronized.")
    void synchronize_ProjectsAreNotEmpty_ShouldUpdateProjects() throws InterruptedException, IllegalAccessException {
        given(client.existsService()).willReturn(true);
        projects.add(project);
        ReflectionTestUtils.setField(classUnderTest, "projects", projects);
        classUnderTest.synchronize(false).waitFinished(TIMEOUT);

        InOrder order = inOrder(client, listener);
        order.verify(client).getVersionInfo();
        order.verify(client).updateProjects(projects);
    }

    @Test
    @DisplayName("It should return false for a instance that is not available.")
    void synchronize_ServiceNotExisting_ExpectAvailableFalse() throws InterruptedException {
        given(client.existsService()).willReturn(false);
        classUnderTest.synchronize(false).waitFinished(TIMEOUT);
        
        assertFalse(classUnderTest.isAvailable());
    }

    @Test
    @DisplayName("It should perform a synchronization task in an interval.")
    void updateSyncInterval() {
        DefaultBambooInstance instance = new DefaultBambooInstance(properties);
        instance.updateSyncInterval(1);
        Optional<Task> task = instance.getSynchronizationTask();
        assertFalse(task.get().isFinished());
    }

    @Test
    @DisplayName("It should queue an event which should be present in Lookup.")
    void queue_Once_ExpectOneEventInLookup() throws InterruptedException {
        given(client.existsService()).willReturn(true);
        project.setChildren(singletonList(plan));
        classUnderTest.setChildren(singletonList(project));
        classUnderTest.queue(plan).waitFinished(TIMEOUT);

        assertEquals(1, classUnderTest.getLookup().lookupAll(QueueEvent.class).size());
    }

    @Test
    @DisplayName("It should queue same event which should be present only once in Lookup.")
    void queue_Twice_ExpectOneEventInLookup() throws InterruptedException {
        given(client.existsService()).willReturn(true);
        given(client.queue(plan)).willReturn(Response.ok().build());

        project.setChildren(singletonList(plan));
        classUnderTest.setChildren(singletonList(project));
        classUnderTest.queue(plan).waitFinished(TIMEOUT);
        classUnderTest.queue(plan).waitFinished(TIMEOUT);

        assertEquals(1, classUnderTest.getLookup().lookupAll(QueueEvent.class).size());
    }


    @Test
    @DisplayName("It should not notify for surpressed plans.")
    void updateNotify_NoNotify_ExpectSurpressed() {
        plan.setNotify(false);
        classUnderTest.updateNotify(plan);

        Collection<String> surpressed = classUnderTest.getSuppressedPlans();
        assertFalse(surpressed.isEmpty());
        verify(listener, never()).propertyChange(any(PropertyChangeEvent.class));
    }

    @Test
    @DisplayName("It should not notify for surpressed plans.")
    void updateNotify_Notify_ExpectEmptySurpressed() {
        classUnderTest.updateNotify(plan);

        Collection<String> surpressed = classUnderTest.getSuppressedPlans();
        assertTrue(surpressed.isEmpty());
        verify(listener).propertyChange(any(PropertyChangeEvent.class));
    }

    @Test
    @DisplayName("It should call client to attach changes when results are expanded.")
    void attachChanges_ExpectClientCall() {
        given(client.existsService()).willReturn(true);
        ResultVo result = new ResultVo();
        classUnderTest.expand(result, CHANGES);
        verify(client).attach(result, CHANGES);
    }
}
