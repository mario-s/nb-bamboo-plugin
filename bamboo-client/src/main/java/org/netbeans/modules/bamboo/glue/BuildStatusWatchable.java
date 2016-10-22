package org.netbeans.modules.bamboo.glue;

import org.netbeans.modules.bamboo.model.BambooInstance;

/**
 * Interface to watch the build state.
 * @author spindizzy
 */
public interface BuildStatusWatchable {
    
    /**
     * Add in instance to watch.
     * @param instance the bamboo instance
     */
    void addInstance(BambooInstance instance);
    
    /**
     * Remove an instance from beeing watched.
     * @param instance the bamboo instance
     */
    void removeInstance(BambooInstance instance);
}
