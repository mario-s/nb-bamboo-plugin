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

import java.net.URI;
import java.net.URISyntaxException;

import static java.util.Collections.singletonMap;

import java.util.Map;
import java.util.Optional;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.netbeans.modules.bamboo.model.rcp.InstanceValues;
import org.netbeans.modules.bamboo.model.rest.Info;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import static org.mockito.BDDMockito.given;

/**
 *
 * @author Mario Schroeder
 */
@ExtendWith(MockitoExtension.class)
class ApiCallerTest {

    private static final String FOO = "foo";

    private static final Map<String, String> FOO_MAP = singletonMap(FOO, FOO);

    @Mock
    private InstanceValues values;
    @Mock
    private BasicAuthWebTargetFactory basicAuthWebTargetFactory;
    @Mock
    private AuthHeaderWebTargetFactory authHeaderWebTargetFactory;
    @Mock
    private WebTarget target;
    @Mock
    private Invocation.Builder builder;
    @Mock
    private Response response;

    private ApiCaller<Info> classUnderTest;

    @BeforeEach
    void setUp() {
        given(values.getToken()).willReturn(new char[] {'a'});
        
        var callParameters = new CallParameters(Info.class, values);
        callParameters.setParameters(FOO_MAP);
        classUnderTest = new ApiCaller<>(callParameters);

        ReflectionTestUtils.setField(classUnderTest, "basicAuthWebTargetFactory", basicAuthWebTargetFactory);
        ReflectionTestUtils.setField(classUnderTest, "authHeaderWebTargetFactory", authHeaderWebTargetFactory);
    }

    /**
     * Test of createTarget method, of class ApiCaller.
     */
    @Test
    @DisplayName("It should not create a web target if necessary values are missing.")
    void testCreateTarget_EmptyValues_ExpectNotPresent() {
        assertFalse(classUnderTest.createTarget().isPresent());
    }

    /**
     * Test of createTarget method, of class ApiCaller.
     */
    @Test
    @DisplayName("It should create a web target with basic authentication and additional parameters.")
    void testCreateTarget_Values_ExpectPresent() {
        given(basicAuthWebTargetFactory.isValid()).willReturn(true);
        given(basicAuthWebTargetFactory.create(anyString(), any(Map.class))).willReturn(target);

        assertTrue(classUnderTest.createTarget().isPresent());
    }
    
    /**
     * Test of createTarget method, of class ApiCaller.
     */
    @Test
    @DisplayName("It should create a web target with token authentication and additional parameters.")
    void testCreateTokenTarget_Values_ExpectPresent() {
        given(values.isUseToken()).willReturn(true);
        given(authHeaderWebTargetFactory.isValid()).willReturn(true);
        given(authHeaderWebTargetFactory.create(anyString(), any(Map.class))).willReturn(target);

        assertTrue(classUnderTest.createTarget().isPresent());
    }

    /**
     * Test of doGet method, of class ApiCaller.
     */
    @Test
    @DisplayName("It should allow to perform a GET request.")
    void testGetRequest_ExpectPresent() throws URISyntaxException {
        given(target.getUri()).willReturn(new URI("http://localhost"));
        given(target.request()).willReturn(builder);
        given(builder.accept(anyString())).willReturn(builder);
        given(builder.get(Info.class)).willReturn(new Info());
        
        Optional<Info> result = classUnderTest.doGet(target);
        assertTrue(result.isPresent());
    }
    
    /**
     * Test of doGet method, of class ApiCaller.
     */
    @Test
    @DisplayName("It should allow to perform a GET request and return empty when it fails.")
    void testGetRequest_ExpectEmpty() throws URISyntaxException {
        given(target.getUri()).willReturn(new URI("http://localhost"));
        given(target.request()).willThrow(new WebApplicationException());
        
        Optional<Info> result = classUnderTest.doGet(target);
        assertTrue(result.isEmpty());
    }

    /**
     * Test of doPost method, of class ApiCaller.
     */
    @Test
    @DisplayName("It should allow to perform a POST request.")
    void testPostRequest_ExpectZero() {
        given(target.request()).willReturn(builder);
        given(builder.post(any(Entity.class))).willReturn(response);

        Response result = classUnderTest.doPost(target);
        assertEquals(0, result.getStatus());
    }

}
