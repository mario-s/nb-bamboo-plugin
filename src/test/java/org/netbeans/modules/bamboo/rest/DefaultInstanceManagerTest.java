package org.netbeans.modules.bamboo.rest;

import org.netbeans.modules.bamboo.glue.BambooInstance;
import org.netbeans.modules.bamboo.rest.DefaultInstanceManager;
import org.netbeans.modules.bamboo.rest.DefaultInstanceValues;
import java.util.Collection;
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
import static org.mockito.Mockito.verify;

/**
 *
 * @author spindizzy
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultInstanceManagerTest {

    private DefaultInstanceManager classUnderTest;
    
     private Lookup.Result<BambooInstance> result;

    @Mock
    private LookupListener listener;

    @Captor
    private ArgumentCaptor<LookupEvent> lookupCaptor;

    @Before
    public void setUp() {
        classUnderTest = new DefaultInstanceManager();
        result = classUnderTest.getLookup().lookupResult(
                BambooInstance.class);
        
        result.addLookupListener(listener);
        addInstance();
    }

    private void addInstance() {
        DefaultInstanceValues vals = new DefaultInstanceValues();
        vals.setName(getClass().getName());
        vals.setUrl("");
        vals.setPassword(new char[]{'a'});
        classUnderTest.addInstance(vals);
    }

    @After
    public void shutDown() {
        result.removeLookupListener(listener);
        result.allInstances().forEach(i -> classUnderTest.removeInstance(i));
    }
    

    /**
     * Test of addInstance method, of class BambooManager.
     */
    @Test
    public void testAddInstance() {
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
        classUnderTest.loadInstances();
        verify(listener, atLeast(1)).resultChanged(lookupCaptor.capture());
    }
    
    @Test
    @Ignore("FIXME")
    public void testExistsInstance() {
        assertTrue(classUnderTest.existsInstance(getClass().getName()));
    }
}
