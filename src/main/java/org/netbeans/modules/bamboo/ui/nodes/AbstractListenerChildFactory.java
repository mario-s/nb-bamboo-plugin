package org.netbeans.modules.bamboo.ui.nodes;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.openide.nodes.ChildFactory;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;

/**
 *
 * @author spindizzy
 */
abstract class AbstractListenerChildFactory<T> extends ChildFactory<T> implements LookupListener, PropertyChangeListener {

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        refreshNodes();
    }

    @Override
    public void resultChanged(LookupEvent ev) {
        refreshNodes();
    }

    abstract void refreshNodes();

    abstract void removePropertyChangeListener();
}
