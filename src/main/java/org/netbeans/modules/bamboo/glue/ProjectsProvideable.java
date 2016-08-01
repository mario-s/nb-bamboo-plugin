package org.netbeans.modules.bamboo.glue;

import java.util.List;


/**
 * @author spindizzy
 */
public interface ProjectsProvideable extends PlansProvideable {
    List<BuildProject> getProjects();

    void setProjects(List<BuildProject> results);
}
