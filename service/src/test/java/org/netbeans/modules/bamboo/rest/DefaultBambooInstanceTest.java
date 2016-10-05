package org.netbeans.modules.bamboo.rest;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collection;

import java.util.prefs.Preferences;
import org.junit.After;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.netbeans.modules.bamboo.glue.SharedConstants;
import org.netbeans.modules.bamboo.model.ProjectVo;

import static org.hamcrest.CoreMatchers.is;
import static java.util.Collections.emptyList;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.openide.util.RequestProcessor.Task;

/**
 *
 * @author spindizzy
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultBambooInstanceTest {

    @Mock
    private BambooInstanceProperties properties;
    @Mock
    private Preferences preferences;

    private final PropertyChangeListener listener;
    @InjectMocks
    private DefaultBambooInstance classUnderTest;

    private Collection<ProjectVo> projects;

    public DefaultBambooInstanceTest() {
        listener = mock(PropertyChangeListener.class);
    }

    @Before
    public void setUp() {
        classUnderTest.setSyncInterval(5);
        classUnderTest.addPropertyChangeListener(listener);
        given(properties.getPreferences()).willReturn(preferences);
        projects = emptyList();
    }
    
    @After
    public void shutDown() {
        classUnderTest.removePropertyChangeListener(listener);
    }

    /**
     * Test of getPreferences method, of class DefaultBambooInstance.
     */
    @Test
    public void testGetPreferences() {
        classUnderTest.applyProperties(properties);
        Preferences result = classUnderTest.getPreferences();
        assertNotNull(result);
    }

    /**
     * Test of applyProperties method, of class DefaultBambooInstance.
     */
    @Test
    public void testSetProperties_WithSync() {
        given(properties.get(SharedConstants.PROP_SYNC_INTERVAL)).willReturn("5");
        classUnderTest.applyProperties(properties);
        assertEquals(5, classUnderTest.getSyncInterval());
    }

    /**
     * Test of setProjects method, of class DefaultBambooInstance.
     */
    @Test
    public void testSetProjects_ShouldCreateTask() {
        given(properties.get(SharedConstants.PROP_SYNC_INTERVAL)).willReturn("5");
        classUnderTest.applyProperties(properties);
        classUnderTest.setProjects(projects);
        assertThat(classUnderTest.getSynchronizationTask().isPresent(), is(true));
    }

    @Test
    public void testSynchronize_ListenerShouldBeCalled() throws InterruptedException {
        classUnderTest.synchronize();
        synchronized (listener) {
            listener.wait(1000);
        }
        verify(listener).propertyChange(any(PropertyChangeEvent.class));
    }
    
    @Test
    public void testUpdateSyncInterval() {
        classUnderTest.applyProperties(properties);
        classUnderTest.updateSyncInterval(1);
        Optional<Task> task = classUnderTest.getSynchronizationTask();
        assertThat(task.get().isFinished(), is(false));
    }
}
