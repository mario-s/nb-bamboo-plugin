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

import java.awt.Image;

import static java.util.Collections.singletonList;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import org.netbeans.modules.bamboo.model.rcp.PlanVo;
import org.netbeans.modules.bamboo.model.rcp.ProjectVo;
import org.openide.nodes.Sheet;

/**
 *
 * @author Mario Schroeder
 */
public class ProjectNodeTest {
    
    private ProjectNode classUnderTest;
    
    private ProjectVo project;
    
    @Before
    public void setUp() {
        project = new ProjectVo("");
        project.setChildren(singletonList(new PlanVo("")));
        classUnderTest = new ProjectNode(project);
    }

    /**
     * Test of getIcon method, of class ProjectNode.
     */
    @Test
    public void testGetIcon_ExpectNotNull() {
        Image result = classUnderTest.getIcon(1);
        assertThat(result, notNullValue());

    }
    
     /**
     * Test of getActions method, of class ProjectNode.
     */
    @Test
    public void testCreateSheet_ExpectOneSheet() {
        Sheet result = classUnderTest.createSheet();
        assertThat(result.toArray().length, is(1));
    }
}
