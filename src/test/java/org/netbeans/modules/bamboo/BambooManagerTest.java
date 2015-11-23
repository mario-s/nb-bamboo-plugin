package org.netbeans.modules.bamboo;

import java.util.Collection;
import static org.hamcrest.CoreMatchers.is;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.Assume.assumeThat;
import org.netbeans.modules.bamboo.model.BambooInstance;
import org.openide.util.Lookup;

/**
 *
 * @author spindizzy
 */
public class BambooManagerTest {
    
    
    private Lookup.Result<BambooInstance> result;
    
    @Before
    public void setUp() {
        result = BambooManager.Instance.getLookup().lookupResult(BambooInstance.class);
    }
    
    @After
    public void shutDown() {
        result.allInstances().forEach(i -> BambooManager.removeInstance(i));
    }

    /**
     * Test of addInstance method, of class BambooManager.
     */
    @Test
    public void testAddInstance() {
        BambooManager.addInstance("a", "", 0);
        assertThat(result.allInstances().isEmpty(), is(false));
    }
    
    /**
     * Test of addInstance method, of class BambooManager.
     */
    @Test
    public void testRemoveInstance() {
        BambooManager.addInstance("a", "", 0);
        Collection<? extends BambooInstance> instances = result.allInstances();
        assumeThat(instances.isEmpty(), is(false));
        BambooManager.removeInstance(instances.iterator().next());
    }
}
