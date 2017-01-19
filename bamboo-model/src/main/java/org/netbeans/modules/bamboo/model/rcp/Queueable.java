package org.netbeans.modules.bamboo.model.rcp;

/**
 * This interface defines methods to queue a plan.
 *
 * @author Mario Schroeder
 */
public interface Queueable extends InstanceInvokeable {

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
