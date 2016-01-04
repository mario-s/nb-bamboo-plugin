package org.netbeans.modules.bamboo.rest;

public class BambooRestClient {
    private String serverUrl;

    public BambooRestClient(final String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public ResultsResponse getResultsResponse() {
        ResultsResponse response = new ResultsResponse();

        return response;
    }
}
