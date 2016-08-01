package org.netbeans.modules.bamboo.ui.nodes;

import java.awt.Image;
import org.netbeans.api.annotations.common.StaticResource;
import org.netbeans.modules.bamboo.rest.model.Plan;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.ImageUtilities;

/**
 *
 * @author spindizzy
 */
public class ProjectNode extends AbstractNode{
    @StaticResource
    private static final String ICON_BASE = "org/netbeans/modules/bamboo/resources/grey.png";
    @StaticResource
    private static final String ICON_ENABLED = "org/netbeans/modules/bamboo/resources/blue.png";
    
    private final Plan plan;
    
    public ProjectNode(Plan plan) {
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

    @Override
    public Image getIcon(int type) {
        
        Image icon = super.getIcon(type);
        
        if(plan.isEnabled()){
            icon = ImageUtilities.loadImage(ICON_ENABLED);
        }
        
        return icon; 
    }
     
     
    
}
