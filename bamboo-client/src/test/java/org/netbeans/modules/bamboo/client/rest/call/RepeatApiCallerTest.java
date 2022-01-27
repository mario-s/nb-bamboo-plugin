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
import java.util.Map;

import org.mockito.Mock;

import org.netbeans.modules.bamboo.model.rcp.InstanceValues;
import org.netbeans.modules.bamboo.model.rest.PlansResponse;

import java.util.Optional;

import static java.util.Optional.empty;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.junit.jupiter.MockitoExtension;
import org.netbeans.modules.bamboo.model.rest.Plans;

import static org.mockito.Mockito.verify;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;

import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * @author Mario Schroeder
 */
@ExtendWith(MockitoExtension.class)
class RepeatApiCallerTest {
    private static final String FOO = "foo";
    private static final int SIZE = 50;
    private static final String HOST = "http://localhost";

    @Mock
    private InstanceValues values;
    @Mock
    private BasicAuthWebTargetFactory basicAuthWebTargetFactory;
    @Mock
    private WebTarget target;
    @Mock
    private Invocation.Builder builder;

    private ApiCallRepeater<PlansResponse> classUnderTest;

    @BeforeEach
    void setUp() {
        reset(builder);
        given(values.getToken()).willReturn(new char[] {'a'});
        
        classUnderTest = new ApiCallRepeater<>(new CallParameters(PlansResponse.class, values));
        
        ReflectionTestUtils.setField(classUnderTest, "basicAuthWebTargetFactory", basicAuthWebTargetFactory);
    }

    /**
     * Test of createTarget method, of class ApiCaller.
     */
    @Test
    void testCreateTarget() {
        Optional<WebTarget> result = classUnderTest.createTarget();
        assertFalse(result.isPresent());
    }

    /**
     * Test of repeat method, of class ApiCaller.
     */
    @Test
    void testDoSecondCall_SizeLessMax() {
        PlansResponse initial = new PlansResponse();
        Optional<PlansResponse> expResult = empty();
        Optional<PlansResponse> result = classUnderTest.repeat(initial);
        assertEquals(expResult, result);
    }
    
    @Test
    void testDoSecondCall_SizeGreaterMax_ExpectPresent() throws URISyntaxException {
        given(basicAuthWebTargetFactory.create(anyString(), any(Map.class))).willReturn(target);
        given(target.queryParam(anyString(), any())).willReturn(target);
        given(builder.accept(anyString())).willReturn(builder);
        
        WebTarget newTarget = mock(WebTarget.class);
        
        PlansResponse initial = new PlansResponse();
        Plans plans = new Plans();
        plans.setSize(SIZE);
        plans.setMaxResult(25);
        initial.setPlans(plans);
        
        given(newTarget.getUri()).willReturn(new URI(HOST));
        given(target.queryParam(ApiCallRepeater.MAX, SIZE)).willReturn(newTarget);
        given(newTarget.request()).willReturn(builder);
        given(builder.get(PlansResponse.class)).willReturn(initial);
        
        Optional<PlansResponse> result = classUnderTest.repeat(initial);
        assertTrue(result.isPresent());
        
        InOrder order = inOrder(target, newTarget);
        order.verify(target).queryParam(ApiCallRepeater.MAX, SIZE);
        order.verify(newTarget).request();
    }

    /**
     * Test of doGet method, of class ApiCaller.
     */
    @Test
    void doGet() throws URISyntaxException {
        given(target.getUri()).willReturn(new URI(HOST));
        given(target.request()).willReturn(builder);
        given(builder.accept(MediaType.APPLICATION_XML)).willReturn(builder);
        given(builder.get(eq(PlansResponse.class))).willReturn(new PlansResponse());
        
        classUnderTest.doGet(target);
        verify(builder).get(PlansResponse.class);
    }
}
