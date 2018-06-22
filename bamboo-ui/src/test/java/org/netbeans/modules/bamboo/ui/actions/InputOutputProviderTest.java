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
package org.netbeans.modules.bamboo.ui.actions;

import static org.hamcrest.CoreMatchers.notNullValue;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.netbeans.api.io.OutputWriter;

/**
 *
 * @author Mario Schroeder
 */
public class InputOutputProviderTest {
    
    private InputOutputProvider classUnderTest;
    
    @Before
    public void setUp() {
        classUnderTest = new InputOutputProvider();
    }

    /**
     * Test of getOut method, of class InputOutputProvider.
     */
    @Test
    public void testGetOut() {
        OutputWriter result = classUnderTest.getOut("");
        assertThat(result, notNullValue());
    }
    
}
