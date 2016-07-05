package org.netbeans.modules.bamboo.model;

import org.netbeans.modules.bamboo.model.AllPlansResponse;
import org.netbeans.modules.bamboo.model.AllResultsResponse;
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
