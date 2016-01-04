package org.netbeans.modules.bamboo.rest;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;


public class BambooRestClient {
    private static final String AUTHEN =
        "?os_authType=basic&os_username={username}&os_password={password}";
    private static final String REST_API = "/rest/api/latest";
    private static final String ALL_PLANS = REST_API + "/plan";
    private final String PLAN = ALL_PLANS + "/{buildKey}.json";
    private final String RESULT = "/rest/api/latest/result/{buildKey}.json";

    private String serverUrl;

    public BambooRestClient(final String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public ResultsResponse getResultsResponse() {
        ResultsResponse response = new ResultsResponse();
        Client client = ClientBuilder.newClient();
        String entity = client.target(serverUrl).path("").request().get(String.class);

        return response;
    }
}
