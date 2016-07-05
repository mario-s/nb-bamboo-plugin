package org.netbeans.modules.bamboo.ui.nodes;

import org.netbeans.modules.bamboo.glue.BambooInstance;
import org.netbeans.modules.bamboo.model.AllPlansResponse;
import org.netbeans.modules.bamboo.model.Plan;

import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;

import org.openide.util.Lookup;

import java.util.Collection;
import java.util.List;
import org.netbeans.modules.bamboo.model.PlansProvideable;
import org.netbeans.modules.bamboo.rest.BambooServiceAccessable;


/**
 * @author spindizzy
 */
class PlanNodeFactory extends ChildFactory<Plan> {
    private final PlansProvideable instance;

    private BambooServiceAccessable instanceAccessor;

    private List<Plan> plans;

    PlanNodeFactory(final PlansProvideable instance) {
        this.instance = instance;
//        initClient();
        callServer();
    }

    private void initClient() {
        Collection<? extends BambooServiceAccessable> services = Lookup.getDefault().lookupAll(BambooServiceAccessable.class);

        // simply take the first one, in test environment it is the mock client
        this.instanceAccessor = services.iterator().next();
    }

    private void callServer() {
//        AllPlansResponse all = instanceAccessor.getAllPlans(instance);
//        plans = all.getPlans().getPlan();
        plans = instance.getPlans();
        refresh(true);
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

        return true;
    }
}
