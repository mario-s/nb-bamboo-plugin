package org.netbeans.modules.bamboo.rest;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;


public class BambooRestClient {
    private final String BAMBOO_RESULT_URL =
        "{serverUrl}/rest/api/latest/result/{buildKey}.json?os_authType=basic&os_username={username}&os_password={password}";
    private final String BAMBOO_PLAN_URL =
        "{serverUrl}/rest/api/latest/plan/{buildKey}.json?os_authType=basic&os_username={username}&os_password={password}";

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
