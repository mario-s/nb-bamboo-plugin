package org.netbeans.bamboo.ui.nodes;

import java.util.List;
import javax.swing.Action;
import org.netbeans.api.core.ide.ServicesTabNodeRegistration;
import org.netbeans.bamboo.LookupProvider;
import org.netbeans.bamboo.model.BambooInstance;
import org.netbeans.bamboo.ui.actions.AddInstanceAction;
import static org.netbeans.bamboo.ui.nodes.Bundle.*;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.NbBundle.Messages;

@ServicesTabNodeRegistration(
        name = BambooRootNode.BAMBOO_NODE_NAME,
        displayName = "#LBL_BambooNode",
        shortDescription = "#TIP_BambooNode",
        iconResource = BambooRootNode.ICON_BASE,
        position = 600)
@Messages({
    "LBL_BambooNode=Bamboo Builders",
    "TIP_BambooNode=Bamboo continuous integration servers."
})
public final class BambooRootNode extends AbstractNode {


    static final String BAMBOO_NODE_NAME = "bamboo";

    static final String ICON_BASE = "org/netbeans/bamboo/resources/bamboo.png";

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
