package org.netbeans.modules.bamboo.client.rest;

import java.util.prefs.Preferences;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;

/**
 *
 * @author Mario Schroeder
 */
public class PreferenceWrapperTest {
    
    
    @Before
    public void setUp() {
    }

    /**
     * Test of instancesPrefs method, of class PreferenceWrapper.
     */
    @Test
    public void testInstancesPrefs_ExpectNotNull() {
        Preferences result = PreferenceWrapper.instancesPrefs();
        assertThat(result, notNullValue());
    }
    
}
