package org.netbeans.modules.bamboo.rest;

import org.netbeans.modules.bamboo.model.BambooInstance;

import java.util.logging.Logger;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;


public class BambooRestClient {
    private static final String AUTHEN =
        "?os_authType=basic&os_username={username}&os_password={password}";
    private static final String REST_API = "/rest/api/latest";
    private static final String ALL_PLANS = REST_API + "/plan";
    private final String PLAN = ALL_PLANS + "/{buildKey}.json";
    private final String RESULT = REST_API + "/result/{buildKey}.json";

    private final String serverUrl;

    public BambooRestClient(final BambooInstance instance) {
        this.serverUrl = instance.getUrl();
    }

    private WebTarget target() {
        return ClientBuilder.newClient().target(serverUrl);
    }

    public Plans getPlans() {
        Plans plans = new Plans();

        String entity = target().path(ALL_PLANS).request().get(String.class);

        Logger.getLogger(getClass().getName()).fine(entity);

        return plans;
    }

    public ResultsResponse getResultsResponse() {
        ResultsResponse response = new ResultsResponse();

        String entity = target().path(RESULT).request().get(String.class);

        Logger.getLogger(getClass().getName()).fine(entity);

        return response;
    }
}
