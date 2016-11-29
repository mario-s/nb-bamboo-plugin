package org.netbeans.modules.bamboo.glue;

/**
 * Constants for a Bamboo instance shared across packages.
 */
public interface InstanceConstants {
    
    /**
     * The name of the CI instance.
     */
    String PROP_NAME = "name";
    
    /**
     * THe url to the server.
     */
    String PROP_URL = "url";
    
    /**
     * The synchronization interval.
     */
    String PROP_SYNC_INTERVAL = "syncInterval";
    
    /**
     * Nonsalient plans for the instance, list of job names, separated by |.
     */
    String SUPPRESSED_PLANS = "suppressed_plans";
    
}
