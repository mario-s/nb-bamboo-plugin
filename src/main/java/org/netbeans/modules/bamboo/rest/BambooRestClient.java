package org.netbeans.modules.bamboo.rest;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

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
import static java.util.Optional.empty;
import static java.util.Optional.of;
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

    static final String AUTH_TYPE = "os_authType";
    static final String BASIC = "basic";
    static final String USER = "os_username";
    static final String PASS = "os_password";
    static final String START = "start-index";
    static final String MAX = "max-results";

    static final String REST_API = "/rest/api/latest";

    static final String RESULTS = "/result.json";
    static final String PLANS = "/plan.json";

    static final String RESULT = "/result/{buildKey}.json";

    private static final String PLAN = PLANS + "/{buildKey}.json";

    private final Logger log;
    private final Feature logFeature;

    public BambooRestClient() {
        this.log = Logger.getLogger(getClass().getName());
        this.logFeature = new LoggingFeature(log, Level.INFO, null, null);
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
        Optional<WebTarget> opt = createTarget(values, PLANS);

        if (opt.isPresent()) {
            WebTarget target = opt.get();
            PlansResponse initialResponse = request(target, PlansResponse.class);
            log.fine(String.format("got plans for initial call: %s", initialResponse));
            plans.addAll(initialResponse.getPlansAsCollection());

            Optional<PlansResponse> secondResponse = doSecondPlanCall(initialResponse, values);
            if (secondResponse.isPresent()) {
                plans.addAll(secondResponse.get().getPlansAsCollection());
            }
        }

        return plans;
    }

    private Optional<WebTarget> createTarget(final InstanceValues values, final String path) {
        Optional<WebTarget> opt = empty();
        String url = values.getUrl();
        String user = values.getUsername();
        char[] chars = values.getPassword();
        
        if (StringUtils.isNotBlank(url) && StringUtils.isNotBlank(user)
                && ArrayUtils.isNotEmpty(chars)) {
            
            opt = of(newTarget(values, path));
        } else if (log.isLoggable(Level.WARNING)) {
            log.warning("Invalid values for instance");
        }
        
        return opt;
    }

    private Optional<PlansResponse> doSecondPlanCall(final PlansResponse initial, final InstanceValues values) {
        int max = initial.getPlans().getMaxResult();
        int size = initial.getPlans().getSize();

        Optional<PlansResponse> opt = empty();

        if (size > max) {
            WebTarget target = newTarget(values, PLANS).queryParam(MAX, size);
            PlansResponse response = request(target, PlansResponse.class);
            log.fine(String.format("got all other plans: %s", response));
            opt = of(response);
        }

        return opt;
    }

    private Collection<Result> getResults(InstanceValues values) {
        Set<Result> results = new HashSet<>();
        Optional<WebTarget> opt = createTarget(values, RESULTS);
        if(opt.isPresent()){
            WebTarget target = opt.get();
            ResultsResponse initialResponse = request(target, ResultsResponse.class);
            log.fine(String.format("got results for initial call: %s", initialResponse));
            results.addAll(initialResponse.getResultsAsCollection());
            
            Optional<ResultsResponse> secondResponse = doSecondResultCall(initialResponse, values);
            if(secondResponse.isPresent()) {
                results.addAll(secondResponse.get().getResultsAsCollection());
            }
        }
        
        return results;
    }
    
     private Optional<ResultsResponse> doSecondResultCall(final ResultsResponse initial, final InstanceValues values) {
        int max = initial.getResults().getMaxResult();
        int size = initial.getResults().getSize();

        Optional<ResultsResponse> opt = empty();

        if (size > max) {
            WebTarget target = newTarget(values, RESULTS).queryParam(MAX, size);
            ResultsResponse response = request(target, ResultsResponse.class);
            log.fine(String.format("got all other results: %s", response));
            opt = of(response);
        }

        return opt;
    }

    private WebTarget newTarget(final InstanceValues values, final String path) {
        String url = values.getUrl();
        String user = values.getUsername();
        char[] chars = values.getPassword();
        String password = String.valueOf(chars);

        return newTarget(url, path).queryParam(AUTH_TYPE, BASIC)
                .queryParam(USER, user).queryParam(PASS, password);
    }

    private WebTarget newTarget(String url, final String path) {
        return newTarget(url).path(REST_API).path(path);
    }

    WebTarget newTarget(final String url) {
        return ClientBuilder.newClient().register(logFeature).target(url);
    }

    private <T> T request(final WebTarget target, final Class<T> clazz) {
        return target.request().get(clazz);
    }

}
