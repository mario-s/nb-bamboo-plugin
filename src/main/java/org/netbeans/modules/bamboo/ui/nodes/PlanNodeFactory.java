package org.netbeans.modules.bamboo.ui.nodes;


import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import static java.util.Collections.sort;
import org.netbeans.modules.bamboo.model.PlanVo;
import org.netbeans.modules.bamboo.model.ProjectVo;


/**
 * @author spindizzy
 */
class PlanNodeFactory extends ChildFactory<PlanVo> {
    private static final PlanComparator COMPARATOR = new PlanComparator();

    private final ProjectVo project;

    private Collection<PlanVo> plans;

    PlanNodeFactory(final ProjectVo project) {
        this.project = project;
        refreshNodes();
    }

    final void refreshNodes() {
        plans = project.getPlans();
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

    private static class PlanComparator implements Comparator<PlanVo> {
        @Override
        public int compare(final PlanVo o1, final PlanVo o2) {
            final String left = o1.getName();
            final String right = o2.getName();

            return left.compareToIgnoreCase(right);
        }
    }
}
