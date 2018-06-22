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
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.List;

import static java.util.Collections.singletonList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.netbeans.modules.bamboo.model.rcp.InstanceValues;

import static org.junit.Assert.*;

import org.netbeans.modules.bamboo.model.rcp.ProjectVo;
import org.netbeans.modules.bamboo.model.rest.Plan;
import org.netbeans.modules.bamboo.model.rest.Plans;
import org.netbeans.modules.bamboo.model.rest.Project;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;

/**
 *
 * @author Mario Schroeder
 */
@RunWith(MockitoJUnitRunner.class)
public class ProjectsFactoryTest {

    private static final String FOO = "foo";
    
    @Mock
    private InstanceValues values;

    private ProjectsFactory classUnderTest;

    @Before
    public void setUp() {
        given(values.getUrl()).willReturn(FOO);
        classUnderTest = new ProjectsFactory(values);
    }

    /**
     * Test of create method, of class ProjectsFactory.
     */
    @Test
    public void testCreate_NoProjectNoPlans_ExpectEmpty() {
        Collection<ProjectVo> result = classUnderTest.create();
        assertThat(result.isEmpty(), is(true));
    }

    /**
     * Test of create method, of class ProjectsFactory.
     */
    @Test
    public void testCreate_ProjectNoPlans_ExpectEmpty() {
        Project project = new Project();
        classUnderTest.setProjects(singletonList(project));
        Collection<ProjectVo> result = classUnderTest.create();
        assertThat(result.isEmpty(), is(true));
    }

    /**
     * Test of create method, of class ProjectsFactory.
     */
    @Test
    public void testCreate_ProjectPlans_ExpectNotEmpty() {
        Plan plan = new Plan();
        Project project = new Project();
        project.setKey(FOO);
        classUnderTest.setProjects(singletonList(project));
        classUnderTest.setPlans(singletonList(plan));
        Collection<ProjectVo> result = classUnderTest.create();
        assertThat(result.isEmpty(), is(false));
    }
    
    /**
     * Test of create method, of class ProjectsFactory.
     */
    @Test(expected = ConcurrentModificationException.class)
    public void testCreate_ProjectBasedOnSamePlans_ExpectException() {
        Plan plan = new Plan();
        plan.setKey(FOO);
        plan.setName(FOO);
        List<Plan> planList = new ArrayList<>(1);
        planList.add(plan);
        Plans plans = new Plans();
        plans.setPlan(planList);
        Project project = new Project();
        project.setKey(FOO);
        project.setPlans(plans);
        List<Project> projects = new ArrayList<>(1);
        projects.add(project);
        classUnderTest.setProjects(projects);
        classUnderTest.setPlans(planList);
        
        classUnderTest.create();
    }

}
