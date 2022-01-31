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

import org.netbeans.modules.bamboo.client.glue.HttpUtility;
import org.netbeans.modules.bamboo.client.rest.AbstractVoUpdater.ProjectsUpdater;
import org.netbeans.modules.bamboo.client.rest.call.ApiCallRepeatable;
import org.netbeans.modules.bamboo.client.rest.call.ApiCallable;
import org.netbeans.modules.bamboo.client.rest.call.ApiCallerFactory;
import org.netbeans.modules.bamboo.model.convert.*;
import org.netbeans.modules.bamboo.model.rcp.*;
import org.netbeans.modules.bamboo.model.rest.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.*;
import java.util.function.Function;

import static java.lang.String.format;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonMap;
import static java.util.Objects.requireNonNull;
import static java.util.Optional.empty;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.netbeans.modules.bamboo.client.glue.ExpandParameter.*;
import static org.netbeans.modules.bamboo.client.glue.RestResources.*;

/**
 * This is the client, which actually interacts with a Bamboo server.
 *
 * @author Mario Schroeder
 */
class DefaultBambooClient extends AbstractBambooClient {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultBambooClient.class);

    private static final String RESULTS_FOR_CALL = "got results for call: {}";

    private Function<VoConverter, CollectionVoConverter> conv = CollectionVoConverter::new;

    private final ApiCallerFactory apiCallerFactory;

    DefaultBambooClient(InstanceValues values) {
        this(values, new HttpUtility());
    }

    DefaultBambooClient(InstanceValues values, HttpUtility utility) {
        super(values, utility);
        apiCallerFactory = new ApiCallerFactory(values);
    }

    private Collection<Plan> doPlansCall() {
        LOG.info("requesting plans...");
        Set<Plan> results = new HashSet<>();
        Map<String, String> params = singletonMap(EXPAND, PLAN_DETAILS);
        ApiCallRepeatable caller = apiCallerFactory.newRepeatCaller(PlansResponse.class, PLANS, params);
        doRepeatableCall(caller, results);
        return results;
    }

    private Collection<Result> doResultsCall() {
        LOG.info("requesting results...");
        Set<Result> results = new HashSet<>();
        Map<String, String> params = singletonMap(EXPAND, RESULT_COMMENTS);
        ApiCallRepeatable caller = apiCallerFactory.newRepeatCaller(ResultsResponse.class, RESULTS, params);
        doRepeatableCall(caller, results);
        return results;
    }

    private void doRepeatableCall(ApiCallRepeatable<? extends Responseable> apiCaller,
            Set<? extends ServiceInfoProvideable> results) {
        final Optional<WebTarget> opt = apiCaller.createTarget();
        opt.ifPresent(target -> {
            apiCaller.doGet(target).ifPresent(initialResponse -> {
                LOG.trace(RESULTS_FOR_CALL, initialResponse);
                results.addAll(initialResponse.asCollection());

                apiCaller.repeat(initialResponse).ifPresent(response -> {
                    results.addAll(response.asCollection());
                });
            });
        });
    }

    private void doSimpleCall(ApiCallable<? extends AbstractResponse> apiCaller, Set results) {

        apiCaller.createTarget().ifPresent(target -> {
            apiCaller.doGet(target).ifPresent(initialResponse -> {
                LOG.debug("got results for call: {}", initialResponse);
                results.addAll(initialResponse.asCollection());
            });
        });
    }

    @Override
    void attach(final ResultVo vo, final ResultExpandParameter parameter) {
        requireNonNull(vo);
        requireNonNull(parameter);

        String key = vo.getKey();
        if (isBlank(key)) {
            key = vo.getParent().map(PlanVo::getKey).orElse("");
        }
        Optional<ResultsResponse> response = doResultCall(key, parameter.toString());
        List<Result> results = response.map(ResultsResponse::getResults).map(Results::getResult).orElse(emptyList());

        if (!results.isEmpty()) {
            var res = results.iterator().next();
            if (ResultExpandParameter.CHANGES.equals(parameter)) {
                vo.setChanges(conv.apply(new ChangeVoConverter()).convert(res.getChanges()));
            } else if (ResultExpandParameter.JIRA.equals(parameter)) {
                vo.setIssues(conv.apply(new IssueVoConverter()).convert(res.getJiraIssues()));
            }
        }
    }

    private Optional<ResultsResponse> doResultCall(String resultKey, String expandParameter) {
        String path = format(RESULT, resultKey);
        Map<String, String> params = singletonMap(EXPAND, expandParameter);
        ApiCallable<ResultsResponse> caller = apiCallerFactory.newCaller(ResultsResponse.class, path, params);
        return caller.createTarget().map(caller::doGet).orElse(empty());
    }

    @Override
    Response queue(PlanVo plan) {
        requireNonNull(plan);

        String path = format(QUEUE, plan.getKey());
        ApiCallable caller = apiCallerFactory.newCaller(Object.class, path);
        Optional<WebTarget> target = caller.createTarget();

        return target.map(t -> {
            LOG.info("queued build for: {}", t);
            return caller.doPost(t);
        }).orElseGet(() -> {
            LOG.info("did not queue the build for: {}", path);
            return Response.status(Status.NOT_FOUND).build();
        });
    }

    @Override
    void updateProjects(Collection<ProjectVo> projects) {
        requireNonNull(projects);

        Collection<ProjectVo> source = getProjects();
        if (!source.isEmpty()) {
            ProjectsUpdater updater = new ProjectsUpdater();
            updater.update(source, projects);
        } else {
            projects.clear();
        }
    }

    @Override
    public Collection<ProjectVo> getProjects() {
        ProjectsFactory factory = new ProjectsFactory(getValues());
        try {

            Collection<Plan> plans = getPlans();
            Collection<Project> projects = doProjectsCall(plans.size());

            factory.setPlans(plans);
            factory.setProjects(projects);

        } catch (ServerErrorException | ProcessingException | NotFoundException exc) {
            LOG.info(exc.getMessage(), exc);
        }
        return factory.create();
    }

    private Collection<Project> doProjectsCall(int max) {
        Set<Project> results = new HashSet<>();
        Map<String, String> params = new HashMap<>();
        params.put(EXPAND, PROJECT_PLANS);
        params.put(ApiCallRepeatable.MAX, Integer.toString(max));
        ApiCallable caller = apiCallerFactory.newCaller(ProjectsResponse.class, PROJECTS, params);
        doSimpleCall(caller, results);
        return results;
    }

    /**
     * Load the available plans and their results.
     *
     * @return the available plans
     */
    private Collection<Plan> getPlans() {
        Collection<Plan> plans = doPlansCall();
        Collection<Result> results = doResultsCall();
        results.forEach(result -> {
            plans.forEach(plan -> {
                var key = result.getPlan().getKey();
                if (key.equals(plan.getKey())) {
                    plan.setResult(result);
                }
            });
        });
        return plans;
    }

    @Override
    public VersionInfo getVersionInfo() {
        ApiCallable<Info> infoCaller = apiCallerFactory.newCaller(Info.class, INFO);
        Optional<WebTarget> opt = infoCaller.createTarget();

        return opt.map(t -> {
            Info info = infoCaller.doGet(opt.get()).orElse(new Info());
            return new VersionInfoConverter().convert(info);
        }).orElseGet(() -> new VersionInfo());
    }

}
