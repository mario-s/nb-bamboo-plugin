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
package org.netbeans.modules.bamboo.model.rcp;



import static org.hamcrest.CoreMatchers.is;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * @author schroeder
 */
public class DefaultInstanceValuesTest {
    
    private DefaultInstanceValues classUnderTest;
    
    @Before
    public void setUp() {
        classUnderTest = new DefaultInstanceValues();
        classUnderTest.setPassword(new char[]{'a'});
    }

    /**
     * Test of getName method, of class DefaultInstanceValues.
     */
    @Test
    public void testCopyConstructor_NotNull() {
        DefaultInstanceValues result = new DefaultInstanceValues(classUnderTest);
        assertThat(result.getPassword().length, is(1));
    }
    
    /**
     * Test of getName method, of class DefaultInstanceValues.
     */
    @Test
    public void testCopyConstructor_Null() {
        DefaultInstanceValues result = new DefaultInstanceValues(null);
        assertThat(result.getPassword().length, is(0));
    }

   
}
