package org.netbeans.modules.bamboo.rest;

import java.util.Collection;
import java.util.Collections;
import static java.util.Collections.emptyList;
import java.util.prefs.Preferences;
import static org.hamcrest.CoreMatchers.is;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import static org.mockito.BDDMockito.given;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.netbeans.modules.bamboo.glue.BuildProject;
import org.netbeans.modules.bamboo.glue.InstanceValues;

/**
 *
 * @author spindizzy
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultBambooInstanceTest {
    
    @Mock
    private InstanceValues values;
    @Mock
    private BambooInstanceProperties properties;
    @Mock
    private Preferences preferences;
    @InjectMocks
    private DefaultBambooInstance classUnderTest;
    
    private Collection<BuildProject> projects;
    
    @Before
    public void setUp() {
        given(properties.getPreferences()).willReturn(preferences);
        projects = emptyList();
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
        given(properties.get(BambooInstanceConstants.INSTANCE_SYNC)).willReturn("5");
        classUnderTest.applyProperties(properties);
        assertEquals(5, classUnderTest.getSyncInterval());
    }
    
     /**
     * Test of setProjects method, of class DefaultBambooInstance.
     */
    @Test
    public void testSetProjects_ShouldCreateTask() {
        given(properties.get(BambooInstanceConstants.INSTANCE_SYNC)).willReturn("5");
        classUnderTest.applyProperties(properties);
        classUnderTest.setProjects(projects);
        assertThat(classUnderTest.getSynchronizationTask().isPresent(), is(true));
    }
}
