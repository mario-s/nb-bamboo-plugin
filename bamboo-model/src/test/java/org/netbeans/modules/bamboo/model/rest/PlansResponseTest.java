package org.netbeans.modules.bamboo.model.rest;

import java.util.Collection;
import org.junit.Before;
import org.junit.Test;

import static java.util.Collections.singletonList;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;

/**
 *
 * @author spindizzy
 */
public class PlansResponseTest {
    
    private PlansResponse classUnderTest;
    
    @Before
    public void setUp() {
        classUnderTest = new PlansResponse();
    }

    /**
     * Test of asCollection method, of class ResultsResponse.
     */
    @Test
    public void testAsCollection_NoResults_ExpectEmptyCollection() {
        Collection<Plan> result = classUnderTest.asCollection();
        assertThat(result.isEmpty(), is(true));
    }

        /**
     * Test of asCollection method, of class ResultsResponse.
     */
    @Test
    public void testAsCollection_Results_ExpectNoEmptyCollection() {
        Plans plans = new Plans();
        plans.setPlan(singletonList(new Plan()));
        classUnderTest.setPlans(plans);
        Collection<Plan> result = classUnderTest.asCollection();
        assertThat(result.isEmpty(), is(false));
    }
}
