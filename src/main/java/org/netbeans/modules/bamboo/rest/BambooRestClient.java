package org.netbeans.modules.bamboo.rest;

import org.netbeans.modules.bamboo.glue.InstanceValues;

import org.openide.util.lookup.ServiceProvider;

import java.util.logging.Logger;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;


/**
 * @author spindizzy
 */
@ServiceProvider(service = BambooInstanceAccessable.class)
public class BambooRestClient implements BambooInstanceAccessable {
    static final String AUTH_TYPE = "os_authType";
    static final String BASIC = "basic";
    static final String USER = "os_username";
    static final String PASS = "os_password";

    static final String REST_API = "/rest/api/latest";

    static final String RESULT = "/result/{buildKey}.json";
    static final String ALL_PLANS = "/plan";
    private static final String PLAN = ALL_PLANS + "/{buildKey}.json";

    private final Logger log;

    public BambooRestClient() {
        this.log = Logger.getLogger(getClass().getName());
    }

    private WebTarget target(final InstanceValues values, final String path) {
        String url = values.getUrl();
        String user = values.getUsername();
        String password = String.valueOf(values.getPassword());

        return newTarget(url).path(REST_API).path(path).queryParam(AUTH_TYPE, BASIC)
                             .queryParam(USER, user).queryParam(PASS, password);
    }

    WebTarget newTarget(final String url) {
        return ClientBuilder.newClient().target(url);
    }

    @Override
    public Plans getPlans(final InstanceValues values) {
        Plans entity = target(values, ALL_PLANS).request().get(Plans.class);

        log.fine(String.format("got plans: %s", entity));

        return entity;
    }

    @Override
    public ResultsResponse getResultsResponse(final InstanceValues values) {
        ResultsResponse entity = target(values, RESULT).request().get(ResultsResponse.class);

        log.fine(String.format("got results: %s", entity));

        return entity;
    }
}
