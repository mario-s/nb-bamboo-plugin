package org.netbeans.modules.bamboo.rest;


import java.util.logging.Logger;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import org.netbeans.modules.bamboo.glue.InstanceValues;


public class BambooRestClient implements BambooInstanceAccessable{
    private static final String AUTHEN =
        "?os_authType=basic&os_username={username}&os_password={password}";
    private static final String REST_API = "/rest/api/latest";
    private static final String ALL_PLANS = REST_API + "/plan";
    private final String PLAN = ALL_PLANS + "/{buildKey}.json";
    private final String RESULT = REST_API + "/result/{buildKey}.json";

    private WebTarget target(String serverUrl) {
        return ClientBuilder.newClient().target(serverUrl);
    }

    @Override
    public Plans getPlans(InstanceValues values) {
        Plans plans = new Plans();

        String entity = target(values.getUrl()).path(ALL_PLANS).request().get(String.class);

        Logger.getLogger(getClass().getName()).fine(entity);

        return plans;
    }

    @Override
    public ResultsResponse getResultsResponse(InstanceValues values) {
        ResultsResponse response = new ResultsResponse();

        String entity = target(values.getUrl()).path(RESULT).request().get(String.class);

        Logger.getLogger(getClass().getName()).fine(entity);

        return response;
    }
}
