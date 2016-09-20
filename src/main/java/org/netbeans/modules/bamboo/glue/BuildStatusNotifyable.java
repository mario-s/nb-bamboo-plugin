package org.netbeans.modules.bamboo.glue;

/**
 *  Interface to notify about a failed build.
 * 
 * @author spindizzy
 */
public interface BuildStatusNotifyable {
    
    /**
     * Add the manager to add/ remove/ update instances.
     * @param instance 
     */
    void setManager(InstanceManageable manager);
    
}
