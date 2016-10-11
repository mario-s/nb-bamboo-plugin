package org.netbeans.modules.bamboo.ui.nodes;

import org.openide.nodes.ChildFactory;

/**
 *
 * @author spindizzy
 */
abstract class AbstractRefreshChildFactory<T> extends ChildFactory<T> {
    
    abstract void refreshNodes();

}
