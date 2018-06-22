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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import static org.hamcrest.CoreMatchers.is;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Mario Schroeder
 */
public class ProjectTest {
    private static final String FOO = "foo";
    
    private Project classUnderTest;
    
    @Before
    public void setUp() {
        classUnderTest = new Project();
        classUnderTest.setKey(FOO);
        classUnderTest.setName(FOO);
    }

    @Test
    public void testEquals_SameInstance() {
        assertThat(classUnderTest.equals(classUnderTest), is(true));
    }
    
    @Test
    public void testEquals_DifferentInstance() {
        assertThat(classUnderTest.equals(createOther()), is(true));
    }

    private Project createOther() {
        Project other = new Project();
        other.setKey(FOO);
        other.setName(FOO);
        Plans plans = new Plans();
        
        List<Plan> planList = new ArrayList<>();
        Plan plan = new Plan();
        planList.add(plan);
        plans.setPlan(planList);
        
        Result result = new Result();
        result.setNumber(1);
        plan.setResult(result);
        
        other.setPlans(plans);
        return other;
    }

    
    @Test
    public void testPlansAsCollection() {
        Collection<Plan> plans = classUnderTest.plansAsCollection();
        assertThat(plans.isEmpty(), is(true));
    }

    
    
}
