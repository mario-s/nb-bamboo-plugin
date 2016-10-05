package org.netbeans.modules.bamboo.rest;

import java.util.Collection;
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.netbeans.modules.bamboo.model.ProjectVo;
import org.netbeans.modules.bamboo.model.rest.Plan;
import org.netbeans.modules.bamboo.model.rest.Project;

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
    public void testCreate_NoProjectNoPlans_Empty() {
        Collection<ProjectVo> result = classUnderTest.create();
        assertThat(result.isEmpty(), is(true));
    }

     /**
     * Test of create method, of class ProjectsFactory.
     */
    @Test
    public void testCreate_ProjectNoPlans_Empty() {
        Project project = new Project();
        classUnderTest.setProjects(singletonList(project));
        Collection<ProjectVo> result = classUnderTest.create();
        assertThat(result.isEmpty(), is(true));
    }
    
     /**
     * Test of create method, of class ProjectsFactory.
     */
    @Test
    public void testCreate_ProjectPlans_NotEmpty() {
        Plan plan = new Plan();
        Project project = new Project();
        classUnderTest.setProjects(singletonList(project));
        classUnderTest.setPlans(singletonList(plan));
        Collection<ProjectVo> result = classUnderTest.create();
        assertThat(result.isEmpty(), is(false));
    }

}
