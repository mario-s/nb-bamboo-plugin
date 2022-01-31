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
package org.netbeans.modules.bamboo.ui.nodes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.util.Collections.singletonList;

import org.netbeans.modules.bamboo.model.rcp.PlanVo;
import org.netbeans.modules.bamboo.model.rcp.ProjectVo;
import org.openide.nodes.Sheet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 *
 * @author Mario Schroeder
 */
class ProjectNodeTest {
    
    private ProjectNode classUnderTest;
    
    private ProjectVo project;
    
    @BeforeEach
    void setUp() {
        project = new ProjectVo("");
        project.setChildren(singletonList(new PlanVo("")));
        classUnderTest = new ProjectNode(project);
    }

    /**
     * Test of getIcon method, of class ProjectNode.
     */
    @Test
    void testGetIcon_ExpectNotNull() {
        assertNotNull(classUnderTest.getIcon(1));
    }
    
     /**
     * Test of getActions method, of class ProjectNode.
     */
    @Test
    void testCreateSheet_ExpectOneSheet() {
        Sheet result = classUnderTest.createSheet();
        assertEquals(1, result.toArray().length);
    }
}
