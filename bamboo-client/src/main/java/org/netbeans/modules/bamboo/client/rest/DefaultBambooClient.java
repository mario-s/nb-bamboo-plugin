package org.netbeans.modules.bamboo.client.rest;

import org.netbeans.modules.bamboo.client.rest.call.ApiCallRepeatable;
import org.netbeans.modules.bamboo.client.rest.call.ApiCallable;
import org.netbeans.modules.bamboo.client.rest.call.ApiCallerFactory;
import org.netbeans.modules.bamboo.model.rcp.InstanceValues;
import org.netbeans.modules.bamboo.model.rcp.VersionInfo;
import org.netbeans.modules.bamboo.model.rest.Info;
import org.netbeans.modules.bamboo.model.rest.Plan;
import org.netbeans.modules.bamboo.model.rest.PlansResponse;
import org.netbeans.modules.bamboo.model.rest.Result;
import org.netbeans.modules.bamboo.model.rest.ResultsResponse;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.ServerErrorException;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import lombok.NonNull;
import lombok.extern.java.Log;
import org.netbeans.modules.bamboo.model.rcp.ProjectVo;
import org.netbeans.modules.bamboo.model.rest.AbstractResponse;
import org.netbeans.modules.bamboo.model.rest.Project;
import org.netbeans.modules.bamboo.model.rest.ProjectsResponse;
import org.netbeans.modules.bamboo.model.convert.VersionInfoConverter;
import org.netbeans.modules.bamboo.client.rest.AbstractVoUpdater.ProjectsUpdater;
import org.netbeans.modules.bamboo.model.rcp.PlanVo;
import org.netbeans.modules.bamboo.model.rest.Responseable;
import org.netbeans.modules.bamboo.model.rest.ServiceInfoProvideable;

import static java.util.Collections.singletonMap;
import static java.lang.String.format;


import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.netbeans.modules.bamboo.client.glue.ExpandParameter.EXPAND;
import static org.netbeans.modules.bamboo.client.glue.ExpandParameter.PROJECT_PLANS;
import static org.netbeans.modules.bamboo.client.glue.ExpandParameter.RESULT_COMMENTS;
import static org.netbeans.modules.bamboo.client.glue.RestResources.INFO;
import static org.netbeans.modules.bamboo.client.glue.RestResources.PLANS;
import static org.netbeans.modules.bamboo.client.glue.RestResources.PROJECTS;
import static org.netbeans.modules.bamboo.client.glue.RestResources.QUEUE;
import static org.netbeans.modules.bamboo.client.glue.RestResources.RESULT;
import static org.netbeans.modules.bamboo.client.glue.RestResources.RESULTS;

import org.netbeans.modules.bamboo.model.convert.ChangesVoConverter;
import org.netbeans.modules.bamboo.model.rcp.ResultVo;

/**
 * @author spindizzy
 */
@Log
class DefaultBambooClient extends AbstractBambooClient {

    private final ApiCallerFactory apiCallerFactory;

    DefaultBambooClient(InstanceValues values) {
        this(values, new HttpUtility());
    }

    DefaultBambooClient(InstanceValues values, HttpUtility utility) {
        super(values, utility);
        apiCallerFactory = new ApiCallerFactory(values);
    }

    private Collection<Plan> doPlansCall() {
        Set<Plan> results = new HashSet<>();
        ApiCallRepeatable caller = apiCallerFactory.newRepeatCaller(PlansResponse.class, PLANS);
        doRepeatableCall(caller, results);
        return results;
    }

    private Collection<Result> doResultsCall() {
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
            Responseable initialResponse = apiCaller.doGet(target);
            logInitialResponse(initialResponse);
            results.addAll(initialResponse.asCollection());

            apiCaller.repeat(initialResponse).ifPresent(response -> {
                results.addAll(response.asCollection());
            });
        });
    }

    private void doSimpleCall(ApiCallable<? extends AbstractResponse> apiCaller, Set results) {

        apiCaller.createTarget().ifPresent(target -> {
            AbstractResponse initialResponse = apiCaller.doGet(target);
            logInitialResponse(initialResponse);
            results.addAll(initialResponse.asCollection());
        });
    }

    private void logInitialResponse(Responseable initialResponse) {
        if (log.isLoggable(Level.FINE)) {
            log.fine(format("got results for initial call: %s", initialResponse));
        }
    }

    @Override
    void attach(@NonNull ResultVo vo, String expandParameter) {
        String key = format("%s/%s", vo.getKey(), vo.getNumber());
        Optional<Result> result = doResultCall(key, expandParameter);

        result.ifPresent(res -> {
            ChangesVoConverter converter = new ChangesVoConverter();
            vo.setChanges(converter.convert(res.getChanges()));
        });
    }

    private Optional<Result> doResultCall(String resultKey, String expandParameter) {
        Optional<Result> result = empty();

        String path = format(RESULT, resultKey);
        Map<String, String> params = singletonMap(EXPAND, expandParameter);
        ApiCallable<Result> caller = apiCallerFactory.newCaller(Result.class, path, params);
        Optional<WebTarget> target = caller.createTarget();
        if (target.isPresent()) {
            Result response = caller.doGet(target.get());
            result = of(response);
        }

        return result;
    }

    @Override
    Response queue(@NonNull PlanVo plan) {
        Response response = Response.status(Status.NOT_FOUND).build();
        String path = format(QUEUE, plan.getKey());
        ApiCallable caller = apiCallerFactory.newCaller(Object.class, path);
        Optional<WebTarget> target = caller.createTarget();
        if (target.isPresent()) {
            response = caller.doPost(target.get());
            if (log.isLoggable(Level.INFO)) {
                log.info(format("queued build for: %s...got response: %s", path, response));
            }
        } else if (log.isLoggable(Level.INFO)) {
            log.info(format("did not queue the build for: %s", path));
        }
        return response;
    }

    @Override
    void updateProjects(@NonNull Collection<ProjectVo> projects) {
        Collection<ProjectVo> source = getProjects();
        if (!source.isEmpty()) {
            ProjectsUpdater updater = new ProjectsUpdater();
            updater.update(source, projects);
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
            log.log(Level.INFO, exc.getMessage(), exc);
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
     * @param values {@link InstanceValues}
     *
     * @return the avlailable plans
     */
    private Collection<Plan> getPlans() {
        Collection<Plan> plans = doPlansCall();
        Collection<Result> results = doResultsCall();
        results.forEach(result -> {
            plans.forEach(plan -> {
                if (result.getPlan().getKey().equals(plan.getKey())) {
                    plan.setResult(result);
                }
            });
        });
        return plans;
    }

    @Override
    public VersionInfo getVersionInfo() {
        VersionInfo versionInfo = new VersionInfo();
        ApiCallable<Info> infoCaller = apiCallerFactory.newCaller(Info.class, INFO);
        Optional<WebTarget> opt = infoCaller.createTarget();

        if (opt.isPresent()) {
            Info info = infoCaller.doGet(opt.get());
            VersionInfoConverter converter = new VersionInfoConverter();
            versionInfo = converter.convert(info);
        }

        return versionInfo;
    }

}
