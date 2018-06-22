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
package org.netbeans.modules.bamboo.model.convert;

import org.junit.Before;
import org.junit.Test;
import org.netbeans.modules.bamboo.model.rcp.PlanVo;
import org.netbeans.modules.bamboo.model.rest.Plan;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

/**
 *
 * @author Mario Schroeder
 */
public class PlanVoConverterTest {
    private static final String FOO = "foo";
    
    private PlanVoConverter classUnderTest;
    
    private Plan source;
    
    @Before
    public void setUp() {
        classUnderTest = new PlanVoConverter(FOO);
        source = new Plan();
        source.setKey(FOO);
        source.setName(FOO);
    }

    /**
     * Test of convert method, of class VoConverter.
     */
    @Test
    public void testConvert() {
        PlanVo result = classUnderTest.convert(source);
        assertThat(result.getKey(), equalTo(FOO));
    }

}
