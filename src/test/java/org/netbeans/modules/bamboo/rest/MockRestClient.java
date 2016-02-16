package org.netbeans.modules.bamboo.rest;

import java.util.ArrayList;
import org.netbeans.modules.bamboo.glue.InstanceValues;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author spindizzy
 */
@ServiceProvider(service = BambooInstanceAccessable.class)
public class MockRestClient implements BambooInstanceAccessable{

    @Override
    public Plans getPlans(InstanceValues values) {
        Plans plans = new Plans();
        plans.setPlans(new ArrayList<>());
        plans.getPlans().add(new Plan());
        return plans;
    }

    @Override
    public ResultsResponse getResultsResponse(InstanceValues values) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
}
