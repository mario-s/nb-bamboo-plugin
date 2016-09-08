package org.netbeans.modules.bamboo.rest;

import org.netbeans.modules.bamboo.model.rest.Project;
import org.netbeans.modules.bamboo.glue.InstanceValues;
import org.netbeans.modules.bamboo.glue.VersionInfo;

import java.util.Collection;


/**
 * This interface defines methods to access the Bamboo server.
 *
 * @author spindizzy
 */
public interface BambooServiceAccessable {
    
    /**
     * Return a collection of available {@link BuildProject}.
     * @param values the necessary values to access the rest service.
     * @return the projects.
     */
    Collection<Project> getProjects(InstanceValues values);

    VersionInfo getVersionInfo(InstanceValues values);
   
}
