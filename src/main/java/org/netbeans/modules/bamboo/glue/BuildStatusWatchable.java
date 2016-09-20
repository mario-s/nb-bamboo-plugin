package org.netbeans.modules.bamboo.glue;

/**
 * Interface to watch the build state.
 * @author spindizzy
 */
public interface BuildStatusWatchable {
    
    /**
     * Add in instance to watch.
     * @param projectsProvider 
     */
    void addInstance(BambooInstance projectsProvider);
    
    /**
     * Remove an instance from beeing watched.
     * @param projectsProvider 
     */
    void removeInstance(BambooInstance projectsProvider);
}
