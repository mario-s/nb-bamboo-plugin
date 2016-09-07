package org.netbeans.modules.bamboo.glue;

import org.netbeans.modules.bamboo.model.Project;
import java.util.Collection;


/**
 * @author spindizzy
 */
public interface ProjectsProvideable extends BambooInstance {
    String PROJECTS = "projects";

    Collection<Project> getProjects();

    void setProjects(Collection<Project> results);
}
