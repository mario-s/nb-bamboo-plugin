package org.netbeans.modules.bamboo.glue;

import java.util.Collection;
import org.netbeans.modules.bamboo.model.ProjectVo;


/**
 * @author spindizzy
 */
public interface ProjectsProvideable extends BambooInstance {

    Collection<ProjectVo> getProjects();

    void setProjects(Collection<ProjectVo> results);
}
