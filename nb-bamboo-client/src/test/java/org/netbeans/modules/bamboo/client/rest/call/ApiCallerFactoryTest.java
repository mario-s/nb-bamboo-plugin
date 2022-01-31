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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.netbeans.modules.bamboo.model.rcp.InstanceValues;

import static java.util.Collections.emptyMap;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;

import static org.mockito.BDDMockito.given;
import static org.netbeans.modules.bamboo.client.glue.RestResources.JSON;

/**
 *
 * @author Mario Schroeder
 */
@ExtendWith(MockitoExtension.class)
class ApiCallerFactoryTest {
    
    private static final String FOO = "foo";
    private static final Class BAR = FOO.getClass();
    @Mock
    private InstanceValues values;
    @InjectMocks
    private ApiCallerFactory classUnderTest;
    
    @BeforeEach
    void setup() {
        given(values.getToken()).willReturn(new char[] {'a'});
    }
    
    /**
     * Test of newCaller method, of class ApiCallerFactory.
     */
    @Test
    void testNewCaller_ClassAndString_ExpectNotNull() {
        assertNotNull(classUnderTest.newCaller(BAR, FOO));
    }
    
    /**
     * Test of newCaller method, of class ApiCallerFactory.
     */
    @Test
    void testNewCaller_WithParams_ExpectNotNull() {
        assertNotNull(classUnderTest.newCaller(BAR, FOO, emptyMap()));
    }
    
     /**
     * Test of newCaller method, of class ApiCallerFactory.
     */
    @Test
    void testNewCaller_JsonPath_ExpectNotNull() {
        assertNotNull(classUnderTest.newCaller(BAR, FOO + JSON));
    }


    /**
     * Test of newRepeatCaller method, of class ApiCallerFactory.
     */
    @Test
    void testNewRepeatCaller_ClassAndString_ExpectNotNull() {
        assertNotNull(classUnderTest.newRepeatCaller(BAR, FOO));
    }
    
    /**
     * Test of newRepeatCaller method, of class ApiCallerFactory.
     */
    @Test
    void testNewRepeatCaller_WithParams_ExpectNotNull() {
        assertNotNull(classUnderTest.newRepeatCaller(BAR, FOO, emptyMap()));
    }
}
