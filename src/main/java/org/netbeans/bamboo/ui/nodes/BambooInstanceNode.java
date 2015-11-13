package org.netbeans.bamboo.ui.nodes;

import javax.swing.Action;
import org.netbeans.bamboo.BambooInstance;
import org.netbeans.bamboo.ui.actions.RemoveInstanceAction;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;

public class BambooInstanceNode extends AbstractNode {

    public BambooInstanceNode(final BambooInstance instance) {
        super(Children.LEAF);

        setName(instance.getUrl());
        setDisplayName(instance.getName());
        setShortDescription(instance.getUrl());
    }

    @Override
    public Action[] getActions(boolean context) {
        return new Action[]{new RemoveInstanceAction(this)};
    }
}
