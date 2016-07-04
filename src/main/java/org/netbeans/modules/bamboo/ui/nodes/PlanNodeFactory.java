package org.netbeans.modules.bamboo.ui.nodes;

import org.netbeans.modules.bamboo.glue.BambooInstance;
import org.netbeans.modules.bamboo.rest.AllPlansResponse;
import org.netbeans.modules.bamboo.rest.BambooInstanceAccessable;
import org.netbeans.modules.bamboo.rest.Plan;
import org.netbeans.modules.bamboo.rest.Plans;

import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;

import org.openide.util.Lookup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * @author spindizzy
 */
class PlanNodeFactory extends ChildFactory<Plan> {
    private final BambooInstance instance;

    private BambooInstanceAccessable instanceAccessor;

    private List<Plan> plans;

    PlanNodeFactory(final BambooInstance instance) {
        this.instance = instance;
        initClient();
        callServer();
    }

    private void initClient() {
        Collection<? extends BambooInstanceAccessable> services = Lookup.getDefault().lookupAll(
                BambooInstanceAccessable.class);

        // simply take the first one, in test environment it is the mock client
        this.instanceAccessor = services.iterator().next();
    }

    private void callServer() {
        AllPlansResponse all = instanceAccessor.getAllPlans(instance);
        plans = all.getPlans().getPlan();
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
