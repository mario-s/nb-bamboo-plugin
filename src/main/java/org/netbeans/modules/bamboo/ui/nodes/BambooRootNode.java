package org.netbeans.modules.bamboo.ui.nodes;

import org.netbeans.api.annotations.common.StaticResource;
import org.netbeans.api.core.ide.ServicesTabNodeRegistration;

import org.netbeans.modules.bamboo.glue.InstanceManageable;
import org.netbeans.modules.bamboo.glue.ProjectsProvideable;
import org.netbeans.modules.bamboo.ui.actions.AddInstanceAction;
import static org.netbeans.modules.bamboo.ui.nodes.Bundle.*;

import org.openide.nodes.AbstractNode;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;

import static org.openide.util.Lookup.getDefault;
import org.openide.util.NbBundle.Messages;

import javax.swing.Action;


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

    BambooRootNode() {
        super(Children.LEAF, getDefault().lookup(InstanceManageable.class).getLookup());

        init();
    }

    private void init() {
        setName(BAMBOO_NODE_NAME);
        setDisplayName(LBL_BambooNode());
        setShortDescription(TIP_BambooNode());
        setIconBaseWithExtension(ICON_BASE);

        ChildFactory<ProjectsProvideable> factory = new BambooInstanceNodeFactory(getLookup());
        Children children = Children.create(factory, false);
        setChildren(children);
    }

    @Override
    public Action[] getActions(final boolean context) {
        return new Action[] { new AddInstanceAction() };
    }
}
