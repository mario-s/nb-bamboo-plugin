package org.netbeans.modules.bamboo.rest;

import org.netbeans.modules.bamboo.glue.InstanceValues;
import org.netbeans.modules.bamboo.glue.VersionInfo;

import java.util.Collection;
import org.netbeans.modules.bamboo.model.ProjectVo;


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
    Collection<ProjectVo> getProjects(InstanceValues values);

    VersionInfo getVersionInfo(InstanceValues values);
   
}
