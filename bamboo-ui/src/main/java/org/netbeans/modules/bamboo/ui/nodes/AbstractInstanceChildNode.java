package org.netbeans.modules.bamboo.ui.nodes;

import java.beans.PropertyChangeListener;
import java.util.List;
import javax.swing.Action;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.Lookup;

import static org.openide.util.Utilities.actionsForPath;

/**
 * This is a parant class for all nodes which a children of a bamboo instance.
 *
 * @author spindizzy
 */
abstract class AbstractInstanceChildNode extends AbstractNode implements PropertyChangeListener {

    AbstractInstanceChildNode(Lookup lookup) {
        super(Children.LEAF, lookup);
    }

    protected List<? extends Action> findActions(String path) {
        return actionsForPath(path);
    }

}
