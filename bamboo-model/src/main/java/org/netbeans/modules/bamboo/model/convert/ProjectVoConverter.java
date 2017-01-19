package org.netbeans.modules.bamboo.model.convert;

import org.netbeans.modules.bamboo.model.rcp.ProjectVo;
import org.netbeans.modules.bamboo.model.rest.Project;

/**
 *
 * @author Mario Schroeder
 */
public class ProjectVoConverter extends AbstractVoConverter<Project, ProjectVo> {
    
    private final String serverUrl;

    public ProjectVoConverter(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    @Override
    public ProjectVo convert(Project src) {
        ProjectVo target = new ProjectVo(src.getKey());
        
        copyProperties(src, target);
        
        target.setServerUrl(serverUrl);
        return target;
    }
    
}
