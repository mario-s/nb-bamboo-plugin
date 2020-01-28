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

import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.netbeans.modules.bamboo.model.rcp.InstanceValues;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;

import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 *
 * @author Mario Schroeder
 */
@ExtendWith(MockitoExtension.class)
class WebTargetFactoryTest {
    private static final String FOO = "foo";
    @Mock
    private Client client;
    @Mock
    private InstanceValues values;
    @Mock
    private WebTarget target;
    @InjectMocks
    private WebTargetFactory classUnderTest;
    
    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(classUnderTest, "client", client);
        
        given(values.getUrl()).willReturn(FOO);
        given(values.getUsername()).willReturn(FOO);
        given(values.getPassword()).willReturn(new char[]{'a'});
        
        given(client.target(FOO)).willReturn(target);
        given(target.path(WebTargetFactory.REST_API)).willReturn(target);
        given(target.path(FOO)).willReturn(target);
        given(target.queryParam(anyString(), anyString())).willReturn(target);
    }

    /**
     * Test of newTarget method, of class WebTargetFactory.
     */
    @Test
    void testNewTarget_NoParams_ExpectTarget() {
        WebTarget result = classUnderTest.create(FOO, null);
        assertNotNull(result);
    }
    
      /**
     * Test of newTarget method, of class WebTargetFactory.
     */
    @Test
    void testNewTarget_WithParams_ExpectTarget() {
        Map<String, String> params = new HashMap<>();
        params.put(FOO, FOO);
        WebTarget result = classUnderTest.create(FOO, params);
        assertNotNull(result);
    }
    
}
