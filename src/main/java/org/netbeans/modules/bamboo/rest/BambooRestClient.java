package org.netbeans.modules.bamboo.rest;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import org.netbeans.modules.bamboo.glue.BuildProject;
import org.netbeans.modules.bamboo.glue.InstanceValues;
import org.netbeans.modules.bamboo.glue.VersionInfo;
import org.netbeans.modules.bamboo.rest.model.Info;
import org.netbeans.modules.bamboo.rest.model.Plan;
import org.netbeans.modules.bamboo.rest.model.PlansResponse;
import org.netbeans.modules.bamboo.rest.model.Result;
import org.netbeans.modules.bamboo.rest.model.ResultsResponse;

import org.openide.util.Exceptions;
import org.openide.util.lookup.ServiceProvider;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

import javax.ws.rs.client.WebTarget;


/**
 * @author spindizzy
 */
@ServiceProvider(service = BambooServiceAccessable.class)
public class BambooRestClient implements BambooServiceAccessable {
    static final String REST_API = "/rest/api/latest";

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
        List<BuildProject> projects = new ArrayList<>();

        Collection<Plan> plans = getPlans(values);

        plans.forEach(plan -> {
                BuildProject project = new BuildProject();
                project.setServerUrl(values.getUrl());
                project.setKey(plan.getKey());
                project.setName(plan.getName());
                project.setShortName(plan.getShortName());
                project.setEnabled(plan.isEnabled());
                projects.add(project);
            });

        Collection<Result> results = getResults(values);

        results.forEach(result -> {
                projects.forEach(project -> {
                        Plan plan = result.getPlan();

                        if (plan.getKey().equals(project.getKey())) {
                            project.setState(result.getState());
                        }
                    });
            });

        return projects;
    }

    @Override
    public VersionInfo getVersion(final InstanceValues values) {
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

    private Collection<Plan> getPlans(final InstanceValues values) {
        Set<Plan> plans = new HashSet<>();
        RepeatApiCaller<PlansResponse> plansCaller = createPlansCaller(values);
        Optional<WebTarget> opt = plansCaller.createTarget();

        if (opt.isPresent()) {
            PlansResponse initialResponse = plansCaller.request(opt.get());
            log.fine(String.format("got plans for initial call: %s", initialResponse));
            plans.addAll(initialResponse.asCollection());

            Optional<PlansResponse> secondResponse = plansCaller.doSecondCall(initialResponse);

            if (secondResponse.isPresent()) {
                plans.addAll(secondResponse.get().asCollection());
            }
        }

        return plans;
    }

    private Collection<Result> getResults(final InstanceValues values) {
        Set<Result> results = new HashSet<>();
        RepeatApiCaller<ResultsResponse> apiCaller = createResultsCaller(values);
        Optional<WebTarget> opt = apiCaller.createTarget();

        if (opt.isPresent()) {
            ResultsResponse initialResponse = apiCaller.request(opt.get());
            log.fine(String.format("got results for initial call: %s", initialResponse));
            results.addAll(initialResponse.asCollection());

            Optional<ResultsResponse> secondResponse = apiCaller.doSecondCall(initialResponse);

            if (secondResponse.isPresent()) {
                results.addAll(secondResponse.get().asCollection());
            }
        }

        return results;
    }

    RepeatApiCaller<ResultsResponse> createResultsCaller(final InstanceValues values) {
        RepeatApiCaller<ResultsResponse> apiCaller =
            new RepeatApiCaller<>(values, ResultsResponse.class, RESULTS);

        return apiCaller;
    }

    RepeatApiCaller<PlansResponse> createPlansCaller(final InstanceValues values) {
        return new RepeatApiCaller<>(values, PlansResponse.class, PLANS);
    }

    ApiCaller<Info> createInfoCaller(final InstanceValues values) {
        return new ApiCaller<>(values, Info.class, INFO);
    }
}
