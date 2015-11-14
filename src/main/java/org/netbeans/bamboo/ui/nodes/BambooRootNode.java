package org.netbeans.bamboo.ui.nodes;

import javax.swing.Action;
import org.netbeans.api.annotations.common.StaticResource;
import org.netbeans.api.core.ide.ServicesTabNodeRegistration;
import org.netbeans.bamboo.LookupProvider;
import org.netbeans.bamboo.ui.actions.AddInstanceAction;
import static org.netbeans.bamboo.ui.nodes.Bundle.*;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.NbBundle.Messages;

@ServicesTabNodeRegistration(
        name = BambooRootNode.BAMBOO_NODE_NAME,
        displayName = "#LBL_BambooNode",
        shortDescription = "#TIP_BambooNode",
        iconResource = BambooRootNode.ICON_BASE,
        position = 450)
@Messages({
    "LBL_BambooNode=Bamboo Builders",
    "TIP_BambooNode=Bamboo continuous integration servers."
})
public final class BambooRootNode extends AbstractNode {


    static final String BAMBOO_NODE_NAME = "bamboo";

    @StaticResource
    static final String ICON_BASE = "org/netbeans/bamboo/resources/ci.png";

    BambooRootNode() {
        super(Children.LEAF, LookupProvider.Instance.getLookup());

        setName(BAMBOO_NODE_NAME);
        setDisplayName(LBL_BambooNode());
        setShortDescription(TIP_BambooNode());
        setIconBaseWithExtension(ICON_BASE);
        setChildren(Children.create(new BambooInstanceNodeFactory(getLookup()), false));
    }

    @Override
    public Action[] getActions(boolean context) {
        return new Action[]{new AddInstanceAction()};
    }

}
