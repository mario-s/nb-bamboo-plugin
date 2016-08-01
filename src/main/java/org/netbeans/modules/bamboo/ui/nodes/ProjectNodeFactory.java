package org.netbeans.modules.bamboo.ui.nodes;

import org.netbeans.modules.bamboo.rest.model.Plan;

import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;

import java.util.List;
import org.netbeans.modules.bamboo.glue.PlansProvideable;


/**
 * @author spindizzy
 */
class ProjectNodeFactory extends ChildFactory<Plan> {
    private final PlansProvideable instance;

    private List<Plan> plans;

    ProjectNodeFactory(final PlansProvideable instance) {
        this.instance = instance;
        refreshNodes();
    }

    private void refreshNodes() {
        plans = instance.getPlans();
        refresh(true);
    }

    @Override
    protected Node createNodeForKey(final Plan key) {
        return new ProjectNode(key);
    }

    @Override
    protected boolean createKeys(final List<Plan> toPopulate) {
        if (plans != null) {
            toPopulate.addAll(plans);
        }

        return true;
    }
}
