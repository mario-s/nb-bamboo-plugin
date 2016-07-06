package org.netbeans.modules.bamboo.rest;

import org.netbeans.modules.bamboo.glue.BambooInstance;
import org.netbeans.modules.bamboo.glue.DefaultInstanceValues;
import java.util.Collection;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.Assume.assumeThat;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.times;
import org.mockito.runners.MockitoJUnitRunner;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import org.mockito.InjectMocks;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.util.reflection.Whitebox.setInternalState;

/**
 *
 * @author spindizzy
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultInstanceManagerTest {

    @Mock
    private LookupListener listener;
    @Mock
    private BambooInstanceProduceable bambooInstanceProducer;
    @Captor
    private ArgumentCaptor<LookupEvent> lookupCaptor;
    @Mock
    private Preferences preferences;

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

        classUnderTest = new DefaultInstanceManager() {
            @Override
            Preferences instancesPrefs() {
                return preferences;
            }

        };

        setInternalState(classUnderTest, "bambooInstanceProducer", bambooInstanceProducer);

        result = classUnderTest.getLookup().lookupResult(
                BambooInstance.class);

        result.addLookupListener(listener);

        values = new DefaultInstanceValues();
        values.setName(name);
        values.setUrl("");
        values.setPassword(new char[]{'a'});

        instance = new DefaultBambooInstance();
        instance.setName(values.getName());
        instance.setUrl(values.getUrl());

        given(bambooInstanceProducer.create(values)).willReturn(instance);
        given(preferences.childrenNames()).willReturn(new String[]{values.getName()});
    }

    @After
    public void shutDown() {
        result.removeLookupListener(listener);
    }

    /**
     * Test of addInstance method, of class BambooManager.
     */
    @Test
    public void testAddInstance() {
        classUnderTest.addInstance(values);
        assertThat(result.allInstances().isEmpty(), is(false));
        verify(listener).resultChanged(lookupCaptor.capture());
    }

    /**
     * Test of addInstance method, of class BambooManager.
     */
    @Test
    public void testRemoveInstance() {
        Collection<? extends BambooInstance> instances = result.allInstances();
        assumeThat(instances.isEmpty(), is(false));
        classUnderTest.removeInstance(instances.iterator().next());
        verify(listener, times(2)).resultChanged(lookupCaptor.capture());
    }

    @Test
    public void testLoadInstances() {
        classUnderTest.addInstance(values);
        classUnderTest.loadInstances();
        verify(listener, atLeast(1)).resultChanged(lookupCaptor.capture());
    }

    @Test
    public void testExistsInstance() {
        classUnderTest.addInstance(values);
        assertTrue(classUnderTest.existsInstance(name));
    }
}
