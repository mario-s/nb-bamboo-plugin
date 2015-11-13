package org.netbeans.bamboo;

import static org.hamcrest.CoreMatchers.is;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author spindizzy
 */
public class BambooManagerTest {
    
    private static final String NAME = "test";
    private static final String URL = "http://test/";
    

    /**
     * Test of addInstance method, of class BambooManager.
     */
    @Test
    public void testAddInstance() {
        BambooManager.addInstance(NAME, URL);
        assertThat(BambooManager.getInstances().isEmpty(), is(false));
    }

    /**
     * Test of removeInstance method, of class BambooManager.
     */
    @Test
    public void testRemoveInstance() {
        BambooManager.addInstance(NAME, URL);
        BambooManager.removeInstance(URL);
        assertThat(BambooManager.getInstances().isEmpty(), is(true));
    }
}
