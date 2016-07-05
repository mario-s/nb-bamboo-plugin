package org.netbeans.modules.bamboo.model;

import org.netbeans.modules.bamboo.model.AllPlansResponse;
import org.netbeans.modules.bamboo.model.AllResultsResponse;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import org.glassfish.jersey.logging.LoggingFeature;

import org.netbeans.modules.bamboo.glue.InstanceValues;

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
                    BambooRestClient.this.newTarget(url).path(REST_API).path(path).queryParam(
                        AUTH_TYPE,
                        BASIC).queryParam(USER, user).queryParam(PASS, password));
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
    public AllPlansResponse getAllPlans(final InstanceValues values) {
        AllPlansResponse plans = new AllPlansResponse();
        Optional<WebTarget> target = newTarget(values, ALL_PLANS);

        if (target.isPresent()) {
            plans = request(target.get(), AllPlansResponse.class);

            log.fine(String.format("got plans: %s", plans));
        }

        return plans;
    }

    @Override
    public AllResultsResponse getResultsResponse(final InstanceValues values) {
        AllResultsResponse results = new AllResultsResponse();
        Optional<WebTarget> target = newTarget(values, RESULT);

        if (target.isPresent()) {
            results = request(target.get(), AllResultsResponse.class);

            log.fine(String.format("got results: %s", results));
        }

        return results;
    }
}
