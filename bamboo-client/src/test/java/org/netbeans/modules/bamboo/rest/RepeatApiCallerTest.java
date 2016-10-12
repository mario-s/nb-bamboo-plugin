package org.netbeans.modules.bamboo.rest;

import static org.hamcrest.CoreMatchers.equalTo;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import static org.mockito.BDDMockito.given;

import static org.mockito.Matchers.anyString;

import org.mockito.Mock;

import static org.mockito.Mockito.verify;

import static org.mockito.internal.util.reflection.Whitebox.setInternalState;

import org.mockito.runners.MockitoJUnitRunner;

import org.netbeans.modules.bamboo.model.InstanceValues;
import org.netbeans.modules.bamboo.model.rest.PlansResponse;

import java.util.Optional;

import static java.util.Optional.empty;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import org.netbeans.modules.bamboo.model.rest.Plans;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Matchers.any;


/**
 * @author spindizzy
 */
@RunWith(MockitoJUnitRunner.class)
public class RepeatApiCallerTest {
    private static final String FOO = "foo";

    @Mock
    private InstanceValues values;
    @Mock
    private Client client;
    @Mock
    private WebTarget target;
    @Mock
    private Invocation.Builder builder;

    private RepeatApiCaller<PlansResponse> classUnderTest;

    @Before
    public void setUp() {
        classUnderTest = new RepeatApiCaller<>(values, PlansResponse.class, FOO);
        setInternalState(classUnderTest, "client", client);
        
        given(values.getPassword()).willReturn(FOO.toCharArray());
        given(values.getUrl()).willReturn(FOO);
        given(client.target(anyString())).willReturn(target);
        given(target.path(anyString())).willReturn(target);
        given(target.queryParam(anyString(), any())).willReturn(target);
    }

    /**
     * Test of createTarget method, of class ApiCaller.
     */
    @Test
    public void testCreateTarget() {
        Optional<WebTarget> expResult = empty();
        Optional<WebTarget> result = classUnderTest.createTarget();
        assertThat(result, equalTo(expResult));
    }

    /**
     * Test of doSecondCall method, of class ApiCaller.
     */
    @Test
    public void testDoSecondCall_SizeLessMax() {
        PlansResponse initial = new PlansResponse();
        Optional<PlansResponse> expResult = empty();
        Optional<PlansResponse> result = classUnderTest.doSecondCall(initial);
        assertThat(result, equalTo(expResult));
    }
    
    @Test
    public void testDoSecondCall_SizeGreaterMax() {
        
        PlansResponse initial = new PlansResponse();
        Plans plans = new Plans();
        plans.setSize(50);
        plans.setMaxResult(25);
        initial.setPlans(plans);
        
        given(target.request()).willReturn(builder);
        given(builder.get(PlansResponse.class)).willReturn(initial);
        
        Optional<PlansResponse> result = classUnderTest.doSecondCall(initial);
        assertThat(result.isPresent(), is(true));
    }

    /**
     * Test of request method, of class ApiCaller.
     */
    @Test
    public void testRequest() {
        given(target.request()).willReturn(builder);
        classUnderTest.request(target);
        verify(builder).get(PlansResponse.class);
    }
}
