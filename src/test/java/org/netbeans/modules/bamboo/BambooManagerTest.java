package org.netbeans.modules.bamboo;

import java.util.Collection;
import static org.hamcrest.CoreMatchers.is;
import org.junit.After;
import org.junit.AfterClass;
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
import static org.mockito.Mockito.verify;
import org.mockito.runners.MockitoJUnitRunner;
import org.netbeans.modules.bamboo.model.BambooInstance;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;

/**
 *
 * @author spindizzy
 */
@RunWith(MockitoJUnitRunner.class)
public class BambooManagerTest {

    private static Lookup.Result<BambooInstance> result = BambooManager.Instance.getLookup().lookupResult(
                BambooInstance.class);

    @Mock
    private LookupListener listener;

    @Captor
    private ArgumentCaptor<LookupEvent> lookupCaptor;

    @Before
    public void setUp() {
        result.addLookupListener(listener);
        addInstance();
    }

    private void addInstance() {
        InstanceValues vals = new InstanceValues();
        vals.setName(getClass().getName());
        vals.setUrl("");
        BambooManager.addInstance(vals);
    }

    @After
    public void shutDown() {
        result.removeLookupListener(listener);
    }
    
    @AfterClass
    public static void destroy() {
        result.allInstances().forEach(i -> BambooManager.removeInstance(i));
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
        BambooManager.removeInstance(instances.iterator().next());
        verify(listener, times(2)).resultChanged(lookupCaptor.capture());
    }

    @Test
    public void testLoadInstances() {
        BambooManager.loadInstances();
        verify(listener, atLeast(1)).resultChanged(lookupCaptor.capture());
    }
    
    @Test
    public void testExistsInstance() {
        assertTrue(BambooManager.existsInstance(getClass().getName()));
    }
}
