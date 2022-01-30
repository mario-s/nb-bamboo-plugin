/*
 * Copyright 2022 NetBeans.
 *
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
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.netbeans.modules.bamboo.model.rcp.InstanceValues;
import org.springframework.test.util.ReflectionTestUtils;

/**
 *
 * @author Mario Schroeder
 */
@ExtendWith(MockitoExtension.class)
public class WebTargetFactoryTest {
    
    private static final String FOO = "foo";
    @Mock
    private Client client;
    @Mock
    private WebTarget target;
    @Mock
    private InstanceValues values;
    private WebTargetFactory classUnderTest;
    
    @BeforeEach
    public void setUp() {
        given(values.getToken()).willReturn(FOO.toCharArray());
        
        classUnderTest = new WebTargetFactory(values);
        ReflectionTestUtils.setField(classUnderTest, "client", client);
    }

    @Test
    @DisplayName("It should generate a WebTarget for empty parameter.") 
    void testCreate() {
        verifyWebTarget(emptyMap());
    }
    
    @Test
    @DisplayName("It should generate a WebTarget for not empty parameter.") 
    void testCreate_NoEmptyParams() {
        given(target.queryParam(anyString(), anyString())).willReturn(target);
        
        Map<String, String> params = new HashMap<>();
        params.put(FOO, FOO);
        verifyWebTarget(params);
    }
    
    void verifyWebTarget(final Map<String, String> parms) {
        given(values.getUrl()).willReturn(FOO);
        given(client.target(FOO)).willReturn(target);
        given(target.path(WebTargetFactory.REST_API)).willReturn(target);
        given(target.path(FOO)).willReturn(target);
        
        var res = classUnderTest.create(FOO, parms);
        assertNotNull(res);
    }
    
    @Test
    @DisplayName("It should return true when all needed params are valid.")
    void isValid() {
        given(values.getToken()).willReturn(FOO.toCharArray());
        given(values.getUrl()).willReturn(FOO);

        assertTrue(classUnderTest.isValid());
    }
}
