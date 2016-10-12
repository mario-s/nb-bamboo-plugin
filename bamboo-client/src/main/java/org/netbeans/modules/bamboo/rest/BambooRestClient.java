package org.netbeans.modules.bamboo.rest;

import org.netbeans.modules.bamboo.glue.BambooServiceAccessable;

import org.netbeans.modules.bamboo.model.InstanceValues;
import org.netbeans.modules.bamboo.model.VersionInfo;
import org.netbeans.modules.bamboo.model.rest.Info;
import org.netbeans.modules.bamboo.model.rest.Plan;
import org.netbeans.modules.bamboo.model.rest.PlansResponse;
import org.netbeans.modules.bamboo.model.rest.Result;
import org.netbeans.modules.bamboo.model.rest.ResultsResponse;

import org.openide.util.lookup.ServiceProvider;

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

/**
 * @author spindizzy
 */
@Log
@ServiceProvider(service = BambooServiceAccessable.class)
public class BambooRestClient implements BambooServiceAccessable {

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

    private HttpUtility httpUtility;

    public BambooRestClient(InstanceValues values) {
        this.values = values;
        httpUtility = new HttpUtility();
    }

    @Deprecated
    public BambooRestClient() {
        this(null);
    }

    @Override
    public boolean existsService(InstanceValues values) {
        String url = values.getUrl();
        return httpUtility.exists(url);
    }

    @Override
    public boolean existsService() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public void updateProjects(Collection<ProjectVo> projects) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Collection<ProjectVo> getProjects() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public VersionInfo getVersionInfo() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void updateProjects(Collection<ProjectVo> projects, InstanceValues values) {
        Collection<ProjectVo> source = getProjects(values);
        if (!source.isEmpty()) {
            ProjectsUpdater updater = new ProjectsUpdater();
            updater.update(source, projects);
        }
    }

    @Override
    public Collection<ProjectVo> getProjects(final InstanceValues values) {
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
    public VersionInfo getVersionInfo(final InstanceValues values) {
        VersionInfo versionInfo = new VersionInfo();
        ApiCaller<Info> infoCaller = createInfoCaller(values);
        Optional<WebTarget> opt = infoCaller.createTarget();

        if (opt.isPresent()) {
            Info info = infoCaller.request(opt.get());
            VersionInfoConverter converter = new VersionInfoConverter();
            versionInfo = converter.convert(info);
        }

        return versionInfo;
    }

    private Collection<Project> doProjectsCall(final InstanceValues values, int max) {
        Set<Project> results = new HashSet<>();
        Map<String, String> params = new HashMap<>();
        params.put(EXPAND, PROJECT_PLANS);
        params.put(ApiCaller.MAX, Integer.toString(max));
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
        Optional<WebTarget> opt = apiCaller.createTarget();

        if (opt.isPresent()) {
            AbstractResponse initialResponse = apiCaller.request(opt.get());
            log.fine(String.format("got results for initial call: %s", initialResponse));
            results.addAll(initialResponse.asCollection());
        }
    }

    /**
     * Repeats the API call.
     *
     * @param apiCaller
     * @param results
     */
    private void doRepeatableCall(RepeatApiCaller<? extends AbstractResponse> apiCaller, Set results) {
        Optional<WebTarget> opt = apiCaller.createTarget();

        if (opt.isPresent()) {
            AbstractResponse initialResponse = apiCaller.request(opt.get());
            log.fine(String.format("got results for initial call: %s", initialResponse));
            results.addAll(initialResponse.asCollection());

            Optional<? extends AbstractResponse> secondResponse = apiCaller.doSecondCall(initialResponse);

            if (secondResponse.isPresent()) {
                results.addAll(secondResponse.get().asCollection());
            }
        }
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
