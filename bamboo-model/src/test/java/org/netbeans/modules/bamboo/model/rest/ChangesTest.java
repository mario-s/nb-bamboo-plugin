
package org.netbeans.modules.bamboo.model.rest;

import java.util.Collection;
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author schroeder
 */
public class ChangesTest {
    
    private Changes classUnderTest;
    
    @Before
    public void setUp() {
        classUnderTest = new Changes();
    }

    /**
     * Test of asCollection method, of class Changes.
     */
    @Test
    public void testAsCollection_NullChange_ExpectEmptyCollection() {
        Collection<Change> result = classUnderTest.asCollection();
        assertThat(result.isEmpty(), is(true));
    }

      /**
     * Test of asCollection method, of class Changes.
     */
    @Test
    public void testAsCollection_Change_ExpectNonEmptyCollection() {
        classUnderTest.setChange(singletonList(new Change()));
        Collection<Change> result = classUnderTest.asCollection();
        assertThat(result.isEmpty(), is(false));
    }


    
}
