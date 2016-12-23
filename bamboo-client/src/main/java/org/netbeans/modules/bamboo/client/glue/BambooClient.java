package org.netbeans.modules.bamboo.client.glue;

import org.netbeans.modules.bamboo.model.rcp.VersionInfo;
import java.util.Collection;
import org.netbeans.modules.bamboo.model.rcp.ProjectVo;

/**
 * This interface defines methods to access the Bamboo server.
 *
 * @author spindizzy
 */
public interface BambooClient {

    /**
     * Return a collection of available {@link ProjectVo}.
     *
     * @return the projects.
     */
    Collection<ProjectVo> getProjects();

    /**
     * This method returns information about the Bamboo server.
     *
     * @return information about the version.
     */
    VersionInfo getVersionInfo();

}
