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
import org.netbeans.modules.bamboo.rest.model.Plan;
import org.netbeans.modules.bamboo.rest.model.Plans;
import org.netbeans.modules.bamboo.rest.model.PlansResponse;
import org.netbeans.modules.bamboo.rest.model.Result;
import org.netbeans.modules.bamboo.rest.model.Results;
import org.netbeans.modules.bamboo.rest.model.ResultsResponse;

import java.util.Collection;
import static java.util.Collections.singletonList;
import static java.util.Optional.of;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;


/**
 * @author spindizzy
 */
@RunWith(MockitoJUnitRunner.class)
public class BambooRestClientTest {
    private static final String FOO = "foo";

    @Mock
    private InstanceValues instanceValues;
    @Mock
    private WebTarget webTarget;
    @Mock
    private Invocation.Builder invocationBuilder;
    @Mock
    private RepeatApiCaller<PlansResponse> plansCaller;
    @Mock
    private RepeatApiCaller<ResultsResponse> resultsCaller;

    private BambooRestClient classUnderTest;

    @Before
    public void setUp() {
        given(instanceValues.getUrl()).willReturn("http://foo.bar");
        given(instanceValues.getUsername()).willReturn(FOO);
        given(instanceValues.getPassword()).willReturn(new char[] { 'b', 'a', 'z' });
        given(webTarget.path(BambooRestClient.REST_API)).willReturn(webTarget);
        given(webTarget.request()).willReturn(invocationBuilder);

        classUnderTest =
            new BambooRestClient() {
                @Override
                RepeatApiCaller<PlansResponse> createPlansCaller(final InstanceValues values) {
                    return plansCaller;
                }

                @Override
                RepeatApiCaller<ResultsResponse> createResultsCaller(final InstanceValues values) {
                    return resultsCaller;
                }
            };
    }

    /**
     * Test of getPProjects method, of class BambooRestClient.
     */
    @Test
    public void testGetProjects() {
        PlansResponse plansResponse = new PlansResponse();
        Plans plans = new Plans();
        Plan plan = new Plan();
        plan.setKey(FOO);
        plans.setPlan(singletonList(plan));
        plansResponse.setPlans(plans);

        ResultsResponse resultsResponse = new ResultsResponse();
        Results results = new Results();
        Result result = new Result();
        result.setPlan(plan);
        results.setResult(singletonList(result));
        resultsResponse.setResults(results);

        given(plansCaller.createTarget()).willReturn(of(webTarget));
        given(plansCaller.request(webTarget)).willReturn(plansResponse);
        given(plansCaller.doSecondCall(plansResponse)).willReturn(of(plansResponse));

        given(resultsCaller.createTarget()).willReturn(of(webTarget));
        given(resultsCaller.request(webTarget)).willReturn(resultsResponse);
        given(resultsCaller.doSecondCall(resultsResponse)).willReturn(of(resultsResponse));

        Collection<BuildProject> projects = classUnderTest.getProjects(instanceValues);
        assertFalse(projects.isEmpty());
    }
}
