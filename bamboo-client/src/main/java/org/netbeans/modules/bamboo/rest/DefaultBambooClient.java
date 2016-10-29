package org.netbeans.modules.bamboo.rest;

import org.netbeans.modules.bamboo.model.InstanceValues;
import org.netbeans.modules.bamboo.model.VersionInfo;
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
import org.netbeans.api.annotations.common.NonNull;
import lombok.extern.java.Log;
import org.netbeans.modules.bamboo.model.ProjectVo;
import org.netbeans.modules.bamboo.model.rest.AbstractResponse;
import org.netbeans.modules.bamboo.model.rest.Project;
import org.netbeans.modules.bamboo.model.rest.ProjectsResponse;
import org.netbeans.modules.bamboo.glue.VoConverter.VersionInfoConverter;
import org.netbeans.modules.bamboo.rest.AbstractVoUpdater.ProjectsUpdater;
import org.netbeans.modules.bamboo.model.PlanVo;
import org.netbeans.modules.bamboo.model.rest.ServiceInfoProvideable;

import static java.util.Collections.singletonMap;
import static java.lang.String.format;

/**
 * @author spindizzy
 */
@Log
class DefaultBambooClient extends AbstractBambooClient {

    static final String EXPAND = "expand";
    static final String PROJECT_PLANS = "projects.project.plans.plan";
    static final String RESULT_COMMENTS = "results.result.comments";

    static final String PROJECTS = "/project" + ApiCallerFactory.JSON_PATH;
    static final String PLANS = "/plan" + ApiCallerFactory.JSON_PATH;
    static final String INFO = "/info" + ApiCallerFactory.JSON_PATH;
    static final String RESULTS = "/result";
    static final String QUEUE = "/queue/%s";

    static final String RESULT = "/result/{buildKey}";

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

    private void doRepeatableCall(ApiCallRepeatable<? extends AbstractResponse> apiCaller,
            Set<? extends ServiceInfoProvideable> results) {
        final Optional<WebTarget> opt = apiCaller.createTarget();
        opt.ifPresent(target -> {
            AbstractResponse initialResponse = apiCaller.doGet(target);
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

    private void logInitialResponse(AbstractResponse initialResponse) {
        if (log.isLoggable(Level.FINE)) {
            log.fine(format("got results for initial call: %s", initialResponse));
        }
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
                log.info(String.format("queued build for: %s...got response: %s", path, response));
            }
        }else if(log.isLoggable(Level.INFO)){
            log.info(String.format("did not queue the build for: %s", path));
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
        params.put(ApiCallRepeater.MAX, Integer.toString(max));
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
