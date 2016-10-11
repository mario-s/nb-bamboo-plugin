package org.netbeans.modules.bamboo.ui.nodes;

import org.netbeans.api.annotations.common.StaticResource;
import org.netbeans.api.core.ide.ServicesTabNodeRegistration;

import org.netbeans.modules.bamboo.glue.InstanceManageable;
import org.netbeans.modules.bamboo.ui.actions.AddInstanceAction;

import static org.netbeans.modules.bamboo.ui.nodes.Bundle.*;

import org.openide.nodes.AbstractNode;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;

import static org.openide.util.Lookup.getDefault;

import org.openide.util.NbBundle.Messages;

import javax.swing.Action;
import org.netbeans.modules.bamboo.model.BambooInstance;

/**
 * Root node for the Bamboo Builder
 * @author spindizzy
 */
@ServicesTabNodeRegistration(
    name = BambooRootNode.BAMBOO_NODE_NAME, displayName = "#LBL_BambooNode",
    shortDescription = "#TIP_BambooNode", iconResource = BambooRootNode.ICON_BASE, position = 450
)
@Messages(
    { "LBL_BambooNode=Bamboo Builders", "TIP_BambooNode=Bamboo continuous integration servers." }
)
public final class BambooRootNode extends AbstractNode {
    static final String BAMBOO_NODE_NAME = "bamboo";

    @StaticResource
    static final String ICON_BASE = "org/netbeans/modules/bamboo/resources/ci.png";
    
    private final ChildFactory<BambooInstance> instanceNodeFactory;

    BambooRootNode() {
        super(Children.LEAF, getDefault().lookup(InstanceManageable.class).getLookup());
        instanceNodeFactory = new BambooInstanceNodeFactory(getLookup());
        init();
    }

    private void init() {
        setName(BAMBOO_NODE_NAME);
        setDisplayName(LBL_BambooNode());
        setShortDescription(TIP_BambooNode());
        setIconBaseWithExtension(ICON_BASE);

        Children children = Children.create(instanceNodeFactory, false);
        setChildren(children);
    }

    @Override
    public Action[] getActions(final boolean context) {
        return new Action[] { new AddInstanceAction() };
    }
}
