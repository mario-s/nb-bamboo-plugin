package org.netbeans.modules.bamboo.rest;

import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.prefs.Preferences;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import static org.mockito.BDDMockito.given;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import org.mockito.runners.MockitoJUnitRunner;
import org.netbeans.modules.bamboo.glue.InstanceValues;

/**
 *
 * @author spindizzy
 */
@RunWith(MockitoJUnitRunner.class)
public class BambooInstancePropertiesTest {
    
    @Mock
    private InstanceValues values;
    @Mock
    private Preferences preferences;
    @InjectMocks
    private BambooInstanceProperties classUnderTest;
    
    
    @Before
    public void setUp() {
        given(values.getPassword()).willReturn(new char[]{'a'});
    }

    /**
     * Test of copyProperties method, of class BambooInstanceProperties.
     */
    @Test
    public void testCopyProperties() {
        classUnderTest.copyProperties(values);
        verify(values).getName();
    }

    /**
     * Test of remove method, of class BambooInstanceProperties.
     */
    @Test
    public void testRemove() {
        String key = "a";
        String expResult = "b";
        classUnderTest.put(key, expResult);
        
        String result = classUnderTest.remove(key);
        assertEquals(expResult, result);
    }

    /**
     * Test of isPersisted method, of class BambooInstanceProperties.
     */
    @Test
    @Ignore
    public void testIsPersisted() {
        boolean expResult = false;
        boolean result = classUnderTest.isPersisted();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }


    /**
     * Test of removePropertyChangeListener method, of class BambooInstanceProperties.
     */
    @Test
    @Ignore
    public void testRemovePropertyChangeListener() {
        PropertyChangeListener l = null;
        classUnderTest.removePropertyChangeListener(l);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCurrentListeners method, of class BambooInstanceProperties.
     */
    @Test
    @Ignore
    public void testGetCurrentListeners() {
        PropertyChangeListener l = null;
        classUnderTest.addPropertyChangeListener(l);
        List<PropertyChangeListener> expResult = null;
        List<PropertyChangeListener> result = classUnderTest.getCurrentListeners();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of split method, of class BambooInstanceProperties.
     */
    @Test
    @Ignore
    public void testSplit() {
        String prop = "";
        List<String> expResult = null;
        List<String> result = BambooInstanceProperties.split(prop);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of join method, of class BambooInstanceProperties.
     */
    @Test
    @Ignore
    public void testJoin() {
        List<String> pieces = null;
        String expResult = "";
        String result = BambooInstanceProperties.join(pieces);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPreferences method, of class BambooInstanceProperties.
     */
    @Test
    @Ignore
    public void testGetPreferences() {
        Preferences expResult = null;
        Preferences result = classUnderTest.getPreferences();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
