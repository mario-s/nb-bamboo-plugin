package org.netbeans.modules.bamboo.rest;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.netbeans.modules.bamboo.model.AbstractVo;
import org.netbeans.modules.bamboo.model.PlanVo;
import org.netbeans.modules.bamboo.model.ProjectVo;

/**
 * This class updates all the ValueObjects (vo).
 * @author sindizzy
 */
abstract class AbstractVoUpdater<T extends AbstractVo> {

    /**
     * This method updates the given target with the values from the source.
     *
     * @param source source for updates
     * @param target updates to be applied
     */
    void update(Collection<T> source, Collection<T> target) {
        target.retainAll(source);

        source.forEach(srcVo -> {
            //add it to the target if not present, might change the whole tree
            if (!target.contains(srcVo)) {
                target.add(srcVo);
            } else {
                Optional<T> opt = find(target, srcVo.getKey());
                if (opt.isPresent()) {
                    doUpdate(srcVo, opt.get());
                }
            }
        });

    }
    
    private Optional<T> find(Collection<T> target, String key) {
        return target.parallelStream().filter(vo -> vo.getKey().equals(key)).findFirst();
    }

    abstract void doUpdate(T source, T target);

    

    static class ProjectsUpdater extends AbstractVoUpdater<ProjectVo> {

        @Override
        protected void doUpdate(ProjectVo source, ProjectVo target) {
            
            target.setName(source.getName());
            updatePlans(source.getPlans(), target.getPlans());
        }


        private void updatePlans(List<PlanVo> source, List<PlanVo> target) {
            PlansUpdater updater = new PlansUpdater();
            updater.update(source, target);
        }
    }

    static class PlansUpdater extends AbstractVoUpdater<PlanVo> {

        @Override
        void doUpdate(PlanVo source, PlanVo target) {
            target.setName(source.getName());
            target.setShortName(source.getShortName());
            target.setResult(source.getResult());
        }

    }
}
