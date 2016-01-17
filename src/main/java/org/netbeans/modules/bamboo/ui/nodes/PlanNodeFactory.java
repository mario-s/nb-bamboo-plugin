package org.netbeans.modules.bamboo.ui.nodes;

import java.util.ArrayList;
import java.util.List;
import org.netbeans.modules.bamboo.glue.BambooInstance;
import org.netbeans.modules.bamboo.rest.Plan;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;

/**
 *
 * @author spindizzy
 */
class PlanNodeFactory extends ChildFactory<Plan> {
    private BambooInstance instance;
    
    private List<Plan> plans;

    PlanNodeFactory(BambooInstance instance) {
        this.instance = instance;
        callServer();
    }
    
    private void callServer() {
        //TODO use rest client
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
