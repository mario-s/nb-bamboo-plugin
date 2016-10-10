package org.netbeans.modules.bamboo.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;

import org.junit.Before;
import org.junit.Test;
import org.netbeans.modules.bamboo.model.PlanVo;

import static org.junit.Assert.*;

import org.netbeans.modules.bamboo.model.ProjectVo;
import org.netbeans.modules.bamboo.model.rest.Plan;
import org.netbeans.modules.bamboo.model.rest.Plans;
import org.netbeans.modules.bamboo.model.rest.Project;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.isA;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.is;

/**
 *
 * @author spindizzy
 */
public class ProjectsFactoryTest {

    private static final String FOO = "foo";

    private ProjectsFactory classUnderTest;

    @Before
    public void setUp() {
        classUnderTest = new ProjectsFactory(FOO);
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
        classUnderTest.setProjects(singletonList(project));
        classUnderTest.setPlans(singletonList(plan));
        Collection<ProjectVo> result = classUnderTest.create();
        assertThat(result.isEmpty(), is(false));
    }

    /**
     * Test of create method, of class ProjectsFactory.
     */
    @Test
    public void testCreate_ProjectPlans_ExpectProjectIsParent() {
        Plan plan = new Plan();
        plan.setKey(FOO);
        List<Plan> planListForPlans = new ArrayList<>(1);
        planListForPlans.add(plan);
        Plans plans = new Plans();
        plans.setPlan(planListForPlans);
        Project project = new Project();
        project.setPlans(plans);
        List<Project> projects = new ArrayList<>(1);
        projects.add(project);
        classUnderTest.setProjects(projects);

        //create a different list to avoid concurrent modification exception
        List<Plan> planListForFactory = new ArrayList<>(1);
        planListForFactory.add(plan);
        classUnderTest.setPlans(planListForFactory);

        Collection<ProjectVo> result = classUnderTest.create();
        ProjectVo firstProjectVo = result.iterator().next();
        List<PlanVo> plansOfFirstProject = firstProjectVo.getPlans();
        PlanVo firstPlanVo = plansOfFirstProject.iterator().next();

        assertThat(firstPlanVo.getProject(), equalTo(firstProjectVo));
    }
    
    /**
     * Test of create method, of class ProjectsFactory.
     */
    @Test(expected = ConcurrentModificationException.class)
    public void testCreate_ProjectBasedOnSamePlans_ExpectException() {
        Plan plan = new Plan();
        plan.setKey(FOO);
        List<Plan> planList = new ArrayList<>(1);
        planList.add(plan);
        Plans plans = new Plans();
        plans.setPlan(planList);
        Project project = new Project();
        project.setPlans(plans);
        List<Project> projects = new ArrayList<>(1);
        projects.add(project);
        classUnderTest.setProjects(projects);
        classUnderTest.setPlans(planList);
        
        classUnderTest.create();
    }

}
