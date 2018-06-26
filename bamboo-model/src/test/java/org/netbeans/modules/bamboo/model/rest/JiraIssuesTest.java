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

import java.util.Arrays;
import java.util.Collection;
import static org.hamcrest.CoreMatchers.is;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Mario Schroeder
 */
public class JiraIssuesTest {

    private JiraIssues classUnderTest;
    
    @Before
    public void setUp() {
        classUnderTest = new JiraIssues();
    }


    /**
     * Test of asCollection method, of class JiraIssues.
     */
    @Test
    public void asCollection_NoIssues_ShouldBeEmpty() {
        Collection<Issue> result = classUnderTest.asCollection();
        assertThat(result.isEmpty(), is(true));
    }
    
    
    /**
     * Test of asCollection method, of class JiraIssues.
     */
    @Test
    public void asCollection_Issues_ShouldNotBeEmpty() {
        classUnderTest.setIssues(Arrays.asList(new Issue()));
        Collection<Issue> result = classUnderTest.asCollection();
        assertThat(result.isEmpty(), is(false));
    }
    
}
