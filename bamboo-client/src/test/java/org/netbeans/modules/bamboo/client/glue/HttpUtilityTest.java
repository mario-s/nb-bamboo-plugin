package org.netbeans.modules.bamboo.client.glue;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 *
 * @author Mario Schroeder
 */
public class HttpUtilityTest {
    
    private HttpUtility classUnderTest;
    
    @Before
    public void setUp() {
        classUnderTest = new HttpUtility();
    }

    /**
     * Test of exists method, of class HttpUtility.
     */
    @Test
    public void testExists_False() {
        boolean result = classUnderTest.exists("foo");
        assertThat(result, is(false));
    }
    
    /**
     * Test of exists method, of class HttpUtility.
     */
    @Test
    public void testExists_True() {
        boolean result = classUnderTest.exists("http://netbeans.org");
        assertThat(result, is(true));
    }
    
}
