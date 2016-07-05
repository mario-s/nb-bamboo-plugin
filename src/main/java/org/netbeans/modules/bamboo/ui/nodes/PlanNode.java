package org.netbeans.modules.bamboo.ui.nodes;

import org.netbeans.modules.bamboo.model.Plan;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;

/**
 *
 * @author spindizzy
 */
public class PlanNode extends AbstractNode{
    
    private final Plan plan;
    
    public PlanNode(Plan plan) {
        super(Children.LEAF);
        this.plan = plan;
        
        init();
    }
    
     private void init() {
        setName(plan.getName());
        setDisplayName(plan.getShortName());
        setShortDescription(plan.getName());
    }
    
}
