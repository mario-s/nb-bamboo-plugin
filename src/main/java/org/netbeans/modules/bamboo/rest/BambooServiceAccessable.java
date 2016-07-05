package org.netbeans.modules.bamboo.rest;

import org.netbeans.modules.bamboo.model.AllPlansResponse;
import org.netbeans.modules.bamboo.model.AllResultsResponse;
import org.netbeans.modules.bamboo.InstanceValues;

/**
 * This interface defines methods to access the Bamboo server.
 * 
 * @author spindizzy
 */
public interface BambooServiceAccessable {
    
    AllPlansResponse getAllPlans(InstanceValues values);
    
    AllResultsResponse getResultsResponse(InstanceValues values);
}
