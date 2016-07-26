package org.netbeans.modules.bamboo.rest;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import org.glassfish.jersey.logging.LoggingFeature;

import org.netbeans.modules.bamboo.glue.InstanceValues;
import org.netbeans.modules.bamboo.rest.model.PlansResponse;
import org.netbeans.modules.bamboo.rest.model.ResultsResponse;

import org.openide.util.lookup.ServiceProvider;

import java.util.Optional;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Feature;


/**
 * @author spindizzy
 */
@ServiceProvider(service = BambooServiceAccessable.class)
public class BambooRestClient implements BambooServiceAccessable {
    static final String AUTH_TYPE = "os_authType";
    static final String BASIC = "basic";
    static final String USER = "os_username";
    static final String PASS = "os_password";

    static final String REST_API = "/rest/api/latest";

    static final String RESULT = "/result/{buildKey}.json";
    static final String ALL_PLANS = "/plan.json";
    private static final String PLAN = ALL_PLANS + "/{buildKey}.json";

    private final Logger log;
    private final Feature logFeature;

    public BambooRestClient() {
        this.log = Logger.getLogger(getClass().getName());
        this.logFeature = new LoggingFeature(log, Level.INFO, null, null);
    }

    private Optional<WebTarget> newTarget(final InstanceValues values, final String path) {
        Optional<WebTarget> opt = empty();
        String url = values.getUrl();
        String user = values.getUsername();
        char[] chars = values.getPassword();

        if (StringUtils.isNotBlank(url) && StringUtils.isNotBlank(user) &&
                ArrayUtils.isNotEmpty(chars)) {
            String password = String.valueOf(chars);

            opt = of(
                    newTarget(url).path(REST_API).path(path).queryParam(AUTH_TYPE, BASIC)
                    .queryParam(USER, user).queryParam(PASS, password));
        } else if (log.isLoggable(Level.WARNING)) {
            log.warning("Invalid values for instance");
        }

        return opt;
    }

    WebTarget newTarget(final String url) {
        return ClientBuilder.newClient().register(logFeature).target(url);
    }

    private <T> T request(final WebTarget target, final Class<T> clazz) {
        return target.request().get(clazz);
    }

    @Override
    public PlansResponse getAllPlans(final InstanceValues values) {
        PlansResponse plans = new PlansResponse();
        Optional<WebTarget> target = newTarget(values, ALL_PLANS);

        if (target.isPresent()) {
            plans = request(target.get(), PlansResponse.class);

            log.fine(String.format("got plans: %s", plans));
        }

        return plans;
    }

    @Override
    public ResultsResponse getResultsResponse(final InstanceValues values) {
        ResultsResponse results = new ResultsResponse();
        Optional<WebTarget> target = newTarget(values, RESULT);

        if (target.isPresent()) {
            results = request(target.get(), ResultsResponse.class);

            log.fine(String.format("got results: %s", results));
        }

        return results;
    }
}
