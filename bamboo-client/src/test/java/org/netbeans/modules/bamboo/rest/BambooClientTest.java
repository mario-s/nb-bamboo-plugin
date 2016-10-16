package org.netbeans.modules.bamboo.rest;

import java.util.ArrayList;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import static org.mockito.BDDMockito.given;

import org.mockito.Mock;

import org.mockito.runners.MockitoJUnitRunner;

import org.netbeans.modules.bamboo.model.InstanceValues;
import org.netbeans.modules.bamboo.model.VersionInfo;
import org.netbeans.modules.bamboo.model.rest.Info;
import org.netbeans.modules.bamboo.model.rest.Plan;
import org.netbeans.modules.bamboo.model.rest.Plans;
import org.netbeans.modules.bamboo.model.rest.PlansResponse;
import org.netbeans.modules.bamboo.model.rest.Result;
import org.netbeans.modules.bamboo.model.rest.Results;
import org.netbeans.modules.bamboo.model.rest.ResultsResponse;

import java.util.Collection;

import static java.util.Collections.singletonList;

import java.util.List;
import java.util.Map;

import static java.util.Optional.empty;
import static java.util.Optional.of;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;

import static org.hamcrest.CoreMatchers.equalTo;

import org.netbeans.modules.bamboo.model.ProjectVo;
import org.netbeans.modules.bamboo.model.rest.Project;
import org.netbeans.modules.bamboo.model.rest.Projects;
import org.netbeans.modules.bamboo.model.rest.ProjectsResponse;


import org.netbeans.modules.bamboo.model.PlanVo;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

/**
 * @author spindizzy
 */
@RunWith(MockitoJUnitRunner.class)
public class BambooClientTest {

    private static final String FOO = "foo";
    private static final String BAR = "bar";

    @Mock
    private InstanceValues instanceValues;
    @Mock
    private WebTarget webTarget;
    @Mock
    private Invocation.Builder invocationBuilder;
    @Mock
    private RepeatApiCaller<ProjectsResponse> projectsCaller;
    @Mock
    private RepeatApiCaller<PlansResponse> plansCaller;
    @Mock
    private RepeatApiCaller<ResultsResponse> resultsCaller;
    @Mock
    private ApiCaller<Info> infoCaller;
    @Mock
    private HttpUtility httpUtility;

    private DefaultBambooClient classUnderTest;

    @Before
    public void setUp() {
        given(instanceValues.getUrl()).willReturn("http://foo.bar");
        given(instanceValues.getUsername()).willReturn(FOO);
        given(instanceValues.getPassword()).willReturn(new char[]{'b', 'a', 'z'});
        given(webTarget.path(WebTargetFactory.REST_API)).willReturn(webTarget);
        given(webTarget.request()).willReturn(invocationBuilder);

        classUnderTest
                = new DefaultBambooClient(instanceValues, httpUtility) {
            @Override
            RepeatApiCaller<ProjectsResponse> createProjectCaller(InstanceValues values, Map<String, String> params) {
                return projectsCaller;
            }

            @Override
            RepeatApiCaller<PlansResponse> createPlansCaller(final InstanceValues values) {
                return plansCaller;
            }

            @Override
            RepeatApiCaller<ResultsResponse> createResultsCaller(final InstanceValues values, Map<String, String> params) {
                return resultsCaller;
            }

            @Override
            ApiCaller<Info> createInfoCaller(final InstanceValues values) {
                return infoCaller;
            }
        };
    }
    
    private void trainMocks() {
        Plan fooPlan = new Plan();
        fooPlan.setKey(FOO);
        Plan barPlan = new Plan();
        barPlan.setKey(BAR);
        
        Plans plans = new Plans();
        List<Plan> planList = new ArrayList<>(2);
        planList.add(fooPlan);
        planList.add(barPlan);
        plans.setPlan(planList);
        
        ProjectsResponse projectsResponse = new ProjectsResponse();
        Project project = new Project();
        project.setPlans(plans);
        Projects projects = new Projects();
        projects.setProject(singletonList(project));
        projectsResponse.setProjects(projects);
        
        PlansResponse plansResponse = new PlansResponse();
        
        plansResponse.setPlans(plans);

        ResultsResponse resultsResponse = new ResultsResponse();
        Results results = new Results();
        Result result = new Result();
        result.setPlan(fooPlan);
        results.setResult(singletonList(result));
        resultsResponse.setResults(results);
        
        given(projectsCaller.createTarget()).willReturn(of(webTarget));
        given(projectsCaller.get(webTarget)).willReturn(projectsResponse);
        given(projectsCaller.repeat(projectsResponse)).willReturn(empty());

        given(plansCaller.createTarget()).willReturn(of(webTarget));
        given(plansCaller.get(webTarget)).willReturn(plansResponse);
        given(plansCaller.repeat(plansResponse)).willReturn(of(plansResponse));

        given(resultsCaller.createTarget()).willReturn(of(webTarget));
        given(resultsCaller.get(webTarget)).willReturn(resultsResponse);
        given(resultsCaller.repeat(resultsResponse)).willReturn(of(resultsResponse));
    }

    /**
     * Test of getProjects method, of class DefaultBambooClient.
     */
    @Test
    public void testGetProjects_ExpectNotEmpty() {
        trainMocks();

        Collection<ProjectVo> buildProjects = classUnderTest.getProjects();
        assertThat(buildProjects.isEmpty(), is(false));
    }
    
     /**
     * Test of getProjects method, of class DefaultBambooClient.
     */
    @Test
    public void testGetProjects_ExpectNoParent() {
        trainMocks();

        Collection<ProjectVo> buildProjects = classUnderTest.getProjects();
        buildProjects.forEach(pr -> { assertThat(pr.getParent().isPresent(), is(false));});
        
    }
    
    
    /**
     * Test of getProjects method, of class DefaultBambooClient.
     */
    @Test
    public void testGetProjects_TwoPlans() {
        trainMocks();

        Collection<ProjectVo> buildProjects = classUnderTest.getProjects();
        Collection<PlanVo> plans = buildProjects.iterator().next().getChildren();
        assertThat(plans.size(), is(2));
    }
    
     /**
     * Test of getProjects method, of class DefaultBambooClient.
     */
    @Test
    public void testGetProjects_Equal() {
        trainMocks();

        Collection<ProjectVo> first = classUnderTest.getProjects();
        Collection<ProjectVo> second = classUnderTest.getProjects();
        
        assertThat(first, equalTo(second));
    }

    @Test
    public void testGetVersion() {
        Info info = new Info();
        info.setBuildDate("2014-12-02T07:43:02.000+01:00");

        given(infoCaller.createTarget()).willReturn(of(webTarget));
        given(infoCaller.get(webTarget)).willReturn(info);

        VersionInfo result = classUnderTest.getVersionInfo();
        assertThat(result.getBuildDate(), notNullValue());
    }
    
    @Test
    public void testUpdate() {
        trainMocks();
        List<ProjectVo> toBeUpdated = new ArrayList<>();
        classUnderTest.updateProjects(toBeUpdated);
        assertThat(toBeUpdated.isEmpty(), is(false));
    }
}
