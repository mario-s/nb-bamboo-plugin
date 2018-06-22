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

import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;


import org.mockito.Mock;

import static org.mockito.internal.util.reflection.Whitebox.setInternalState;

import org.mockito.runners.MockitoJUnitRunner;

import org.netbeans.modules.bamboo.model.rcp.InstanceValues;
import org.netbeans.modules.bamboo.model.rest.PlansResponse;

import java.util.Optional;

import static java.util.Optional.empty;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import org.mockito.InOrder;
import org.netbeans.modules.bamboo.model.rest.Plans;

import static org.mockito.Mockito.verify;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;


/**
 * @author Mario Schroeder
 */
@RunWith(MockitoJUnitRunner.class)
public class RepeatApiCallerTest {
    private static final String FOO = "foo";
    private static final int SIZE = 50;

    @Mock
    private InstanceValues values;
    @Mock
    private WebTargetFactory webTargetFactory;
    @Mock
    private WebTarget target;
    @Mock
    private Invocation.Builder builder;

    private ApiCallRepeater<PlansResponse> classUnderTest;

    @Before
    public void setUp() {
        classUnderTest = new ApiCallRepeater<>(new CallParameters(PlansResponse.class, values));
        setInternalState(classUnderTest, "webTargetFactory", webTargetFactory);
        
        given(values.getPassword()).willReturn(FOO.toCharArray());
        given(values.getUrl()).willReturn(FOO);
        given(webTargetFactory.newTarget(anyString(), any(Map.class))).willReturn(target);
        given(target.path(anyString())).willReturn(target);
        given(target.queryParam(anyString(), any())).willReturn(target);
        
        given(target.request()).willReturn(builder);
        given(builder.accept(anyString())).willReturn(builder);
    }

    /**
     * Test of createTarget method, of class ApiCaller.
     */
    @Test
    public void testCreateTarget() {
        Optional<WebTarget> result = classUnderTest.createTarget();
        assertThat(result.isPresent(), is(false));
    }

    /**
     * Test of repeat method, of class ApiCaller.
     */
    @Test
    public void testDoSecondCall_SizeLessMax() {
        PlansResponse initial = new PlansResponse();
        Optional<PlansResponse> expResult = empty();
        Optional<PlansResponse> result = classUnderTest.repeat(initial);
        assertThat(result, equalTo(expResult));
    }
    
    @Test
    public void testDoSecondCall_SizeGreaterMax_ExpectPresent() {
        WebTarget newTarget = mock(WebTarget.class);
        
        PlansResponse initial = new PlansResponse();
        Plans plans = new Plans();
        plans.setSize(SIZE);
        plans.setMaxResult(25);
        initial.setPlans(plans);
        
        given(target.queryParam(ApiCallRepeater.MAX, SIZE)).willReturn(newTarget);
        given(newTarget.request()).willReturn(builder);
        given(builder.get(PlansResponse.class)).willReturn(initial);
        
        Optional<PlansResponse> result = classUnderTest.repeat(initial);
        assertThat(result.isPresent(), is(true));
        
        InOrder order = inOrder(target, newTarget);
        order.verify(target).queryParam(ApiCallRepeater.MAX, SIZE);
        order.verify(newTarget).request();
    }

    /**
     * Test of doGet method, of class ApiCaller.
     */
    @Test
    public void testGetRequest() {
        given(target.request()).willReturn(builder);
        classUnderTest.doGet(target);
        verify(builder).get(PlansResponse.class);
    }
}
