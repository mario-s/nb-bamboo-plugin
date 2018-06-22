/* 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.netbeans.modules.bamboo.model.rest;

import java.util.Collection;
import org.junit.Before;
import org.junit.Test;

import static java.util.Collections.singletonList;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;

/**
 *
 * @author Mario Schroeder
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
