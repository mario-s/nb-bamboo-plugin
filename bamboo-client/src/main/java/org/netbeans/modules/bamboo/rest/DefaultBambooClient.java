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

/**
 * @author spindizzy
 */
@Log
public class DefaultBambooClient implements BambooClient {

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

    public DefaultBambooClient(InstanceValues values, HttpUtility utility) {
        this.values = values;
        this.utility = utility;
    }

    @Override
    public boolean existsService() {
        return utility.exists(values.getUrl());
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

            Collection<Plan> plans = getPlans(values);
            Collection<Project> projects = doProjectsCall(values, plans.size());

            factory.setPlans(plans);
            factory.setProjects(projects);

        } catch (ServerErrorException | ProcessingException | NotFoundException exc) {
            log.log(Level.FINE, exc.getMessage(), exc);
        }
        return factory.create();
    }

    /**
     * Load the available plans and their results.
     *
     * @param values {@link InstanceValues}
     * @return the avlailable plans
     */
    private Collection<Plan> getPlans(final InstanceValues values) {
        Collection<Plan> plans = doPlansCall(values);
        Collection<Result> results = doResultsCall(values);
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
        ApiCaller<Info> infoCaller = createInfoCaller(values);
        Optional<WebTarget> opt = infoCaller.createTarget();

        if (opt.isPresent()) {
            Info info = infoCaller.get(opt.get());
            VersionInfoConverter converter = new VersionInfoConverter();
            versionInfo = converter.convert(info);
        }

        return versionInfo;
    }

    private Collection<Project> doProjectsCall(final InstanceValues values, int max) {
        Set<Project> results = new HashSet<>();
        Map<String, String> params = new HashMap<>();
        params.put(EXPAND, PROJECT_PLANS);
        params.put(RepeatApiCaller.MAX, Integer.toString(max));
        doSimpleCall(createProjectCaller(values, params), results);
        return results;
    }

    private Collection<Plan> doPlansCall(final InstanceValues values) {
        Set<Plan> results = new HashSet<>();
        doRepeatableCall(createPlansCaller(values), results);
        return results;
    }

    private Collection<Result> doResultsCall(final InstanceValues values) {
        Set<Result> results = new HashSet<>();
        Map<String, String> params = new HashMap<>();
        params.put(EXPAND, RESULT_COMMENTS);
        doRepeatableCall(createResultsCaller(values, params), results);
        return results;
    }

    /**
     * Does a API call only once.
     *
     * @param apiCaller
     * @param results
     */
    private void doSimpleCall(ApiCaller<? extends AbstractResponse> apiCaller, Set results) {

        apiCaller.createTarget().ifPresent( target -> {
            AbstractResponse initialResponse = apiCaller.get(target);
            log.fine(String.format("got results for initial call: %s", initialResponse));
            results.addAll(initialResponse.asCollection());
        });
    }

    /**
     * Repeats the API call.
     *
     * @param apiCaller
     * @param results
     */
    private void doRepeatableCall(RepeatApiCaller<? extends AbstractResponse> apiCaller, Set results) {

        apiCaller.createTarget().ifPresent( target -> {
            AbstractResponse initialResponse = apiCaller.get(target);
            log.fine(String.format("got results for initial call: %s", initialResponse));
            results.addAll(initialResponse.asCollection());

            apiCaller.repeat(initialResponse).ifPresent( response -> {
                results.addAll(response.asCollection());
            });
        });
    }

    RepeatApiCaller<ResultsResponse> createResultsCaller(final InstanceValues values, Map<String, String> params) {
        return new RepeatApiCaller<>(values, ResultsResponse.class, RESULTS, params);
    }

    RepeatApiCaller<PlansResponse> createPlansCaller(final InstanceValues values) {
        return new RepeatApiCaller<>(values, PlansResponse.class, PLANS);
    }

    ApiCaller<ProjectsResponse> createProjectCaller(final InstanceValues values, Map<String, String> params) {
        return new ApiCaller<>(values, ProjectsResponse.class, PROJECTS, params);
    }

    ApiCaller<Info> createInfoCaller(final InstanceValues values) {
        return new ApiCaller<>(values, Info.class, INFO);
    }

}
