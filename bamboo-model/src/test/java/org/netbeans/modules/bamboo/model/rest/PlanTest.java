package org.netbeans.modules.bamboo.model.rest;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 *
 * @author spindizzy
 */
public class PlanTest {
    
    private static final String FOO = "foo";
    
    private Plan classUnderTest;
    
    @Before
    public void setUp() {
        classUnderTest = new Plan();
        classUnderTest.setKey(FOO);
    }

    /**
     * Test of equals method, of class Plan.
     */
    @Test
    public void testEquals() {
        Plan other = new Plan();
        boolean result = classUnderTest.equals(other);
        assertThat(result, is(false));
    }

  
}
