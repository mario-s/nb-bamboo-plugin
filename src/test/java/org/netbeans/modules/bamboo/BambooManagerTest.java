package org.netbeans.modules.bamboo;

import static org.hamcrest.CoreMatchers.is;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
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


    /**
     * Test of addInstance method, of class BambooManager.
     */
    @Test
    public void testAddInstance() {
        String name = "a";
        String url = "";
        int sync = 0;
        BambooManager.addInstance(name, url, sync);
        assertThat(result.allItems().isEmpty(), is(false));
    }
    
}
