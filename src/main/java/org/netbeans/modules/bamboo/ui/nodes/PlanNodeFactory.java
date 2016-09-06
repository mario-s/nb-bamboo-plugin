package org.netbeans.modules.bamboo.ui.nodes;

import org.netbeans.modules.bamboo.model.BuildProject;

import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import org.netbeans.modules.bamboo.model.Plan;
import static java.util.Collections.sort;


/**
 * @author spindizzy
 */
class PlanNodeFactory extends ChildFactory<Plan> {
    private static final PlanComparator COMPARATOR = new PlanComparator();

    private final BuildProject project;

    private Collection<Plan> plans;

    PlanNodeFactory(final BuildProject project) {
        this.project = project;
        refreshNodes();
    }

    final void refreshNodes() {
        plans = project.getPlans();
        refresh(false);
    }

    @Override
    protected Node createNodeForKey(final Plan key) {
        return new PlanNode(key);
    }

    @Override
    protected boolean createKeys(final List<Plan> toPopulate) {
        if (plans != null) {
            toPopulate.addAll(plans);
        }

        sort(toPopulate, COMPARATOR);

        return true;
    }

    private static class PlanComparator implements Comparator<Plan> {
        @Override
        public int compare(final Plan o1, final Plan o2) {
            final String left = o1.getName();
            final String right = o2.getName();

            return left.compareToIgnoreCase(right);
        }
    }
}
