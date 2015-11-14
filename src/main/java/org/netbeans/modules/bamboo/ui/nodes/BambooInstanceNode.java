package org.netbeans.modules.bamboo.ui.nodes;

import javax.swing.Action;
import org.netbeans.api.annotations.common.StaticResource;
import org.netbeans.modules.bamboo.model.BambooInstance;
import org.netbeans.modules.bamboo.ui.actions.RemoveInstanceAction;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;

public class BambooInstanceNode extends AbstractNode {
    
    @StaticResource
    private static final String ICON_BASE = "org/netbeans/modules/bamboo/resources/instance.png";
    
    private BambooInstance instance;

    public BambooInstanceNode(final BambooInstance instance) {
        super(Children.LEAF);
        this.instance = instance;

        setName(instance.getUrl());
        setDisplayName(instance.getName());
        setShortDescription(instance.getUrl());
        setIconBaseWithExtension(ICON_BASE);
    }

    @Override
    public Action[] getActions(boolean context) {
        return new Action[]{new RemoveInstanceAction(instance)};
    }
}
