package org.netbeans.modules.bamboo.rest;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import org.netbeans.modules.bamboo.model.BuildProject;
import org.netbeans.modules.bamboo.glue.InstanceValues;
import org.netbeans.modules.bamboo.glue.VersionInfo;
import org.netbeans.modules.bamboo.model.Info;
import org.netbeans.modules.bamboo.model.Plan;
import org.netbeans.modules.bamboo.model.PlansResponse;
import org.netbeans.modules.bamboo.model.Result;
import org.netbeans.modules.bamboo.model.ResultsResponse;

import org.openide.util.lookup.ServiceProvider;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.ServerErrorException;

import javax.ws.rs.client.WebTarget;
import org.netbeans.modules.bamboo.model.AbstractResponse;
import org.netbeans.modules.bamboo.model.Project;
import org.netbeans.modules.bamboo.model.ProjectsResponse;

/**
 * @author spindizzy
 */
@ServiceProvider(service = BambooServiceAccessable.class)
public class BambooRestClient implements BambooServiceAccessable {

    static final String REST_API = "/rest/api/latest";
    
    static final String EXPAND = "expand";
    static final String EXPAND_PROJ_PLANS = "projects.project.plans";

    static final String PROJECTS = "/project.json";
    static final String RESULTS = "/result.json";
    static final String PLANS = "/plan.json";
    static final String INFO = "/info.json";

    static final String RESULT = "/result/{buildKey}.json";

    private static final String PLAN = PLANS + "/{buildKey}.json";

    private static final String BUILD_DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS";

    private final Logger log;

    public BambooRestClient() {
        this.log = Logger.getLogger(getClass().getName());
    }

    @Override
    public Collection<BuildProject> getProjects(final InstanceValues values) {
        List<BuildProject> buildProjects = new ArrayList<>();

        try {
            Collection<Project> projects = doProjectsCall(values);
            Collection<Plan> plans = doPlansCall(values);
            Collection<Result> results = doResultsCall(values);
            
            results.forEach(result -> {
                plans.forEach(plan -> {
                   if(result.getPlan().getKey().equals(plan.getKey())) {
                       plan.setResult(result);
                   }
                });
            });
            
            
            projects.forEach(project -> {
                BuildProject buildProject = new BuildProject();
                buildProject.setServerUrl(values.getUrl());
                buildProject.setKey(project.getKey());
                buildProject.setName(project.getName());
                buildProject.setPlans(project.getPlans().getPlan());
                buildProjects.add(buildProject);
                
                buildProject.getPlans().forEach(buildPlan -> {
                    buildPlan.setServerUrl(values.getUrl());
                    plans.forEach(plan -> {
                        if(buildPlan.getKey().equals(plan.getKey())){
                            buildPlan.setEnabled(plan.isEnabled());
                            buildPlan.setName(plan.getName());
                            buildPlan.setShortName(plan.getShortName());
                            buildPlan.setResult(plan.getResult());
                        }
                    });
                });
            });
        } catch (ServerErrorException exc) {
           log.log(Level.WARNING, exc.getMessage(), exc);
        }
        return buildProjects;
    }

    @Override
    public VersionInfo getVersionInfo(final InstanceValues values) {
        VersionInfo versionInfo = new VersionInfo();
        ApiCaller<Info> infoCaller = createInfoCaller(values);
        Optional<WebTarget> opt = infoCaller.createTarget();

        if (opt.isPresent()) {
            Info info = infoCaller.request(opt.get());
            versionInfo.setVersion(info.getVersion());
            versionInfo.setBuildNumber(info.getBuildNumber());

            final String buildDate = info.getBuildDate();

            if (isNotBlank(buildDate)) {
                try {
                    DateFormat df = new SimpleDateFormat(BUILD_DATE_PATTERN);
                    versionInfo.setBuildDate(df.parse(buildDate));
                } catch (ParseException ex) {
                    log.fine(ex.getMessage());
                }
            }
        }

        return versionInfo;
    }
    
    private Collection<Project> doProjectsCall(final InstanceValues values) {
        Set<Project> results = new HashSet<>();
        doRepeatableCall(createProjectCaller(values), results);
        return results;
    }

    private Collection<Plan> doPlansCall(final InstanceValues values) {
        Set<Plan> results = new HashSet<>();
        doRepeatableCall(createPlansCaller(values), results);
        return results;
    }

    private Collection<Result> doResultsCall(final InstanceValues values) {
        Set<Result> results = new HashSet<>();
        doRepeatableCall(createResultsCaller(values), results);
        return results;
    }
    
    private void doRepeatableCall(RepeatApiCaller<? extends AbstractResponse> apiCaller, Set results){
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

    RepeatApiCaller<ResultsResponse> createResultsCaller(final InstanceValues values) {
        RepeatApiCaller<ResultsResponse> apiCaller
                = new RepeatApiCaller<>(values, ResultsResponse.class, RESULTS);

        return apiCaller;
    }

    RepeatApiCaller<PlansResponse> createPlansCaller(final InstanceValues values) {
        return new RepeatApiCaller<>(values, PlansResponse.class, PLANS);
    }
    
    RepeatApiCaller<ProjectsResponse> createProjectCaller(final InstanceValues values) {
        Map<String,String> params = new HashMap<>();
        params.put(EXPAND, EXPAND_PROJ_PLANS);
        return new RepeatApiCaller<>(values, ProjectsResponse.class, PROJECTS, params);
    }

    ApiCaller<Info> createInfoCaller(final InstanceValues values) {
        return new ApiCaller<>(values, Info.class, INFO);
    }
}
