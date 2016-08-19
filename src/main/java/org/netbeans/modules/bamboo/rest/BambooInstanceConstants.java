package org.netbeans.modules.bamboo.rest;

/**
 *
 * @author spindizzy
 */
final class BambooInstanceConstants {

    private BambooInstanceConstants() {}
    
    static final String INSTANCE_SYNC = "sync_time"; 
    
    static final String INSTANCE_USER = "username";
    
    static final String INSTANCE_PASSWORD = "password";

    /**
     * preferred jobs for the instance, list of job names, separated by |
     */
    static final String INSTANCE_PREF_JOBS = "pref_jobs";

    /**
     * Nonsalient jobs for the instance, list of job names, separated by |
     */
    static final String INSTANCE_SUPPRESSED_JOBS = "suppressed_jobs";

    static final String INSTANCE_PERSISTED = "persisted";

    /**
     * True value, e.g. for {@link #INSTANCE_PERSISTED} key.
     */
    static final String TRUE = "true";
    /**
     * False value, e.g. for {@link #INSTANCE_PERSISTED} key.
     */
    static final String FALSE = "false";
    
}
