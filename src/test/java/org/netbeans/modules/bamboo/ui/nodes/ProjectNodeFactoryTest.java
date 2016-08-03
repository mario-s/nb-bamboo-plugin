package org.netbeans.modules.bamboo.ui.nodes;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import static org.mockito.BDDMockito.given;

import org.mockito.Mock;

import org.mockito.runners.MockitoJUnitRunner;

import org.netbeans.modules.bamboo.glue.BuildProject;
import org.netbeans.modules.bamboo.glue.ProjectsProvideable;

import org.openide.nodes.Node;

import java.util.ArrayList;
import java.util.List;


/**
 * @author spindizzy
 */
@RunWith(MockitoJUnitRunner.class)
public class ProjectNodeFactoryTest {
    @Mock
    private ProjectsProvideable projectsProvideable;

    private ProjectNodeFactory classUnderTest;

    private BuildProject project;

    @Before
    public void setUp() {
        project = new BuildProject();

        List<BuildProject> projects = new ArrayList<>();
        projects.add(project);
        given(projectsProvideable.getProjects()).willReturn(projects);

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
        List<BuildProject> toPopulate = new ArrayList<>();
        classUnderTest.createKeys(toPopulate);
        assertFalse(toPopulate.isEmpty());
    }
}
