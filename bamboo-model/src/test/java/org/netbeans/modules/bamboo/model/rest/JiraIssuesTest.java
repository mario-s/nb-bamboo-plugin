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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 *
 * @author Mario Schroeder
 */
class JiraIssuesTest {

    private JiraIssues classUnderTest;
    
    @BeforeEach
    void setUp() {
        classUnderTest = new JiraIssues();
    }


    /**
     * Test of asCollection method, of class JiraIssues.
     */
    @Test
    void asCollection_NoIssues_ShouldBeEmpty() {
        Collection<Issue> result = classUnderTest.asCollection();
        assertTrue(result.isEmpty());
    }
   
    
    /**
     * Test of asCollection method, of class JiraIssues.
     */
    @Test
    void asCollection_Issues_ShouldNotBeEmpty() {
        classUnderTest.setIssues(Arrays.asList(new Issue()));
        Collection<Issue> result = classUnderTest.asCollection();
        assertFalse(result.isEmpty());
    }
    
}
