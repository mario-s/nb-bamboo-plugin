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

import static java.util.Collections.singletonList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 *
 * @author schroeder
 */
class ChangesTest {
    
    private Changes classUnderTest;
    
    @BeforeEach
    void setUp() {
        classUnderTest = new Changes();
    }

    /**
     * Test of asCollection method, of class Changes.
     */
    @Test
    void testAsCollection_NullChange_ExpectEmptyCollection() {
        Collection<Change> result = classUnderTest.asCollection();
        assertTrue(result.isEmpty());
    }

      /**
     * Test of asCollection method, of class Changes.
     */
    @Test
    void testAsCollection_Change_ExpectNonEmptyCollection() {
        classUnderTest.setChanges(singletonList(new Change()));
        Collection<Change> result = classUnderTest.asCollection();
        assertFalse(result.isEmpty());
    }
   
}
