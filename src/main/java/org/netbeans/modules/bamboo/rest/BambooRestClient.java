package org.netbeans.modules.bamboo.rest;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;


public class BambooRestClient {
    private static final String AUTHEN =
        "?os_authType=basic&os_username={username}&os_password={password}";
    private static final String REST_API = "/rest/api/latest";
    private static final String ALL_PLANS = REST_API + "/plan";
    private final String PLAN = ALL_PLANS + "/{buildKey}.json";
    private final String RESULT = REST_API + "/result/{buildKey}.json";

    private String serverUrl;

    public BambooRestClient(final String serverUrl) {
        this.serverUrl = serverUrl;
    }

    private WebTarget createTarget(final String path) {
        Client client = ClientBuilder.newClient();

        return client.target(path);
    }

    public Plans getPlans() {
        Plans plans = new Plans();

        return plans;
    }

    public ResultsResponse getResultsResponse() {
        ResultsResponse response = new ResultsResponse();

        String entity = createTarget(RESULT).request().get(String.class);

        return response;
    }
}
