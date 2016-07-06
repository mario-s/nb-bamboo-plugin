package org.netbeans.modules.bamboo.ui.nodes;

import org.netbeans.api.annotations.common.StaticResource;
import org.netbeans.modules.bamboo.rest.model.Plan;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;

/**
 *
 * @author spindizzy
 */
public class PlanNode extends AbstractNode{
    @StaticResource
    private static final String ICON_BASE = "org/netbeans/modules/bamboo/resources/grey.png";
    
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
        setIconBaseWithExtension(ICON_BASE);
    }
    
}
