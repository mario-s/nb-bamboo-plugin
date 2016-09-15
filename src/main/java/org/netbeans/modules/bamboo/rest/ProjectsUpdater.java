
package org.netbeans.modules.bamboo.rest;

import java.util.Collection;
import org.netbeans.modules.bamboo.model.ProjectVo;

/**
 * Updates a collection of project.
 * 
 * @author spindizzy
 */
final class ProjectsUpdater {
    
    ProjectsUpdater(){
    }
    
    /**
     * This method updates the given target with the values from the source.
     * @param source source for updates
     * @param target updates to be applied
     */
    void update(Collection<ProjectVo> source, Collection<ProjectVo> target){
        
        //if nothing has changed we can skip here
        //a change will cause the complete tree to be rebuild
        if(target.retainAll(source)){
            
        }
    }
}
