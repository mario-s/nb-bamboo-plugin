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
import lombok.extern.java.Log;
import org.netbeans.modules.bamboo.model.ProjectVo;
import org.netbeans.modules.bamboo.model.rest.AbstractResponse;
import org.netbeans.modules.bamboo.model.rest.Project;
import org.netbeans.modules.bamboo.model.rest.ProjectsResponse;
import org.netbeans.modules.bamboo.glue.VoConverter.VersionInfoConverter;
import org.netbeans.modules.bamboo.rest.AbstractVoUpdater.ProjectsUpdater;
import org.netbeans.modules.bamboo.glue.BambooClient;
import org.netbeans.modules.bamboo.model.PlanVo;
import org.netbeans.modules.bamboo.model.rest.ServiceInfoProvideable;

import static java.util.Collections.singletonMap;

/**
 * @author spindizzy
 */
@Log
class DefaultBambooClient implements BambooClient {

    static final String EXPAND = "expand";
    static final String PROJECT_PLANS = "projects.project.plans.plan";
    static final String RESULT_COMMENTS = "results.result.comments";

    static final String PROJECTS = "/project.json";
    static final String RESULTS = "/result.json";
    static final String PLANS = "/plan.json";
    static final String INFO = "/info.json";

    static final String RESULT = "/result/{buildKey}.json";

    private static final String PLAN = PLANS + "/{buildKey}.json";

    private final InstanceValues values;

    private final HttpUtility utility;

    private final ApiCallerFactory apiCallerFactory;

    DefaultBambooClient(InstanceValues values) {
        this(values, new HttpUtility());
    }

    DefaultBambooClient(InstanceValues values, HttpUtility utility) {
        this.values = values;
        this.utility = utility;
        apiCallerFactory = new ApiCallerFactory(values);
    }

    private Collection<Plan> doPlansCall() {
        Set<Plan> results = new HashSet<>();
        RepeatApiCaller caller = apiCallerFactory.newRepeatCaller(PlansResponse.class, PLANS);
        doRepeatableCall(caller, results);
        return results;
    }

    private Collection<Result> doResultsCall() {
        Set<Result> results = new HashSet<>();
        Map<String, String> params = singletonMap(EXPAND, RESULT_COMMENTS);
        RepeatApiCaller caller = apiCallerFactory.newRepeatCaller(ResultsResponse.class, RESULTS, params);
        doRepeatableCall(caller, results);
        return results;
    }
    private void doRepeatableCall(RepeatApiCaller<? extends AbstractResponse> apiCaller, Set<? extends ServiceInfoProvideable> results) {
        
        apiCaller.createTarget().ifPresent(target -> {
            AbstractResponse initialResponse = apiCaller.get(target);
            log.fine(String.format("got results for initial call: %s", initialResponse));
            results.addAll(initialResponse.asCollection());
            
            apiCaller.repeat(initialResponse).ifPresent(response -> {
                results.addAll(response.asCollection());
            });
        });
    }
    private void doSimpleCall(ApiCaller<? extends AbstractResponse> apiCaller, Set results) {
        
        apiCaller.createTarget().ifPresent(target -> {
            AbstractResponse initialResponse = apiCaller.get(target);
            log.fine(String.format("got results for initial call: %s", initialResponse));
            results.addAll(initialResponse.asCollection());
        });
    }

    @Override
    public boolean existsService() {
        return utility.exists(values.getUrl());
    }

    @Override
    public int queue(ProjectVo project, PlanVo plan) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void updateProjects(Collection<ProjectVo> projects) {
        Collection<ProjectVo> source = getProjects();
        if (!source.isEmpty()) {
            ProjectsUpdater updater = new ProjectsUpdater();
            updater.update(source, projects);
        }
    }

    @Override
    public Collection<ProjectVo> getProjects() {
        ProjectsFactory factory = new ProjectsFactory(values);
        try {

            Collection<Plan> plans = getPlans();
            Collection<Project> projects = doProjectsCall(plans.size());

            factory.setPlans(plans);
            factory.setProjects(projects);

        } catch (ServerErrorException | ProcessingException | NotFoundException exc) {
            log.log(Level.WARNING, exc.getMessage(), exc);
        }
        return factory.create();
    }

    private Collection<Project> doProjectsCall(int max) {
        Set<Project> results = new HashSet<>();
        Map<String, String> params = new HashMap<>();
        params.put(EXPAND, PROJECT_PLANS);
        params.put(RepeatApiCaller.MAX, Integer.toString(max));
        ApiCaller caller = apiCallerFactory.newCaller(ProjectsResponse.class, PROJECTS, params);
        doSimpleCall(caller, results);
        return results;
    }

    /**
     * Load the available plans and their results.
     *
     * @param values {@link InstanceValues}

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
        ApiCaller<Info> infoCaller = apiCallerFactory.newCaller(Info.class, INFO);
        Optional<WebTarget> opt = infoCaller.createTarget();

        if (opt.isPresent()) {
            Info info = infoCaller.get(opt.get());
            VersionInfoConverter converter = new VersionInfoConverter();
            versionInfo = converter.convert(info);
        }

        return versionInfo;
    }

}
