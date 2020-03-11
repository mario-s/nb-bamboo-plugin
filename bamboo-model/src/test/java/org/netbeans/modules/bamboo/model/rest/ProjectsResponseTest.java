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
package org.netbeans.modules.bamboo.model.rest;

import java.util.Collection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 *
 * @author Mario Schroeder
 */
public class ProjectsResponseTest {
    
    private ProjectsResponse classUnderTest;
    
    @BeforeEach
    public void setUp() {
        classUnderTest = new ProjectsResponse();
    }

    /**
     * Test of asCollection method, of class ResultsResponse.
     */
    @Test
    void testAsCollection_NoResults_ExpectEmptyCollection() {
        Collection<Project> result = classUnderTest.asCollection();
        assertTrue(result.isEmpty());
    }

    /**
     * Test of asCollection method, of class ResultsResponse.
     */
    @Test
    void testAsCollection_Results_ExpectNoEmptyCollection() {
        Projects projects = new Projects();
        projects.setProject(singletonList(new Project()));
        classUnderTest.setProjects(projects);
        Collection<Project> result = classUnderTest.asCollection();
        assertFalse(result.isEmpty());
    }
}
