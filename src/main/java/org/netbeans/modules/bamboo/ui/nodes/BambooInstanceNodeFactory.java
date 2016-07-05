package org.netbeans.modules.bamboo.ui.nodes;

import java.util.List;
import org.netbeans.modules.bamboo.glue.BambooInstance;
import org.netbeans.modules.bamboo.model.PlansProvideable;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;

/**
 *
 * @author spindizzy
 */
class BambooInstanceNodeFactory extends ChildFactory<PlansProvideable> implements LookupListener {
    private final Lookup lookup;
    
    private Lookup.Result<PlansProvideable> result;

    public BambooInstanceNodeFactory(Lookup lookup) {
        this.lookup = lookup;
        lookupResult();
    }

    private void lookupResult() {
        result = lookup.lookupResult(PlansProvideable.class);
        result.addLookupListener(this);
        resultChanged(null);
    }

    @Override
    protected Node createNodeForKey(final PlansProvideable key) {
        return new BambooInstanceNode(key);
    }

    @Override
    protected boolean createKeys(final List<PlansProvideable> toPopulate) {
        toPopulate.addAll(result.allInstances());
        return true;
    }

    @Override
    public void resultChanged(LookupEvent ev) {
        refresh(true);
    }
    
}
