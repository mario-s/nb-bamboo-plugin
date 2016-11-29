package org.netbeans.modules.bamboo.ui.nodes;

import java.util.Collection;
import java.util.concurrent.CountDownLatch;
import org.netbeans.api.annotations.common.StaticResource;
import org.netbeans.api.core.ide.ServicesTabNodeRegistration;

import org.netbeans.modules.bamboo.glue.InstanceManageable;
import org.netbeans.modules.bamboo.ui.actions.AddInstanceAction;

import static org.netbeans.modules.bamboo.ui.nodes.Bundle.*;

import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;

import static org.openide.util.Lookup.getDefault;

import org.openide.util.NbBundle.Messages;

import javax.swing.Action;
import org.netbeans.modules.bamboo.model.event.InstancesLoadEvent;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;

/**
 * Root node for the Bamboo Builder
 *
 * @author spindizzy
 */
@ServicesTabNodeRegistration(
        name = BambooRootNode.BAMBOO_NODE_NAME, displayName = "#LBL_BambooNode",
        shortDescription = "#TIP_BambooNode", iconResource = BambooRootNode.ICON_BASE, position = 450
)
@Messages(
        {"LBL_BambooNode=Bamboo Builders", "TIP_BambooNode=Bamboo continuous integration servers."}
)
public final class BambooRootNode extends AbstractNode implements LookupListener {

    static final String BAMBOO_NODE_NAME = "bamboo";

    @StaticResource
    static final String ICON_BASE = "org/netbeans/modules/bamboo/resources/ci.png";

    private final BambooInstanceNodeFactory nodeFactory;

    private Lookup.Result<InstancesLoadEvent> eventResult;

    private boolean lazy;

    private final InstanceManageable instanceManager;

    BambooRootNode() {
        this(true);
    }

    BambooRootNode(boolean lazy) {
        super(Children.LEAF, getDefault());
        this.lazy = lazy;

        instanceManager = getDefault().lookup(InstanceManageable.class);
        nodeFactory = new BambooInstanceNodeFactory(instanceManager.getLookup());

        init();
    }

    @Override
    public Action[] getActions(final boolean context) {
        return new Action[]{new AddInstanceAction()};
    }

    private void init() {
        eventResult = instanceManager.getLookup().lookupResult(InstancesLoadEvent.class);
        resultChanged(null);

        setName(BAMBOO_NODE_NAME);
        setDisplayName(LBL_BambooNode());
        setShortDescription(TIP_BambooNode());
        setIconBaseWithExtension(ICON_BASE);

        setChildren(Children.create(nodeFactory, lazy));
    }

    @Override
    public void resultChanged(final LookupEvent ev) {
        Collection<? extends InstancesLoadEvent> events = eventResult.allInstances();
        if (!events.isEmpty()) {
            InstancesLoadEvent first = events.iterator().next();
            CountDownLatch countDown = new CountDownLatch(first.getInstances().size());
            nodeFactory.setBlocker(countDown);
        }
    }
    
}
