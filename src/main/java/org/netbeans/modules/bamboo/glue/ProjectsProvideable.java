package org.netbeans.modules.bamboo.glue;

import java.util.Collection;
import org.netbeans.modules.bamboo.model.ProjectVo;


/**
 * @author spindizzy
 */
public interface ProjectsProvideable extends BambooInstance {
    String PROJECTS = "projects";

    Collection<ProjectVo> getProjects();

    void setProjects(Collection<ProjectVo> results);
}
