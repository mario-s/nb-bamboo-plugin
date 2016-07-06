package org.netbeans.modules.bamboo.rest;

import org.netbeans.modules.bamboo.rest.model.PlansResponse;
import org.netbeans.modules.bamboo.rest.model.ResultsResponse;
import org.netbeans.modules.bamboo.glue.InstanceValues;

/**
 * This interface defines methods to access the Bamboo server.
 * 
 * @author spindizzy
 */
public interface BambooServiceAccessable {
    
    PlansResponse getAllPlans(InstanceValues values);
    
    ResultsResponse getResultsResponse(InstanceValues values);
}
