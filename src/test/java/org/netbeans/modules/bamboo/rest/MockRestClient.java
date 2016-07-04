package org.netbeans.modules.bamboo.rest;

import org.netbeans.modules.bamboo.glue.InstanceValues;

import org.openide.util.lookup.ServiceProvider;

import java.util.ArrayList;


/**
 * @author spindizzy
 */
@ServiceProvider(service = BambooInstanceAccessable.class)
public class MockRestClient implements BambooInstanceAccessable {
    @Override
    public Plans getPlans(final InstanceValues values) {
        Plans plans = new Plans();
        plans.setPlans(new ArrayList<>());
        plans.getPlans().add(new Plan());

        return plans;
    }

    @Override
    public ResultsResponse getResultsResponse(final InstanceValues values) {
        Results.Builder resBuilder = new Results.Builder();
        Results results = resBuilder.addResult(new Result()).build();
        ResultsResponse.Builder respBuilder = new ResultsResponse.Builder();

        return respBuilder.results(results).build();
    }
}
