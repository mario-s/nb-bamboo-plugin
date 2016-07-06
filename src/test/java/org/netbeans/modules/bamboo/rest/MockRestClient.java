package org.netbeans.modules.bamboo.rest;

import org.netbeans.modules.bamboo.rest.BambooServiceAccessable;
import org.netbeans.modules.bamboo.rest.Result;
import org.netbeans.modules.bamboo.rest.Plans;
import org.netbeans.modules.bamboo.rest.AllResultsResponse;
import org.netbeans.modules.bamboo.rest.Results;
import org.netbeans.modules.bamboo.rest.Plan;
import org.netbeans.modules.bamboo.rest.AllPlansResponse;
import org.netbeans.modules.bamboo.glue.InstanceValues;

import org.openide.util.lookup.ServiceProvider;

import java.util.ArrayList;


/**
 * @author spindizzy
 */
@ServiceProvider(service = BambooServiceAccessable.class)
public class MockRestClient implements BambooServiceAccessable {
    @Override
    public AllPlansResponse getAllPlans(final InstanceValues values) {
        AllPlansResponse all = new AllPlansResponse();
        Plans plans = new Plans();
        plans.setPlan(new ArrayList<>());
        plans.getPlan().add(new Plan());
        all.setPlans(plans);

        return all;
    }

    @Override
    public AllResultsResponse getResultsResponse(final InstanceValues values) {
        Results.Builder resBuilder = new Results.Builder();
        Results results = resBuilder.addResult(new Result()).build();
        AllResultsResponse.Builder respBuilder = new AllResultsResponse.Builder();

        return respBuilder.results(results).build();
    }
}
