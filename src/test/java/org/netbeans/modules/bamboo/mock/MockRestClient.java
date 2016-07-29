package org.netbeans.modules.bamboo.mock;

import org.netbeans.modules.bamboo.rest.model.Result;
import org.netbeans.modules.bamboo.rest.model.Plans;
import org.netbeans.modules.bamboo.rest.model.ResultsResponse;
import org.netbeans.modules.bamboo.rest.model.Results;
import org.netbeans.modules.bamboo.rest.model.Plan;
import org.netbeans.modules.bamboo.rest.model.PlansResponse;
import org.netbeans.modules.bamboo.glue.InstanceValues;

import org.openide.util.lookup.ServiceProvider;

import java.util.ArrayList;
import org.netbeans.modules.bamboo.rest.BambooServiceAccessable;


/**
 * @author spindizzy
 */
@ServiceProvider(service = BambooServiceAccessable.class, position = 10)
public class MockRestClient implements BambooServiceAccessable {
    @Override
    public PlansResponse getAllPlans(final InstanceValues values) {
        PlansResponse all = new PlansResponse();
        Plans plans = new Plans();
        plans.setPlan(new ArrayList<>());
        plans.getPlan().add(new Plan());
        all.setPlans(plans);

        return all;
    }

    @Override
    public ResultsResponse getResultsResponse(final InstanceValues values) {
        Results.Builder resBuilder = new Results.Builder();
        Results results = resBuilder.addResult(new Result()).build();
        ResultsResponse.Builder respBuilder = new ResultsResponse.Builder();

        return respBuilder.results(results).build();
    }
}
