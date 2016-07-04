package org.netbeans.modules.bamboo.rest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import static org.mockito.BDDMockito.given;

import org.mockito.Mock;

import org.mockito.runners.MockitoJUnitRunner;

import org.netbeans.modules.bamboo.glue.InstanceValues;

import static java.util.Collections.singletonList;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;


/**
 * @author spindizzy
 */
@RunWith(MockitoJUnitRunner.class)
public class BambooRestClientTest {
    @Mock
    private InstanceValues instanceValues;
    @Mock
    private WebTarget webTarget;
    @Mock
    private Invocation.Builder invocationBuilder;

    private BambooRestClient classUnderTest;

    @Before
    public void setUp() {
        given(instanceValues.getUrl()).willReturn("http://foo.bar");
        given(instanceValues.getUsername()).willReturn("foo");
        given(instanceValues.getPassword()).willReturn(new char[] { 'b', 'a', 'z' });
        given(webTarget.path(BambooRestClient.REST_API)).willReturn(webTarget);
        given(webTarget.queryParam(BambooRestClient.AUTH_TYPE, BambooRestClient.BASIC)).willReturn(
            webTarget);
        given(webTarget.queryParam(BambooRestClient.USER, "foo")).willReturn(webTarget);
        given(webTarget.queryParam(BambooRestClient.PASS, "baz")).willReturn(webTarget);
        given(webTarget.request()).willReturn(invocationBuilder);

        classUnderTest =
            new BambooRestClient() {
                @Override
                WebTarget newTarget(final String url) {
                    return webTarget;
                }
            };
    }

    /**
     * Test of getPlan method, of class BambooRestClient.
     */
    @Test
    public void testGetPlans() {
        AllPlansResponse all = new AllPlansResponse();
        Plans plans = new Plans();
        plans.setPlan(singletonList(new Plan()));
        all.setPlans(plans);
        given(invocationBuilder.get(AllPlansResponse.class)).willReturn(all);
        given(webTarget.path(BambooRestClient.ALL_PLANS)).willReturn(webTarget);

        AllPlansResponse result = classUnderTest.getAllPlans(instanceValues);
        assertFalse(result.getPlans().getPlan().isEmpty());
    }

    /**
     * Test of getResultsResponse method, of class BambooRestClient.
     */
    @Test
    public void testGetResultsResponse() {
        AllResultsResponse expectedResult = new AllResultsResponse();
        expectedResult.setResults(new Results());
        given(invocationBuilder.get(AllResultsResponse.class)).willReturn(expectedResult);
        given(webTarget.path(BambooRestClient.RESULT)).willReturn(webTarget);

        AllResultsResponse result = classUnderTest.getResultsResponse(instanceValues);
        assertNotNull(result.getResults());
    }
}
