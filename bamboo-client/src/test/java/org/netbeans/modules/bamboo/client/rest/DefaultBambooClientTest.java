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
package org.netbeans.modules.bamboo.client.rest;

import java.util.ArrayList;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import org.mockito.Mock;

import org.mockito.runners.MockitoJUnitRunner;


import java.util.Collection;

import static java.util.Collections.singletonList;

import java.util.List;
import java.util.Map;

import static java.util.Optional.empty;
import static java.util.Optional.of;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.netbeans.modules.bamboo.model.rcp.ProjectVo;
import org.netbeans.modules.bamboo.model.rest.Project;
import org.netbeans.modules.bamboo.model.rest.Projects;
import org.netbeans.modules.bamboo.model.rest.ProjectsResponse;
import org.netbeans.modules.bamboo.model.rcp.PlanVo;
import org.netbeans.modules.bamboo.model.rcp.ResultExpandParameter;
import org.netbeans.modules.bamboo.model.rcp.ResultVo;
import org.netbeans.modules.bamboo.model.rest.Change;
import org.netbeans.modules.bamboo.model.rest.Changes;
import org.netbeans.modules.bamboo.model.rest.Issue;
import org.netbeans.modules.bamboo.model.rest.JiraIssues;
import org.netbeans.modules.bamboo.model.rcp.InstanceValues;
import org.netbeans.modules.bamboo.model.rcp.VersionInfo;
import org.netbeans.modules.bamboo.model.rest.Info;
import org.netbeans.modules.bamboo.model.rest.Plan;
import org.netbeans.modules.bamboo.model.rest.Plans;
import org.netbeans.modules.bamboo.model.rest.PlansResponse;
import org.netbeans.modules.bamboo.model.rest.Result;
import org.netbeans.modules.bamboo.model.rest.Results;
import org.netbeans.modules.bamboo.model.rest.ResultsResponse;


import org.netbeans.modules.bamboo.client.rest.call.ApiCallRepeatable;
import org.netbeans.modules.bamboo.client.rest.call.ApiCallable;
import org.netbeans.modules.bamboo.client.rest.call.ApiCallerFactory;

import static org.hamcrest.CoreMatchers.equalTo;

import static org.mockito.BDDMockito.given;
import static org.mockito.internal.util.reflection.Whitebox.setInternalState;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Matchers.anyString;

import static java.lang.String.format;

import static org.netbeans.modules.bamboo.client.glue.RestResources.INFO;
import static org.netbeans.modules.bamboo.client.glue.RestResources.PLANS;
import static org.netbeans.modules.bamboo.client.glue.RestResources.PROJECTS;
import static org.netbeans.modules.bamboo.client.glue.RestResources.QUEUE;
import static org.netbeans.modules.bamboo.client.glue.RestResources.RESULTS;

/**
 * @author Mario Schroeder
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultBambooClientTest {

    private static final String FOO = "foo";

    private static final String BAR = "bar";

    @Mock
    private InstanceValues instanceValues;

    @Mock
    private WebTarget webTarget;

    @Mock
    private Invocation.Builder invocationBuilder;

    @Mock
    private ApiCallRepeatable<ProjectsResponse> projectsCaller;

    @Mock
    private ApiCallRepeatable<PlansResponse> plansCaller;

    @Mock
    private ApiCallRepeatable<ResultsResponse> resultsCaller;

    @Mock
    private ApiCallable<Result> resultCaller;

    @Mock
    private ApiCallable<Info> infoCaller;

    @Mock
    private ApiCallable postCaller;

    @Mock
    private ApiCallerFactory apiCallerFactory;
    
    private DefaultBambooClient classUnderTest;

    @Before
    public void setUp() {
        given(instanceValues.getUrl()).willReturn("http://foo.bar");
        given(instanceValues.getUsername()).willReturn(FOO);
        given(instanceValues.getPassword()).willReturn(new char[]{'b', 'a', 'z'});
        given(webTarget.path(anyString())).willReturn(webTarget);
        given(webTarget.request()).willReturn(invocationBuilder);

        classUnderTest
                = new DefaultBambooClient(instanceValues);

        setInternalState(classUnderTest, "apiCallerFactory", apiCallerFactory);

        trainMocks();
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

        Info info = new Info();
        info.setBuildDate("2014-12-02T07:43:02.000+01:00");

        given(projectsCaller.createTarget()).willReturn(of(webTarget));
        given(projectsCaller.doGet(webTarget)).willReturn(projectsResponse);
        given(projectsCaller.repeat(projectsResponse)).willReturn(empty());

        given(plansCaller.createTarget()).willReturn(of(webTarget));
        given(plansCaller.doGet(webTarget)).willReturn(plansResponse);
        given(plansCaller.repeat(plansResponse)).willReturn(of(plansResponse));

        given(resultsCaller.createTarget()).willReturn(of(webTarget));
        given(resultsCaller.doGet(webTarget)).willReturn(resultsResponse);
        given(resultsCaller.repeat(resultsResponse)).willReturn(of(resultsResponse));

        given(infoCaller.createTarget()).willReturn(of(webTarget));
        given(infoCaller.doGet(webTarget)).willReturn(info);

        given(resultCaller.createTarget()).willReturn(of(webTarget));
        given(resultCaller.doGet(webTarget)).willReturn(result);

        trainApiCallerFactory();
    }

    private void trainApiCallerFactory() {

        given(apiCallerFactory.newCaller(eq(ProjectsResponse.class), eq(PROJECTS), any(
                Map.class))).willReturn(
                        projectsCaller);

        given(apiCallerFactory.newRepeatCaller(eq(PlansResponse.class), eq(PLANS))).willReturn(
                plansCaller);

        given(apiCallerFactory.newRepeatCaller(eq(ResultsResponse.class), eq(RESULTS),
                any(Map.class))).willReturn(
                resultsCaller);

        given(apiCallerFactory.newCaller(eq(Info.class), eq(INFO))).willReturn(
                infoCaller);

        given(apiCallerFactory.newCaller(eq(Object.class), eq(format(QUEUE, FOO)))).willReturn(
                postCaller);

        given(apiCallerFactory.newCaller(eq(Result.class), anyString(), any(Map.class))).willReturn(
                resultCaller);
    }

    /**
     * Test of getProjects method, of class DefaultBambooClient.
     */
    @Test
    public void testGetProjects_ExpectNotEmpty() {
        Collection<ProjectVo> buildProjects = classUnderTest.getProjects();
        assertThat(buildProjects.isEmpty(), is(false));
    }

    /**
     * Test of getProjects method, of class DefaultBambooClient.
     */
    @Test
    public void testGetProjects_ExpectNoParent() {
        Collection<ProjectVo> buildProjects = classUnderTest.getProjects();
        buildProjects.forEach(pr -> {
            assertThat(pr.getParent().isPresent(), is(false));
        });

    }

    /**
     * Test of getProjects method, of class DefaultBambooClient.
     */
    @Test
    public void testGetProjects_TwoPlans() {
        Collection<ProjectVo> buildProjects = classUnderTest.getProjects();
        Collection<PlanVo> plans = buildProjects.iterator().next().getChildren();
        assertThat(plans.size(), is(2));
    }

    /**
     * Test of getProjects method, of class DefaultBambooClient.
     */
    @Test
    public void testGetProjects_Equal() {
        Collection<ProjectVo> first = classUnderTest.getProjects();
        Collection<ProjectVo> second = classUnderTest.getProjects();

        assertThat(first, equalTo(second));
    }

    @Test
    public void testGetVersion() {
        VersionInfo info = classUnderTest.getVersionInfo();
        assertThat(info.getBuildDate(), notNullValue());
    }

    @Test
    public void testUpdate() {
        List<ProjectVo> toBeUpdated = new ArrayList<>();
        classUnderTest.updateProjects(toBeUpdated);
        assertThat(toBeUpdated.isEmpty(), is(false));
    }

    @Test
    public void testQueue_TargetPresent_Expect200() {
        final int code = 200;
        PlanVo plan = new PlanVo(FOO);
        plan.setParent(new ProjectVo(FOO));
        given(postCaller.createTarget()).willReturn(of(webTarget));
        given(postCaller.doPost(webTarget)).willReturn(Response.ok().build());

        Response response = classUnderTest.queue(plan);
        assertThat(response.getStatus(), is(code));
        verify(postCaller).doPost(webTarget);
    }

    @Test
    public void testQueue_TargetEmpty_ExpectNotFound() {
        final int code = 404;
        PlanVo plan = new PlanVo(FOO);
        plan.setParent(new ProjectVo(FOO));
        given(postCaller.createTarget()).willReturn(empty());

        Response response = classUnderTest.queue(plan);
        assertThat(response.getStatus(), is(code));
        verify(postCaller, never()).doPost(webTarget);
    }

    @Test
    public void testAttach_ChangesNoResult_ShouldNotHaveChanges() {
        ResultVo vo = new ResultVo();

        classUnderTest.attach(vo, ResultExpandParameter.Changes);
        assertThat(vo.getChanges().get().isEmpty(), is(true));
    }

    @Test
    public void testAttach_Changes_ShouldHaveChanges() {
        Result result = new Result();
        Changes changes = new Changes();
        Change change = new Change();
        changes.setChanges(singletonList(change));
        result.setChanges(changes);

        given(resultCaller.doGet(webTarget)).willReturn(result);
        ResultVo vo = new ResultVo();

        classUnderTest.attach(vo, ResultExpandParameter.Changes);
        assertThat(vo.getChanges().get().isEmpty(), is(false));
    }

    @Test
    public void testAttach_IssuesNoResult_ShouldNotHaveIssues() {
        ResultVo vo = new ResultVo();

        classUnderTest.attach(vo, ResultExpandParameter.Jira);
        assertThat(vo.getIssues().get().isEmpty(), is(true));
    }

    @Test
    public void testAttach_IssuesResult_ShouldHaveIssues() {
        Result result = new Result();
        JiraIssues issues = new JiraIssues();
        Issue issue = new Issue();
        issues.setIssues(singletonList(issue));
        result.setJiraIssues(issues);

        given(resultCaller.doGet(webTarget)).willReturn(result);
        ResultVo vo = new ResultVo();

        classUnderTest.attach(vo, ResultExpandParameter.Jira);
        assertThat(vo.getIssues().get().isEmpty(), is(false));
    }
}
