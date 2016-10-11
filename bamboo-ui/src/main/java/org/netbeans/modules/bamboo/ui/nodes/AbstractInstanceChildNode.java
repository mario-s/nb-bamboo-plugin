package org.netbeans.modules.bamboo.ui.nodes;

import java.beans.PropertyChangeListener;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.Lookup;

/**
 * This is a parant class for all nodes which a children of a bamboo instance.
 * @author spindizzy
 */
abstract class AbstractInstanceChildNode extends AbstractNode implements PropertyChangeListener{

    AbstractInstanceChildNode(Lookup lookup) {
        super(Children.LEAF, lookup);
    }
}
