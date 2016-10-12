package org.netbeans.modules.bamboo.glue;

import org.netbeans.modules.bamboo.model.InstanceValues;
import org.netbeans.modules.bamboo.model.VersionInfo;
import java.util.Collection;
import org.netbeans.modules.bamboo.model.ProjectVo;

/**
 * This interface defines methods to access the Bamboo server.
 *
 * @author spindizzy
 */
public interface BambooServiceAccessable {

    /**
     * This method returns <code>true</code> when the url can be reached, if not it returns <code>false</code>.
     *
     * @param values the necessary values to access the rest service.
     * @return <code>true</code> when server is present, otherwhise <code>false</code>
     *
     *
     */
    @Deprecated
    boolean existsService(InstanceValues values);
    
    /**
     * This method returns <code>true</code> when the url can be reached, if not it returns <code>false</code>.
     *
     * @return <code>true</code> when server is present, otherwhise <code>false</code>
     *
     *
     */
    boolean existsService();

    /**
     * Return a collection of available {@link BuildProject}.
     *
     * @param values the necessary values to access the rest service.
     * @return the projects.
     */
    @Deprecated
    Collection<ProjectVo> getProjects(InstanceValues values);
    
    /**
     * Return a collection of available {@link BuildProject}.
     *
     * @return the projects.
     */
    Collection<ProjectVo> getProjects();

    /**
     * This method updated the given projects.It will change the content of the given projects parameter.
     *
     * @param projects projects to be updated
     * @param values the necessary values to access the rest service.
     */
    @Deprecated
    void updateProjects(Collection<ProjectVo> projects, InstanceValues values);
    
     /**
     * This method updated the given projects.It will change the content of the given projects parameter.
     *
     * @param projects projects to be updated
     */
    void updateProjects(Collection<ProjectVo> projects);

    /**
     * This method returns inforamtion about the Bamboo server.
     *
     * @param values the necessary values to access the rest service.
     * @return information about the version.
     */
    @Deprecated
    VersionInfo getVersionInfo(InstanceValues values);

    /**
     * This method returns information about the Bamboo server.
     *
     * @return information about the version.
     */
    VersionInfo getVersionInfo();
}
