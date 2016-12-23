package org.netbeans.modules.bamboo.client.rest;

import java.beans.PropertyChangeEvent;
import org.netbeans.modules.bamboo.model.rcp.BambooInstance;
import org.netbeans.modules.bamboo.model.rcp.DefaultInstanceValues;
import java.util.Collection;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assume.assumeThat;

import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import static org.mockito.Mockito.times;

import org.mockito.runners.MockitoJUnitRunner;
import org.netbeans.modules.bamboo.client.glue.BuildStatusWatchable;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import org.netbeans.modules.bamboo.mock.MockBuildStateWatcher;

import static org.openide.util.Lookup.getDefault;

import org.netbeans.modules.bamboo.client.glue.InstanceConstants;

/**
 *
 * @author spindizzy
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultInstanceManagerTest {

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
    
    private final String name;

    public DefaultInstanceManagerTest() {
        name = getClass().getName();
    }

    @Before
    public void setUp() throws BackingStoreException {
        
        MockBuildStateWatcher watcher = (MockBuildStateWatcher) getDefault().lookup(BuildStatusWatchable.class);
        watcher.setDelegate(buildStatusWatcher);

        classUnderTest = new DefaultInstanceManager() {
            @Override
            Preferences instancesPrefs() {
                return preferences;
            }

        };

        result = classUnderTest.getLookup().lookupResult(
                BambooInstance.class);

        result.addLookupListener(listener);

        values = new DefaultInstanceValues();
        values.setName(name);
        values.setUrl("");
        values.setPassword(new char[]{'a'});

        instance = new DefaultBambooInstance(properties);
        instance.setName(values.getName());
        instance.setUrl(values.getUrl());

        given(preferences.childrenNames()).willReturn(new String[]{values.getName()});
    }

    @After
    public void shutDown() {
        result.removeLookupListener(listener);
        classUnderTest.removeInstance(instance);
    }

    /**
     * Test of addInstance method, of class BambooManager.
     */
    @Test
    public void testAddInstance() {
        classUnderTest.addInstance(instance);
        assertThat(result.allInstances().isEmpty(), is(false));
        verify(listener).resultChanged(lookupCaptor.capture());
    }

    /**
     * Test of addInstance method, of class BambooManager.
     */
    @Test
    public void testRemoveInstance() {
        classUnderTest.addInstance(instance);
        Collection<? extends BambooInstance> instances = result.allInstances();
        assumeThat(instances.isEmpty(), is(false));
        classUnderTest.removeInstance(instances.iterator().next());
        verify(listener, times(2)).resultChanged(lookupCaptor.capture());
    }

    @Test
    public void testLoadInstances() {
        classUnderTest.addInstance(instance);
        Collection<BambooInstance> instances = classUnderTest.loadInstances();
        assertThat(instances.isEmpty(), is(false));
    }

    @Test
    public void testExistsInstance() throws BackingStoreException {
        given(preferences.nodeExists(name)).willReturn(true);
        classUnderTest.addInstance(instance);
        assertThat(classUnderTest.existsInstance(name), is(true));
    }
    
    @Test
    public void testPropertyChange_ShouldPersist() {
        PropertyChangeEvent event = new PropertyChangeEvent(instance, InstanceConstants.PROP_SYNC_INTERVAL, 0, 1);
        classUnderTest.propertyChange(event);
        Collection<BambooInstance> instances = classUnderTest.loadInstances();
        assertThat(instances.isEmpty(), is(false));
    }
}
