package org.netbeans.modules.bamboo.ui.nodes;

import java.util.ArrayList;
import java.util.List;
import org.netbeans.modules.bamboo.glue.BambooInstance;
import org.netbeans.modules.bamboo.rest.BambooInstanceAccessable;
import org.netbeans.modules.bamboo.rest.Plan;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.openide.util.Lookup;

/**
 *
 * @author spindizzy
 */
class PlanNodeFactory extends ChildFactory<Plan> {
    private final BambooInstance instance;
    
    private final BambooInstanceAccessable instanceAccessor;
    
    private List<Plan> plans;

    PlanNodeFactory(BambooInstance instance) {
        this.instance = instance;
        this.instanceAccessor = Lookup.getDefault().lookup(BambooInstanceAccessable.class);
        callServer();
    }
    
    private void callServer() {
        //TODO use instanceAccessor
        Plan plan = new Plan(){
            @Override
            public String getShortName() {
                return "test";
            }

            @Override
            public String getName() {
                return "Test Plan";
            }
        };
        plans = new ArrayList();
        plans.add(plan);
        refresh(true);
    }

    @Override
    protected Node createNodeForKey(final Plan key) {
        return new PlanNode(key);
    }

    @Override
    protected boolean createKeys(final List<Plan> toPopulate) {
        toPopulate.addAll(plans);
        return true;
    }

}
