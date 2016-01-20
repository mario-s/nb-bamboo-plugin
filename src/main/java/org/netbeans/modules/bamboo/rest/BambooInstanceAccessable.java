package org.netbeans.modules.bamboo.rest;

import org.netbeans.modules.bamboo.glue.InstanceValues;
import org.netbeans.modules.bamboo.rest.Plans;
import org.netbeans.modules.bamboo.rest.ResultsResponse;

/**
 * This interface defines methods to access the Bamboo server.
 * 
 * @author spindizzy
 */
public interface BambooInstanceAccessable {
    
    Plans getPlans(InstanceValues values);
    
    ResultsResponse getResultsResponse(InstanceValues values);
}
