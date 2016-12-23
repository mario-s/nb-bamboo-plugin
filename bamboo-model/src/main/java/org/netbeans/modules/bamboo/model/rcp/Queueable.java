package org.netbeans.modules.bamboo.model.rcp;

/**
 * This interface defines a methods to queue something (normally a plan).
 * @author spindizzy
 */
public interface Queueable extends Availability{
    
    /**
     * This method returns true if the plan is enabled.
     * @return boolean
     */
    boolean isEnabled();
    
    /**
     * Queues the plan for the next available build.
     */
    void queue();
}
