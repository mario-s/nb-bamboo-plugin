package org.netbeans.modules.bamboo.glue;


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
    
    /**
     * This method updated the given projects.It will change the content of the given projects parameter.
     * @param projects projects to be updated
     * @param values the necessary values to access the rest service.
     */
    void updateProjects(Collection<ProjectVo> projects, InstanceValues values);

    /**
     * This method returns inforamtion about the Bamboo server.
     * @param values the necessary values to access the rest service.
     * @return information about the version.
     */
    VersionInfo getVersionInfo(InstanceValues values);
   
}
