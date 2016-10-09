
package org.netbeans.modules.bamboo.model;


import static org.hamcrest.CoreMatchers.is;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * @author schroeder
 */
public class DefaultInstanceValuesTest {
    
    private DefaultInstanceValues classUnderTest;
    
    @Before
    public void setUp() {
        classUnderTest = new DefaultInstanceValues();
        classUnderTest.setPassword(new char[]{'a'});
    }

    /**
     * Test of getName method, of class DefaultInstanceValues.
     */
    @Test
    public void testCopyConstructor() {
        DefaultInstanceValues result = new DefaultInstanceValues(classUnderTest);
        assertThat(result.getPassword().length, is(1));
    }

   
}
