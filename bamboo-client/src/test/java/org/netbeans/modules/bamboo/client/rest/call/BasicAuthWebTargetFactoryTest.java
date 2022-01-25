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

import static java.util.Collections.emptyMap;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.client.WebTarget;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

/**
 *
 * @author Mario Schroeder
 */
class BasicAuthWebTargetFactoryTest extends AbstractWebTargetFactoryTest{
    
    private BasicAuthWebTargetFactory classUnderTest;
    
    @BeforeEach
    void setUp() {
        given(values.getUrl()).willReturn(FOO);
        given(values.getUsername()).willReturn(FOO);
        given(values.getPassword()).willReturn(FOO.toCharArray());
        
        classUnderTest = new BasicAuthWebTargetFactory(values);
        ReflectionTestUtils.setField(classUnderTest, "client", client);
        
        trainTarget();
        given(target.queryParam(anyString(), anyString())).willReturn(target);
    }

    /**
     * Test of newTarget method, of class WebTargetFactory.
     */
    @Test
    void testNewTarget_NoParams_ExpectTarget() {
        verifyWebTarget(emptyMap());
    }
    
      /**
     * Test of newTarget method, of class WebTargetFactory.
     */
    @Test
    void testNewTarget_WithParams_ExpectTarget() {
        Map<String, String> params = new HashMap<>();
        params.put(FOO, FOO);
        verifyWebTarget(params);
    }

    void verifyWebTarget(Map<String, String> params) {
        WebTarget result = classUnderTest.create(FOO, params);
        assertNotNull(result);
    }
    
}
