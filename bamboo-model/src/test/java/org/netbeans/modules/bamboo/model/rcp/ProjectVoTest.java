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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Mario Schroeder
 */
public class ProjectVoTest {
    private static final String FOO = "foo";
    
    private ProjectVo classUnderTest;

    private PlanVo plan;
    
    @BeforeEach
    void setUp() {
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
    void testGetChildren_ExpectProjectToBeParent() {
        Collection<PlanVo> children = classUnderTest.getChildren();
        ProjectVo parent = children.iterator().next().getParent().get();
        assertEquals(classUnderTest, parent);
    }

    /**
     * Test of equals method, of class ProjectVo.
     */
    @Test
    void testEquals_True() {
        ProjectVo instance = newInstance();
        assertTrue(classUnderTest.equals(instance));
    }

     /**
     * Test of equals method, of class ProjectVo.
     */
    @Test
    void testEqualsNewResult_True() {
        ProjectVo instance = newInstance();
        ResultVo resultVo = new ResultVo();
        resultVo.setNumber(1);
        instance.getChildren().iterator().next().setResult(resultVo);
        assertTrue(classUnderTest.equals(instance));
    }

    @Test
    void testAddPlanToChildren_ExpectException() {
        assertThrows(UnsupportedOperationException.class, () -> classUnderTest.getChildren().add(new PlanVo("")));
    }
    
    @Test
    void testIsChild_ExistingPlan_ExpectTrue() {
        assertTrue(classUnderTest.isChild(plan));
    }
    
    @Test
    void testIsChild_EmptyPlan_ExpectFalse() {
        assertFalse(classUnderTest.isChild(empty()));
    }
    
    @Test
    void testIsChild_PresentPlan_ExpectTrue() {
        assertTrue(classUnderTest.isChild(of(plan)));
    }
    
    @Test
    void testIsAvailable_ParentNotPresent_ExpectFalse() {
        assertFalse(classUnderTest.isAvailable());
    }
   
}
