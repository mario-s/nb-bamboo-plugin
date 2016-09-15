package org.netbeans.modules.bamboo.rest;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.netbeans.modules.bamboo.model.PlanVo;
import org.netbeans.modules.bamboo.model.ProjectVo;

/**
 * Updates a collection of project.
 *
 * @author spindizzy
 */
final class ProjectsUpdater {

    ProjectsUpdater() {
    }

    

    /**
     * This method updates the given target with the values from the source.
     *
     * @param source source for updates
     * @param target updates to be applied
     */
    void update(Collection<ProjectVo> source, Collection<ProjectVo> target) {

        //a change will cause the complete tree to be rebuild
        target.retainAll(source);

        source.forEach(srcProjVo -> {
            //add it to the target if not present, will change the whole tree
            if (!target.contains(srcProjVo)) {
                target.add(srcProjVo);
            }else{
                Optional<ProjectVo> optProj = find(target, srcProjVo);
                if(optProj.isPresent()) {
                    //copy plans
                    ProjectVo tarProjVo = optProj.get();
                    updatePlans(srcProjVo.getPlans(), tarProjVo.getPlans());
                }
            }
        });

    }
    
    private Optional<ProjectVo> find(Collection<ProjectVo> target, ProjectVo project) {
        return target.stream().filter(vo -> vo.getKey().equals(project.getKey())).findFirst();
    }
    
    private void updatePlans(List<PlanVo> source, List<PlanVo> target) {
        //TODO implement
    }
}
