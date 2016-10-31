package org.netbeans.modules.bamboo.model.rest;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 *
 * @author spindizzy
 */
public class ResultTest {
    
    private static final String FOO = "foo";
    
    private Result classUnderTest;
    
    @Before
    public void setUp() {
        classUnderTest = new Result();
        classUnderTest.setKey(FOO);
    }

    /**
     * Test of equals method, of class Plan.
     */
    @Test
    public void testEquals() {
        Result other = new Result();
        boolean result = classUnderTest.equals(other);
        assertThat(result, is(false));
    }

  
}
