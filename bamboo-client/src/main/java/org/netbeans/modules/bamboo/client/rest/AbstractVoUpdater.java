/* 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.netbeans.modules.bamboo.client.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import org.netbeans.modules.bamboo.model.rcp.AbstractVo;
import org.netbeans.modules.bamboo.model.rcp.PlanVo;
import org.netbeans.modules.bamboo.model.rcp.ProjectVo;

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
            
            Collection<PlanVo> sourceChildren = new ArrayList<>();
            sourceChildren.addAll(source.getChildren());
            Collection<PlanVo> targetChildren = new ArrayList<>();
            targetChildren.addAll(target.getChildren());
                    
            updatePlans(sourceChildren, targetChildren);
            
            target.setChildren(targetChildren);
        }


        private void updatePlans(Collection<PlanVo> source, Collection<PlanVo> target) {
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
            target.setEnabled(source.isEnabled());
        }

    }
}
