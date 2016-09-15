package org.netbeans.modules.bamboo.rest;

import java.util.Collection;
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
            }
        });

    }
}
