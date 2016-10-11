package org.netbeans.modules.bamboo.ui.nodes;

import java.beans.PropertyChangeListener;
import org.netbeans.modules.bamboo.glue.BambooInstance;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.Lookup;

/**
 * This is a parant class for all nodes which a children of a {@link BambooInstance}.
 * @author spindizzy
 */
abstract class AbstractInstanceChildNode extends AbstractNode implements PropertyChangeListener{
    
    private final BambooInstance instance;

    AbstractInstanceChildNode(BambooInstance instance, Lookup lookup) {
        super(Children.LEAF, lookup);
        this.instance = instance;
    }

    protected BambooInstance getInstance() {
        return instance;
    }
    
}
