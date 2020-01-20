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

import org.mockito.Mock;

import java.util.Collection;

import static java.util.Collections.singletonList;

import java.util.List;
import java.util.Map;

import static java.util.Optional.empty;
import static java.util.Optional.of;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

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

import static org.mockito.BDDMockito.given;
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

import org.springframework.test.util.ReflectionTestUtils;

import static java.lang.String.format;
import static java.util.Optional.empty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.netbeans.modules.bamboo.client.glue.RestResources.INFO;
import static org.netbeans.modules.bamboo.client.glue.RestResources.PLANS;
import static org.netbeans.modules.bamboo.client.glue.RestResources.PROJECTS;
import static org.netbeans.modules.bamboo.client.glue.RestResources.QUEUE;
import static org.netbeans.modules.bamboo.client.glue.RestResources.RESULTS;

/**
 * @author Mario Schroeder
 */
@ExtendWith(MockitoExtension.class)
class DefaultBambooClientTest {

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

    private Plan plan;

    private Plans plans;

    private Result result;

    private Info info;

    private ProjectsResponse projectsResponse;

    private PlansResponse plansResponse;

    private ResultsResponse resultsResponse;

    @BeforeEach
    void setUp() {
        //given(instanceValues.getUrl()).willReturn("http://foo.bar");

        classUnderTest = new DefaultBambooClient(instanceValues);

        ReflectionTestUtils.setField(classUnderTest, "apiCallerFactory", apiCallerFactory);

        plan = new Plan();
        plan.setKey(FOO);
        Plan barPlan = new Plan();
        barPlan.setKey(BAR);

        plans = new Plans();
        List<Plan> planList = new ArrayList<>(2);
        planList.add(plan);
        planList.add(barPlan);
        plans.setPlan(planList);

        projectsResponse = new ProjectsResponse();
        Project project = new Project();
        project.setPlans(plans);
        Projects projects = new Projects();
        projects.setProject(singletonList(project));
        projectsResponse.setProjects(projects);

        plansResponse = new PlansResponse();
        plansResponse.setPlans(plans);

        resultsResponse = new ResultsResponse();
        Results results = new Results();
        result = new Result();
        result.setPlan(plan);
        results.setResult(singletonList(result));
        resultsResponse.setResults(results);

        info = new Info();
        info.setBuildDate("2014-12-02T07:43:02.000+01:00");
    }

    private void trainMocks() {

        given(projectsCaller.createTarget()).willReturn(of(webTarget));
        given(projectsCaller.doGet(webTarget)).willReturn(of(projectsResponse));

        given(plansCaller.createTarget()).willReturn(of(webTarget));
        given(plansCaller.doGet(webTarget)).willReturn(of(plansResponse));
        given(plansCaller.repeat(plansResponse)).willReturn(of(plansResponse));

        given(resultsCaller.createTarget()).willReturn(of(webTarget));
        given(resultsCaller.doGet(webTarget)).willReturn(of(resultsResponse));
        given(resultsCaller.repeat(resultsResponse)).willReturn(of(resultsResponse));

        given(infoCaller.createTarget()).willReturn(of(webTarget));
        given(infoCaller.doGet(webTarget)).willReturn(of(info));

        given(resultCaller.createTarget()).willReturn(of(webTarget));
        given(resultCaller.doGet(webTarget)).willReturn(of(result));

        given(apiCallerFactory.newCaller(eq(Info.class), eq(INFO))).willReturn(
                infoCaller);

        given(apiCallerFactory.newCaller(eq(Object.class), eq(format(QUEUE, FOO)))).willReturn(
                postCaller);

        given(apiCallerFactory.newCaller(eq(Result.class), anyString(), any(Map.class))).willReturn(
                resultCaller);

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
    }

    private void trainForProjectResponse() {
        given(plansCaller.createTarget()).willReturn(of(webTarget));
        given(plansCaller.doGet(webTarget)).willReturn(of(plansResponse));
        given(plansCaller.repeat(plansResponse)).willReturn(of(plansResponse));
        given(projectsCaller.createTarget()).willReturn(of(webTarget));
        given(projectsCaller.doGet(webTarget)).willReturn(of(projectsResponse));
    }

    /**
     * Test of getProjects method, of class DefaultBambooClient.
     */
    @Test
    void testGetProjects_ExpectNotEmpty() {
        trainApiCallerFactory();
        trainForProjectResponse();

        Collection<ProjectVo> buildProjects = classUnderTest.getProjects();
        assertFalse(buildProjects.isEmpty());
    }

    /**
     * Test of getProjects method, of class DefaultBambooClient.
     */
    @Test
    void testGetProjects_ExpectNoParent() {
        trainApiCallerFactory();
        trainForProjectResponse();
        Collection<ProjectVo> buildProjects = classUnderTest.getProjects();
        buildProjects.forEach(pr -> {
            assertFalse(pr.getParent().isPresent());
        });

    }

    /**
     * Test of getProjects method, of class DefaultBambooClient.
     */
    @Test
    void testGetProjects_TwoPlans() {
        trainApiCallerFactory();
        trainForProjectResponse();

        Collection<ProjectVo> buildProjects = classUnderTest.getProjects();
        Collection<PlanVo> result = buildProjects.iterator().next().getChildren();
        assertEquals(2, result.size());
    }

    /**
     * Test of getProjects method, of class DefaultBambooClient.
     */
    @Test
    void testGetProjects_Equal() {
        trainApiCallerFactory();
        trainForProjectResponse();

        Collection<ProjectVo> first = classUnderTest.getProjects();
        Collection<ProjectVo> second = classUnderTest.getProjects();
        assertEquals(first, second);
    }

    @Test
    void testUpdate() {
        trainApiCallerFactory();
        trainForProjectResponse();

        List<ProjectVo> toBeUpdated = new ArrayList<>();
        classUnderTest.updateProjects(toBeUpdated);
        assertFalse(toBeUpdated.isEmpty());
    }

    @Test
    void testGetVersion() {
        given(apiCallerFactory.newCaller(eq(Info.class), eq(INFO))).willReturn(
                infoCaller);
        given(infoCaller.createTarget()).willReturn(of(webTarget));
        given(infoCaller.doGet(webTarget)).willReturn(of(info));

        assertNotNull(classUnderTest.getVersionInfo().getBuildDate());
    }

    @Test
    void testQueue_TargetPresent_Expect200() {
        given(apiCallerFactory.newCaller(eq(Object.class), anyString())).willReturn(postCaller);
        
        int code = 200;
        PlanVo planVo = new PlanVo(FOO);
        planVo.setParent(new ProjectVo(FOO));
        given(postCaller.createTarget()).willReturn(of(webTarget));
        given(postCaller.doPost(webTarget)).willReturn(Response.ok().build());

        Response response = classUnderTest.queue(planVo);
        assertEquals(code, response.getStatus());
        verify(postCaller).doPost(webTarget);
    }
    
    @Test
    void testQueue_TargetEmpty_ExpectNotFound() {
        given(apiCallerFactory.newCaller(eq(Object.class), anyString())).willReturn(postCaller);

        final int code = 404;
        PlanVo planVo = new PlanVo(FOO);
        planVo.setParent(new ProjectVo(FOO));
        given(postCaller.createTarget()).willReturn(empty());

        Response response = classUnderTest.queue(planVo);
        assertEquals(code, response.getStatus());
        verify(postCaller, never()).doPost(webTarget);
    }

    @Test
    void testAttach_ChangesNoResult_ShouldNotHaveChanges() {
        given(apiCallerFactory.newCaller(eq(Result.class), anyString(), any(Map.class))).willReturn(
                resultCaller);        
        ResultVo vo = new ResultVo();

        classUnderTest.attach(vo, ResultExpandParameter.Changes);
        assertFalse(vo.getChanges().isPresent());
    }

    @Test
    void testAttach_Changes_ShouldHaveChanges() {
        given(apiCallerFactory.newCaller(eq(Result.class), anyString(), any(Map.class))).willReturn(
                resultCaller);
        given(resultCaller.createTarget()).willReturn(of(webTarget));
        given(resultCaller.doGet(webTarget)).willReturn(of(result));
        
        Changes changes = new Changes();
        Change change = new Change();
        changes.setChanges(singletonList(change));
        result.setChanges(changes);

        ResultVo vo = new ResultVo();

        classUnderTest.attach(vo, ResultExpandParameter.Changes);
        assertFalse(vo.getChanges().get().isEmpty());
    }

    @Disabled
    @Test
    void testAttach_IssuesNoResult_ShouldNotHaveIssues() {
        ResultVo vo = new ResultVo();

        classUnderTest.attach(vo, ResultExpandParameter.Jira);
        assertTrue(vo.getIssues().get().isEmpty());
    }

    @Disabled
    @Test
    void testAttach_IssuesResult_ShouldHaveIssues() {
        Result result = new Result();
        JiraIssues issues = new JiraIssues();
        Issue issue = new Issue();
        issues.setIssues(singletonList(issue));
        result.setJiraIssues(issues);

        given(resultCaller.doGet(webTarget)).willReturn(of(result));
        ResultVo vo = new ResultVo();

        classUnderTest.attach(vo, ResultExpandParameter.Jira);
        assertFalse(vo.getIssues().get().isEmpty());
    }
}
