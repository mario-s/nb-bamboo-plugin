package org.netbeans.modules.bamboo.ui.nodes;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import static org.mockito.BDDMockito.given;

import org.mockito.Mock;

import org.mockito.runners.MockitoJUnitRunner;


import org.openide.nodes.Node;

import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.CoreMatchers.equalTo;
import org.netbeans.modules.bamboo.glue.BambooInstance;
import org.netbeans.modules.bamboo.model.ProjectVo;

/**
 * @author spindizzy
 */
@RunWith(MockitoJUnitRunner.class)
public class ProjectNodeFactoryTest {

    @Mock
    private BambooInstance projectsProvideable;
    
    private ProjectNodeFactory classUnderTest;

    private List<ProjectVo> projects;

    private ProjectVo project;

    @Before
    public void setUp() {
        project = new ProjectVo("b");
        project.setName("b");

        ProjectVo other = new ProjectVo("a");
        other.setName("a");

        projects = new ArrayList<>();
        projects.add(project);
        projects.add(other);

        given(projectsProvideable.getProjects()).willReturn(projects);
        given(projectsProvideable.getLookup()).willReturn(project.getLookup());

        classUnderTest = new ProjectNodeFactory(projectsProvideable);
    }

    /**
     * Test of createNodeForKey method, of class ProjectNodeFactory.
     */
    @Test
    public void testCreateNodeForKey() {
        Node result = classUnderTest.createNodeForKey(project);
        assertNotNull(result);
    }

    /**
     * Test of createKeys method, of class ProjectNodeFactory.
     */
    @Test
    public void testCreateKeys() {
        List<ProjectVo> toPopulate = new ArrayList<>();
        classUnderTest.createKeys(toPopulate);
        assertThat(toPopulate.get(0).getName(), equalTo("a"));
    }
}
