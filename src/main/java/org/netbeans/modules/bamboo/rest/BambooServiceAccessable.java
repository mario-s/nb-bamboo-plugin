package org.netbeans.modules.bamboo.rest;

import org.netbeans.modules.bamboo.glue.BuildProject;
import org.netbeans.modules.bamboo.glue.InstanceValues;
import org.netbeans.modules.bamboo.rest.model.PlansResponse;
import org.netbeans.modules.bamboo.rest.model.ResultsResponse;

import java.util.Collection;


/**
 * This interface defines methods to access the Bamboo server.
 *
 * @author spindizzy
 */
public interface BambooServiceAccessable {
    @Deprecated
    PlansResponse getAllPlans(InstanceValues values);

    @Deprecated
    ResultsResponse getResultsResponse(InstanceValues values);

    Collection<BuildProject> getProjects(InstanceValues values);
}
