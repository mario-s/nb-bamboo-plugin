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
package org.netbeans.modules.bamboo.ui.nodes;

import java.io.Serializable;
import org.openide.nodes.Node;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import org.netbeans.modules.bamboo.model.rcp.PlanVo;
import org.netbeans.modules.bamboo.model.rcp.ProjectVo;

import lombok.extern.java.Log;

import static java.util.Collections.sort;

/**
 * This factory creates a new {@link PlanNode}.
 *
 * @author Mario Schroeder
 */
@Log
class PlanNodeFactory extends AbstractRefreshChildFactory<PlanVo> {

    private static final PlanComparator COMPARATOR = new PlanComparator();

    private final ProjectVo project;

    private Collection<PlanVo> plans;

    PlanNodeFactory(final ProjectVo project) {
        this.project = project;
        init();
    }

    private void init() {
        refreshNodes();
    }

    @Override
    void refreshNodes() {
        log.info(String.format("refreshing plans of %s", project.getName()));
        plans = project.getChildren();
        refresh(false);
    }

    @Override
    protected Node createNodeForKey(final PlanVo key) {
        return new PlanNode(key);
    }

    @Override
    protected boolean createKeys(final List<PlanVo> toPopulate) {
        if (plans != null) {
            toPopulate.addAll(plans);
        }

        sort(toPopulate, COMPARATOR);

        return true;
    }

    private static class PlanComparator implements Comparator<PlanVo>, Serializable {

        private static final long serialVersionUID = 1L;

        @Override
        public int compare(final PlanVo o1, final PlanVo o2) {
            if (o1.isNotify() != o2.isNotify()) {
                return o1.isNotify() ? -1 : 1;
            } else {
                final String leftName = o1.getName();
                final String rightName = o2.getName();

                return leftName.compareToIgnoreCase(rightName);
            }
        }
    }
}
