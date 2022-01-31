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

import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import org.netbeans.modules.bamboo.client.glue.InstanceConstants;
import org.netbeans.modules.bamboo.model.rcp.BambooInstance;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import static org.mockito.Mockito.inOrder;

/**
 *
 * @author Mario Schroeder
 */
@ExtendWith(MockitoExtension.class)
class BambooInstancePropertiesTest {
    
    @Mock
    private BambooInstance instance;
    @Mock
    private Preferences preferences;
    @Mock
    private PropertyChangeListener listener;
    @InjectMocks
    private BambooInstanceProperties classUnderTest;

    /**
     * Test of copyProperties method, of class BambooInstanceProperties.
     */
    @Test
    @DisplayName("It should copy properties from a Bamboo instance.")
    void testCopyProperties() {
        given(instance.getToken()).willReturn(new char[]{'a', 'b'});
        classUnderTest.copyProperties(instance);
        verify(instance).getName();
    }

    /**
     * Test of remove method, of class BambooInstanceProperties.
     */
    @Test
    @DisplayName("It should allow to remove a property by key.")
    void testRemove() {
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
    @DisplayName("It should return true when instance is persisted.")
    void testIsPersisted_True() {
        assertTrue(classUnderTest.isPersisted());
    }


    /**
     * Test of getCurrentListeners method, of class BambooInstanceProperties.
     */
    @Test
    @DisplayName("It should allow to add and return listeners.")
    void testGetCurrentListeners() {
        classUnderTest.addPropertyChangeListener(listener);
        List<PropertyChangeListener> result = classUnderTest.getCurrentListeners();
        assertEquals(1, result.size());
    }

    /**
     * Test of getPreferences method, of class BambooInstanceProperties.
     */
    @Test
    @DisplayName("It shoould return preferences used to store properties.")
    void testGetPreferences() {
        String name = "foo";
        given(preferences.node(name)).willReturn(preferences);
        classUnderTest.put(InstanceConstants.PROP_NAME, name);
        assertNotNull(classUnderTest.getPreferences());
    }
    
    @Test
    @DisplayName("It should allow to clear all properties.")
    void testClear() throws BackingStoreException {
        classUnderTest.clear();
        var order = inOrder(preferences);
        order.verify(preferences).removeNode();
        order.verify(preferences).flush();
    }
    
}
