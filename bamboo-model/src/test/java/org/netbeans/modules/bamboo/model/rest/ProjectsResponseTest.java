package org.netbeans.modules.bamboo.model.rest;

import java.util.Collection;
import org.junit.Before;
import org.junit.Test;

import static java.util.Collections.singletonList;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;

/**
 *
 * @author spindizzy
 */
public class ProjectsResponseTest {
    
    private ProjectsResponse classUnderTest;
    
    @Before
    public void setUp() {
        classUnderTest = new ProjectsResponse();
    }

    /**
     * Test of asCollection method, of class ResultsResponse.
     */
    @Test
    public void testAsCollection_NoResults_ExpectEmptyCollection() {
        Collection<Project> result = classUnderTest.asCollection();
        assertThat(result.isEmpty(), is(true));
    }

        /**
     * Test of asCollection method, of class ResultsResponse.
     */
    @Test
    public void testAsCollection_Results_ExpectNoEmptyCollection() {
        Projects projects = new Projects();
        projects.setProject(singletonList(new Project()));
        classUnderTest.setProjects(projects);
        Collection<Project> result = classUnderTest.asCollection();
        assertThat(result.isEmpty(), is(false));
    }
}
