package org.netbeans.modules.bamboo.rest;

import org.netbeans.modules.bamboo.glue.InstanceValues;

import org.openide.util.lookup.ServiceProvider;

import java.util.logging.Logger;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;


@ServiceProvider(service = BambooInstanceAccessable.class)
public class BambooRestClient implements BambooInstanceAccessable {
    private static final String AUTH_TYPE = "os_authType";
    private static final String BASIC = "basic";
    private static final String USER = "os_username";
    private static final String PASS = "os_password";

    private static final String REST_API = "/rest/api/latest";
    private static final String ALL_PLANS = REST_API + "/plan";
    private final String PLAN = ALL_PLANS + "/{buildKey}.json";
    private final String RESULT = REST_API + "/result/{buildKey}.json";

    private WebTarget target(final InstanceValues values, final String path) {
        String url = values.getUrl();
        String user = values.getName();
        String password = String.valueOf(values.getPassword());

        return ClientBuilder.newClient().target(url).path(path).queryParam(AUTH_TYPE, BASIC)
                            .queryParam(USER, user).queryParam(PASS, password);
    }

    @Override
    public Plans getPlans(final InstanceValues values) {
        Plans plans = new Plans();

        String entity = target(values, ALL_PLANS).request().get(String.class);

        Logger.getLogger(getClass().getName()).fine(entity);

        return plans;
    }

    @Override
    public ResultsResponse getResultsResponse(final InstanceValues values) {
        ResultsResponse response = new ResultsResponse();

        String entity = target(values, RESULT).request().get(String.class);

        Logger.getLogger(getClass().getName()).fine(entity);

        return response;
    }
}
