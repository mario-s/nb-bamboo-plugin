package org.netbeans.modules.bamboo.ui.nodes;

import org.netbeans.api.annotations.common.NonNull;
import org.netbeans.modules.bamboo.glue.BambooInstance;
import org.openide.nodes.ChildFactory;

/**
 *
 * @author spindizzy
 */
abstract class AbstractRefreshChildFactory<T> extends ChildFactory<T> {
    
    private final BambooInstance instance;
    
    AbstractRefreshChildFactory(@NonNull BambooInstance instance) {
        this.instance = instance;
    }

    protected BambooInstance getInstance() {
        return instance;
    }

    abstract void refreshNodes();

}
