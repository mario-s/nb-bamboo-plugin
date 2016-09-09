package org.netbeans.modules.bamboo.ui.nodes;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.openide.nodes.ChildFactory;

/**
 *
 * @author spindizzy
 */
abstract class AbstractListenerChildFactory<T> extends ChildFactory<T> implements PropertyChangeListener {

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        refreshNodes();
    }

    abstract void refreshNodes();

    abstract void removePropertyChangeListener();
}
