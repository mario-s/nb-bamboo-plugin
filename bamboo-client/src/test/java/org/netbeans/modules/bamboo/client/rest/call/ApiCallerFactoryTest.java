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
package org.netbeans.modules.bamboo.client.rest.call;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.netbeans.modules.bamboo.model.rcp.InstanceValues;

import static java.util.Collections.emptyMap;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;
import static org.netbeans.modules.bamboo.client.glue.RestResources.JSON_PATH;

/**
 *
 * @author Mario Schroeder
 */
@RunWith(MockitoJUnitRunner.class)
public class ApiCallerFactoryTest {
    private static final String FOO = "foo";
    private static final Class BAR = FOO.getClass();
    @Mock
    private InstanceValues values;
    @InjectMocks
    private ApiCallerFactory classUnderTest;
    
    /**
     * Test of newCaller method, of class ApiCallerFactory.
     */
    @Test
    public void testNewCaller_ClassAndString_ExpectNotNull() {
        ApiCallable result = classUnderTest.newCaller(BAR, FOO);
        assertThat(result, notNullValue());
    }
    
    /**
     * Test of newCaller method, of class ApiCallerFactory.
     */
    @Test
    public void testNewCaller_WithParams_ExpectNotNull() {
        ApiCallable result = classUnderTest.newCaller(BAR, FOO, emptyMap());
        assertThat(result, notNullValue());
    }
    
     /**
     * Test of newCaller method, of class ApiCallerFactory.
     */
    @Test
    public void testNewCaller_JsonPath_ExpectNotNull() {
        ApiCallable result = classUnderTest.newCaller(BAR, FOO + JSON_PATH);
        assertThat(result, notNullValue());
    }


    /**
     * Test of newRepeatCaller method, of class ApiCallerFactory.
     */
    @Test
    public void testNewRepeatCaller_ClassAndString_ExpectNotNull() {
        ApiCallRepeatable result = classUnderTest.newRepeatCaller(BAR, FOO);
        assertThat(result, notNullValue());
    }
    
    /**
     * Test of newRepeatCaller method, of class ApiCallerFactory.
     */
    @Test
    public void testNewRepeatCaller_WithParams_ExpectNotNull() {
        ApiCallRepeatable result = classUnderTest.newRepeatCaller(BAR, FOO, emptyMap());
        assertThat(result, notNullValue());
    }
    
}
