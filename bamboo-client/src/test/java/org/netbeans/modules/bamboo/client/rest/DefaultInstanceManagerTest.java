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
import org.netbeans.modules.bamboo.model.rcp.BambooInstance;
import org.netbeans.modules.bamboo.model.rcp.DefaultInstanceValues;
import java.util.Collection;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import static org.mockito.Mockito.times;

import org.mockito.junit.jupiter.MockitoExtension;
import org.netbeans.modules.bamboo.client.glue.BuildStatusWatchable;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import org.netbeans.modules.bamboo.mock.MockBuildStateWatcher;

import static org.openide.util.Lookup.getDefault;

import org.netbeans.modules.bamboo.client.glue.InstanceConstants;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeFalse;

/**
 *
 * @author Mario Schroeder
 */
@ExtendWith(MockitoExtension.class)
class DefaultInstanceManagerTest {
    
    private static final String FOO = "foo";
    
    private static final String FOO_URL = "http://foo.com";
    
    private static final String BAR_URL = "http://bar.com";

    @Mock
    private LookupListener listener;
    @Captor
    private ArgumentCaptor<LookupEvent> lookupCaptor;
    @Mock
    private Preferences preferences;
    @Mock
    private BambooInstanceProperties properties;
    @Mock
    private BuildStatusWatchable buildStatusWatcher;

    private DefaultInstanceManager classUnderTest;

    private Lookup.Result<BambooInstance> result;

    private DefaultInstanceValues values;

    private DefaultBambooInstance instance;
    
    @BeforeEach
    void setUp(){
        
        MockBuildStateWatcher watcher = (MockBuildStateWatcher) getDefault().lookup(BuildStatusWatchable.class);
        watcher.setDelegate(buildStatusWatcher);

        classUnderTest = createInstance();

        result = classUnderTest.getLookup().lookupResult(
                BambooInstance.class);

        result.addLookupListener(listener);

        values = new DefaultInstanceValues();
        values.setName(FOO);
        values.setUrl(FOO_URL);
        values.setPassword(new char[]{'a'});

        instance = new DefaultBambooInstance(properties);
        instance.setName(values.getName());
        instance.setUrl(values.getUrl());

    }
    
    private DefaultInstanceManager createInstance() {
        return new DefaultInstanceManager() {
            @Override
            Preferences instancesPrefs() {
                return preferences;
            }

        };
    }

    @AfterEach
    public void shutDown() {
        result.removeLookupListener(listener);
        classUnderTest.removeInstance(instance);
    }

    /**
     * Test of addInstance method, of class BambooManager.
     */
    @Test
    void testAddInstance() {
        classUnderTest.addInstance(instance);
        assertFalse(result.allInstances().isEmpty());
        verify(listener).resultChanged(lookupCaptor.capture());
    }

    /**
     * Test of addInstance method, of class BambooManager.
     */
    @Test
    void testRemoveInstance() {
        classUnderTest.addInstance(instance);
        Collection<? extends BambooInstance> instances = result.allInstances();
        assumeFalse(instances.isEmpty());
        
        classUnderTest.removeInstance(instances.iterator().next());
        verify(listener, times(2)).resultChanged(lookupCaptor.capture());
    }

    @Test
    void testLoadInstances() throws BackingStoreException {
        given(preferences.childrenNames()).willReturn(new String[]{values.getName()});
        
        classUnderTest.addInstance(instance);
        Collection<BambooInstance> instances = classUnderTest.loadInstances();
        assertFalse(instances.isEmpty());
    }

    @Test
    void testExistsInstance() throws BackingStoreException {
        classUnderTest.addInstance(instance);
        assertTrue(classUnderTest.existsInstanceName(FOO));
    }
    
    @Test
    void testExistsInstanceByUrl_NoUrl_ExpectFalse() throws BackingStoreException {
        assertFalse(classUnderTest.existsInstanceUrl(""));
    }
    
    @Test
    void testExistsInstanceByUrl_OtherUrl_ExpectFalse() throws BackingStoreException {
        classUnderTest.addInstance(instance);
        assertFalse(classUnderTest.existsInstanceUrl(BAR_URL));
    }
    
    @Test
    void testExistsInstanceByUrl_SameUrl_ExpectTrue() throws BackingStoreException {
        classUnderTest.addInstance(instance);
        assertTrue(classUnderTest.existsInstanceUrl(FOO_URL));
    }
    
    @Test
    void testPropertyChange_ShouldPersist() throws BackingStoreException {
        given(preferences.childrenNames()).willReturn(new String[]{values.getName()});
        
        PropertyChangeEvent event = new PropertyChangeEvent(instance, InstanceConstants.PROP_SYNC_INTERVAL, 0, 1);
        classUnderTest.propertyChange(event);
        Collection<BambooInstance> instances = classUnderTest.loadInstances();
        assertFalse(instances.isEmpty());
    }
}
