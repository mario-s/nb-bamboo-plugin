package org.netbeans.modules.bamboo.rest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import static org.mockito.BDDMockito.given;

import org.mockito.Mock;

import org.mockito.runners.MockitoJUnitRunner;

import org.netbeans.modules.bamboo.glue.BuildProject;
import org.netbeans.modules.bamboo.glue.InstanceValues;
import org.netbeans.modules.bamboo.rest.BambooRestClient;
import org.netbeans.modules.bamboo.rest.model.Plan;
import org.netbeans.modules.bamboo.rest.model.Plans;
import org.netbeans.modules.bamboo.rest.model.PlansResponse;
import org.netbeans.modules.bamboo.rest.model.Results;
import org.netbeans.modules.bamboo.rest.model.ResultsResponse;

import java.util.Collection;
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
     * Test of getPProjects method, of class BambooRestClient.
     */
    @Test
    public void testGetProjects() {
        PlansResponse all = new PlansResponse();
        Plans plans = new Plans();
        plans.setPlan(singletonList(new Plan()));
        all.setPlans(plans);
        given(invocationBuilder.get(PlansResponse.class)).willReturn(all);
        given(webTarget.path(BambooRestClient.ALL_PLANS)).willReturn(webTarget);

        Collection<BuildProject> result = classUnderTest.getProjects(instanceValues);
        assertFalse(result.isEmpty());
    }
}
