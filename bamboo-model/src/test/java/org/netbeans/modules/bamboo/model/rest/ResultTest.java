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

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 *
 * @author Mario Schroeder
 */
public class ResultTest {
    
    private static final String FOO = "foo";
    
    private Result classUnderTest;
    
    @Before
    public void setUp() {
        classUnderTest = new Result();
        classUnderTest.setKey(FOO);
    }

    /**
     * Test of equals method, of class Plan.
     */
    @Test
    public void testEquals() {
        Result other = new Result();
        boolean result = classUnderTest.equals(other);
        assertThat(result, is(false));
    }

  
}
