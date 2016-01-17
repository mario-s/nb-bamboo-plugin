package org.netbeans.modules.bamboo.ui.nodes;

import java.io.IOException;
import java.util.List;
import javax.swing.Action;
import org.netbeans.api.annotations.common.StaticResource;
import org.netbeans.modules.bamboo.BambooManager;
import org.netbeans.modules.bamboo.glue.SharedConstants;
import org.netbeans.modules.bamboo.glue.BambooInstance;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.Utilities;
import org.openide.util.lookup.Lookups;

/**
 * This class is the node of a Bamboo CI server.
 * @author spindizzy
 */
public class BambooInstanceNode extends AbstractNode {

    @StaticResource
    private static final String ICON_BASE = "org/netbeans/modules/bamboo/resources/instance.png";

    private BambooInstance instance;

    public BambooInstanceNode(final BambooInstance instance) {
        super(Children.LEAF, Lookups.singleton(instance));
        this.instance = instance;

        init();
    }

    private void init() {
        setName(instance.getUrl());
        setDisplayName(instance.getName());
        setShortDescription(instance.getUrl());
        setIconBaseWithExtension(ICON_BASE);
    }

    @Override
    public Action[] getActions(boolean context) {
        List<? extends Action> actions = Utilities.actionsForPath(
                SharedConstants.ACTION_PATH);
        
        return actions.toArray(new Action[actions.size()]);
    }

    @Override
    public boolean canDestroy() {
        return true;
    }

    @Override
    public void destroy() throws IOException {
        BambooManager.removeInstance(instance);
    }
}
