package org.netbeans.modules.bamboo.rest;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import org.netbeans.modules.bamboo.glue.InstanceValues;
import org.netbeans.modules.bamboo.glue.VersionInfo;
import org.netbeans.modules.bamboo.model.rest.Info;
import org.netbeans.modules.bamboo.model.rest.Plan;
import org.netbeans.modules.bamboo.model.rest.PlansResponse;
import org.netbeans.modules.bamboo.model.rest.Result;
import org.netbeans.modules.bamboo.model.rest.ResultsResponse;

import org.openide.util.lookup.ServiceProvider;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.ServerErrorException;

import javax.ws.rs.client.WebTarget;
import org.netbeans.modules.bamboo.model.PlanVo;
import org.netbeans.modules.bamboo.model.ProjectVo;
import org.netbeans.modules.bamboo.model.ResultVo;
import org.netbeans.modules.bamboo.model.rest.AbstractResponse;
import org.netbeans.modules.bamboo.model.rest.Project;
import org.netbeans.modules.bamboo.model.rest.ProjectsResponse;
import org.netbeans.modules.bamboo.rest.AbstractVoConverter.PlanVoConverter;
import org.netbeans.modules.bamboo.rest.AbstractVoConverter.ProjectVoConverter;
import org.netbeans.modules.bamboo.rest.AbstractVoConverter.ResultVoConverter;
import org.netbeans.modules.bamboo.rest.AbstractVoUpdater.ProjectsUpdater;

/**
 * @author spindizzy
 */
@ServiceProvider(service = BambooServiceAccessable.class)
public class BambooRestClient implements BambooServiceAccessable {

    private static final ProjectVoConverter PROJECT_CONVERTER = new ProjectVoConverter();
    private static final PlanVoConverter PLAN_CONVERTER = new PlanVoConverter();
    private static final ResultVoConverter RESULT_CONVERTER = new ResultVoConverter();

    static final String REST_API = "/rest/api/latest";

    static final String EXPAND = "expand";
    static final String PROJECT_PLANS = "projects.project.plans.plan";
    static final String RESULT_COMMENTS = "results.result.comments";

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
    public void updateProjects(Collection<ProjectVo> projects, InstanceValues values) {
        Collection<ProjectVo> source = getProjects(values);
        if (!source.isEmpty()) {
            ProjectsUpdater updater = new ProjectsUpdater();
            updater.update(source, projects);
        }
    }

    @Override
    public Collection<ProjectVo> getProjects(final InstanceValues values) {
        Collection<ProjectVo> vos = new ArrayList<>();

        try {

            Collection<Plan> plans = getPlans(values);

            Collection<Project> projects = doProjectsCall(values, plans.size());

            projects.forEach(project -> {

                ProjectVo projectVo = PROJECT_CONVERTER.convert(project);
                projectVo.setServerUrl(values.getUrl());

                List<PlanVo> planVos = new ArrayList<>();

                project.plansAsCollection().forEach(projectPlan -> {
                    String planKey = projectPlan.getKey();
                    Optional<PlanVo> extracted = extractPlan(planKey, plans);
                    if (extracted.isPresent()) {
                        PlanVo planVo = extracted.get();
                        planVo.setServerUrl(values.getUrl());
                        planVos.add(planVo);
                    }
                });

                projectVo.setPlans(planVos);

                vos.add(projectVo);
            });
        } catch (ServerErrorException exc) {
            log.log(Level.FINE, exc.getMessage(), exc);
        }
        return vos;
    }

    private Optional<PlanVo> extractPlan(String planKey, Collection<Plan> plans) {
        Optional<PlanVo> vo = empty();
        for (Plan plan : plans) {
            if (planKey.equals(plan.getKey())) {
                PlanVo planVo = PLAN_CONVERTER.convert(plan);

                Result result = plan.getResult();
                if (result != null) {
                    ResultVo resultVo = RESULT_CONVERTER.convert(result);
                    planVo.setResult(resultVo);
                }

                vo = of(planVo);
            }
        }
        return vo;

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
            versionInfo.setVersion(info.getVersion());
            versionInfo.setBuildNumber(info.getBuildNumber());

            final String buildDate = info.getBuildDate();

            if (isNotBlank(buildDate)) {
                try {
                    DateTimeFormatter formater = new DateTimeFormatterBuilder()
                            .appendPattern(BUILD_DATE_PATTERN)
                            .appendOffset("+HH:MM", "+00:00")
                            .toFormatter();
                    versionInfo.setBuildDate(LocalDate.parse(buildDate, formater));
                } catch (DateTimeParseException ex) {
                    log.fine(ex.getMessage());
                }
            }
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
