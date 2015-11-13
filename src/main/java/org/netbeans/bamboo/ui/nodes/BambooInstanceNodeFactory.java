package org.netbeans.bamboo.ui.nodes;

import java.util.List;
import org.netbeans.bamboo.model.BambooInstance;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;

/**
 *
 * @author spindizzy
 */
class BambooInstanceNodeFactory extends ChildFactory<BambooInstance> implements LookupListener {
    private final Lookup lookup;
    
    
    private Lookup.Result<BambooInstance> result;

    public BambooInstanceNodeFactory(Lookup lookup) {
        this.lookup = lookup;
        lookupResult();
    }

    private void lookupResult() {
        result = lookup.lookupResult(BambooInstance.class);
        result.addLookupListener(this);
    }

    @Override
    protected Node createNodeForKey(final BambooInstance key) {
        return new BambooInstanceNode(key);
    }

    @Override
    protected boolean createKeys(final List<BambooInstance> toPopulate) {
        toPopulate.addAll(result.allInstances());
        return true;
    }

    @Override
    public void resultChanged(LookupEvent ev) {
        refresh(true);
    }
    
}
