package org.netbeans.modules.bamboo.rest;

import java.util.prefs.Preferences;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import static org.mockito.BDDMockito.given;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
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
    DefaultBambooInstance classUnderTest;
    
    @Before
    public void setUp() {
        given(properties.getPreferences()).willReturn(preferences);
    }

    /**
     * Test of getPreferences method, of class DefaultBambooInstance.
     */
    @Test
    public void testGetPreferences() {
        classUnderTest.setProperties(properties);
        Preferences result = classUnderTest.getPreferences();
        assertNotNull(result);
    }

    /**
     * Test of setProperties method, of class DefaultBambooInstance.
     */
    @Test
    public void testSetProperties_WithSync() {
        given(properties.get(BambooInstanceConstants.INSTANCE_SYNC)).willReturn("5");
        classUnderTest.setProperties(properties);
        assertEquals(5, classUnderTest.getSyncInterval());
    }
}
