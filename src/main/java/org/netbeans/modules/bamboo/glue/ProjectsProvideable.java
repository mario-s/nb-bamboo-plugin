package org.netbeans.modules.bamboo.glue;

import java.util.Collection;


/**
 * @author spindizzy
 */
public interface ProjectsProvideable extends BambooInstance {
    String PROJECTS = "projects";

    Collection<BuildProject> getProjects();

    void setProjects(Collection<BuildProject> results);
}
