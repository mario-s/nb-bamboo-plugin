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
package org.netbeans.modules.bamboo.client.rest;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.netbeans.modules.bamboo.model.rcp.ProjectVo;
import org.netbeans.modules.bamboo.client.rest.AbstractVoUpdater.ProjectsUpdater;

/**
 *
 * @author Mario Schroeder
 */
public class ProjectsUpdaterTest {
    private static final String FOO = "foo";
    private static final String BAR = "bar";
    
    private ProjectsUpdater classUnderTest;
    
    private List<ProjectVo> source;
    
    private List<ProjectVo> target;
    
    @Before
    public void setUp() {
        classUnderTest = new ProjectsUpdater();
        source = new ArrayList<>();
        target = new ArrayList<>();
    }

    /**
     * Test of update method, of class ProjectsUpdater.
     */
    @Test
    public void testUpdate_SameContent_NoChange() {
        ProjectVo left = new ProjectVo(FOO);
        ProjectVo right = new ProjectVo(FOO);
        
        source.add(left);
        target.add(right);
        
        classUnderTest.update(source, target);

        assertThat(target.get(0), equalTo(right));
    }
    
    /**
     * Test of update method, of class ProjectsUpdater.
     */
    @Test
    public void testUpdate_DifferentContent_ExpectLeft() {
        ProjectVo left = new ProjectVo(FOO);
        ProjectVo right = new ProjectVo(BAR);
        
        source.add(left);
        target.add(right);
        
        classUnderTest.update(source, target);

        assertThat(target.get(0).getKey(), equalTo(FOO));
    }
    
     /**
     * Test of update method, of class ProjectsUpdater.
     */
    @Test
    public void testUpdate_DifferentContent_OnlyOne() {
        ProjectVo left = new ProjectVo(FOO);
        ProjectVo right = new ProjectVo(BAR);
        
        source.add(left);
        target.add(right);
        target.add(left);
        
        classUnderTest.update(source, target);

        assertThat(target.size(), is(1));
    }
    
     /**
     * Test of update method, of class ProjectsUpdater.
     */
    @Test
    public void testUpdate_AddContent_NewOne() {
        ProjectVo left = new ProjectVo(FOO);
        ProjectVo right = new ProjectVo(BAR);
        
        source.add(left);
        source.add(right);
        target.add(right);
        
        classUnderTest.update(source, target);

        assertThat(target.size(), is(2));
    }
    
}
