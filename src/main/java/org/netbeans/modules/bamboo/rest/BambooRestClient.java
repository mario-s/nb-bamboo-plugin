package org.netbeans.modules.bamboo.rest;

import org.glassfish.jersey.logging.LoggingFeature;

import org.netbeans.modules.bamboo.glue.BuildProject;
import org.netbeans.modules.bamboo.glue.InstanceValues;
import org.netbeans.modules.bamboo.rest.model.Plan;
import org.netbeans.modules.bamboo.rest.model.PlansResponse;

import org.openide.util.lookup.ServiceProvider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Feature;
import org.netbeans.modules.bamboo.rest.model.Result;
import org.netbeans.modules.bamboo.rest.model.ResultsResponse;

/**
 * @author spindizzy
 */
@ServiceProvider(service = BambooServiceAccessable.class)
public class BambooRestClient implements BambooServiceAccessable {

    static final String REST_API = "/rest/api/latest";

    static final String RESULTS = "/result.json";
    static final String PLANS = "/plan.json";

    static final String RESULT = "/result/{buildKey}.json";

    private static final String PLAN = PLANS + "/{buildKey}.json";

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

    private Collection<Plan> getPlans(final InstanceValues values) {
        Set<Plan> plans = new HashSet<>();
        ApiCaller<PlansResponse> plansCaller = createPlansCaller(values);
        Optional<WebTarget> opt = plansCaller.createTarget();

        if (opt.isPresent()) {
            WebTarget target = opt.get();
            PlansResponse initialResponse = plansCaller.request(target);
            log.fine(String.format("got plans for initial call: %s", initialResponse));
            plans.addAll(initialResponse.asCollection());

            Optional<PlansResponse> secondResponse = plansCaller.doSecondCall(initialResponse);
            if (secondResponse.isPresent()) {
                plans.addAll(secondResponse.get().asCollection());
            }
        }

        return plans;
    }

    private Collection<Result> getResults(InstanceValues values) {
        Set<Result> results = new HashSet<>();
        ApiCaller<ResultsResponse> apiCaller = createResultsCaller(values);
        Optional<WebTarget> opt = apiCaller.createTarget();
        if (opt.isPresent()) {
            WebTarget target = opt.get();
            ResultsResponse initialResponse = apiCaller.request(target);
            log.fine(String.format("got results for initial call: %s", initialResponse));
            results.addAll(initialResponse.asCollection());

            Optional<ResultsResponse> secondResponse = apiCaller.doSecondCall(initialResponse);
            if (secondResponse.isPresent()) {
                results.addAll(secondResponse.get().asCollection());
            }
        }

        return results;
    }

    ApiCaller<ResultsResponse> createResultsCaller(InstanceValues values) {
        ApiCaller<ResultsResponse> apiCaller = new ApiCaller<>(values, ResultsResponse.class, RESULTS);
        return apiCaller;
    }

    ApiCaller<PlansResponse> createPlansCaller(final InstanceValues values) {
        return new ApiCaller<>(values, PlansResponse.class, PLANS);
    }
}
