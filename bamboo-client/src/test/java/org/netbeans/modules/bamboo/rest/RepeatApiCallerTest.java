package org.netbeans.modules.bamboo.rest;

import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import static org.mockito.BDDMockito.given;

import static org.mockito.Matchers.anyString;

import org.mockito.Mock;

import static org.mockito.internal.util.reflection.Whitebox.setInternalState;

import org.mockito.runners.MockitoJUnitRunner;

import org.netbeans.modules.bamboo.model.InstanceValues;
import org.netbeans.modules.bamboo.model.rest.PlansResponse;

import java.util.Optional;

import static java.util.Optional.empty;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import org.mockito.InOrder;
import org.netbeans.modules.bamboo.model.rest.Plans;

import static org.mockito.Mockito.verify;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;


/**
 * @author spindizzy
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

    private RepeatApiCaller<PlansResponse> classUnderTest;

    @Before
    public void setUp() {
        classUnderTest = new RepeatApiCaller<>(values, PlansResponse.class, FOO);
        setInternalState(classUnderTest, "webTargetFactory", webTargetFactory);
        
        given(values.getPassword()).willReturn(FOO.toCharArray());
        given(values.getUrl()).willReturn(FOO);
        given(webTargetFactory.newTarget(anyString(), any(Map.class))).willReturn(target);
        given(target.path(anyString())).willReturn(target);
        given(target.queryParam(anyString(), any())).willReturn(target);
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
        
        given(target.queryParam(RepeatApiCaller.MAX, SIZE)).willReturn(newTarget);
        given(target.request()).willReturn(builder);
        given(newTarget.request()).willReturn(builder);
        given(builder.get(PlansResponse.class)).willReturn(initial);
        
        Optional<PlansResponse> result = classUnderTest.repeat(initial);
        assertThat(result.isPresent(), is(true));
        
        InOrder order = inOrder(target, newTarget);
        order.verify(target).queryParam(RepeatApiCaller.MAX, SIZE);
        order.verify(newTarget).request();
    }

    /**
     * Test of get method, of class ApiCaller.
     */
    @Test
    public void testGetRequest() {
        given(target.request()).willReturn(builder);
        classUnderTest.get(target);
        verify(builder).get(PlansResponse.class);
    }
}
