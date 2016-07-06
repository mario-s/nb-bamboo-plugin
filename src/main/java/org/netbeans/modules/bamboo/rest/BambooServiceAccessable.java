package org.netbeans.modules.bamboo.rest;

import org.netbeans.modules.bamboo.rest.AllPlansResponse;
import org.netbeans.modules.bamboo.rest.AllResultsResponse;
import org.netbeans.modules.bamboo.glue.InstanceValues;
import org.netbeans.modules.bamboo.glue.InstanceValues;

/**
 * This interface defines methods to access the Bamboo server.
 * 
 * @author spindizzy
 */
public interface BambooServiceAccessable {
    
    AllPlansResponse getAllPlans(InstanceValues values);
    
    AllResultsResponse getResultsResponse(InstanceValues values);
}
