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

import org.netbeans.modules.bamboo.glue.InstanceValues;
import org.netbeans.modules.bamboo.rest.model.PlansResponse;

import java.util.Optional;
import static java.util.Optional.empty;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;


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
        given(client.target(anyString())).willReturn(target);
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
    public void testDoSecondCall() {
        PlansResponse initial = new PlansResponse();
        Optional<PlansResponse> expResult = empty();
        Optional<PlansResponse> result = classUnderTest.doSecondCall(initial);
        assertThat(result, equalTo(expResult));
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
