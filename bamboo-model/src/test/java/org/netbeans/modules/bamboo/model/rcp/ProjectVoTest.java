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
package org.netbeans.modules.bamboo.model.rcp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;

/**
 *
 * @author Mario Schroeder
 */
public class ProjectVoTest {
    private static final String FOO = "foo";
    
    private ProjectVo classUnderTest;

    private PlanVo plan;
    
    @Before
    public void setUp() {
        plan = new PlanVo(FOO);
        
        classUnderTest = newInstance();
    }

    private ProjectVo newInstance() {
        ProjectVo instance = new ProjectVo(FOO);
        instance.setName(FOO);
        
        List<PlanVo> plans = new ArrayList<>();
        
        plans.add(plan);
        instance.setChildren(plans);
                
        return instance;
    }
    
    @Test
    public void testGetChildren_ExpectProjectToBeParent() {
        Collection<PlanVo> children = classUnderTest.getChildren();
        ProjectVo parent = children.iterator().next().getParent().get();
        assertThat(parent, is(classUnderTest));
    }

    /**
     * Test of equals method, of class ProjectVo.
     */
    @Test
    public void testEquals_True() {
        ProjectVo instance = newInstance();
        boolean result = classUnderTest.equals(instance);
        assertThat(result, is(true));
    }

     /**
     * Test of equals method, of class ProjectVo.
     */
    @Test
    public void testEqualsNewResult_True() {
        ProjectVo instance = newInstance();
        ResultVo resultVo = new ResultVo();
        resultVo.setNumber(1);
        instance.getChildren().iterator().next().setResult(resultVo);
        boolean result = classUnderTest.equals(instance);
        assertThat(result, is(true));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAddPlanToChildren_ExpectException() {
        PlanVo plan = new PlanVo("");
        classUnderTest.getChildren().add(plan);
    }
    
    @Test
    public void testIsChild_ExistingPlan_ExpectTrue() {
        boolean result = classUnderTest.isChild(plan);
        assertThat(result, is(true));
    }
    
    @Test
    public void testIsChild_EmptyPlan_ExpectFalse() {
        boolean result = classUnderTest.isChild(empty());
        assertThat(result, is(false));
    }
    
    @Test
    public void testIsChild_PresentPlan_ExpectTrue() {
        boolean result = classUnderTest.isChild(of(plan));
        assertThat(result, is(true));
    }
    
    @Test
    public void testIsAvailable_ParentNotPresent_ExpectFalse() {
        boolean result = classUnderTest.isAvailable();
        assertThat(result, is(false));
    }
   
}
