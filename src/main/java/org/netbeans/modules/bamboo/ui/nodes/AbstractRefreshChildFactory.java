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
abstract class AbstractRefreshChildFactory<T> extends ChildFactory<T> {

    abstract void refreshNodes();

}
