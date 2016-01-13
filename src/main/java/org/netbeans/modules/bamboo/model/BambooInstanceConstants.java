package org.netbeans.modules.bamboo.model;

/**
 *
 * @author spindizzy
 */
public final class BambooInstanceConstants {

    private BambooInstanceConstants() {}
    
    public static final String INSTANCE_NAME = "name";
    
    public static final String INSTANCE_URL = "url";
    
    public static final String INSTANCE_SYNC = "sync_time"; 
    
    public static final String INSTANCE_USER = "username";
    
    public static final String INSTANCE_PASSWORD = "password";

    /**
     * preferred jobs for the instance, list of job names, separated by |
     */
    public static final String INSTANCE_PREF_JOBS = "pref_jobs";

    /**
     * Nonsalient jobs for the instance, list of job names, separated by |
     */
    public static final String INSTANCE_SUPPRESSED_JOBS = "suppressed_jobs";

    public static final String INSTANCE_PERSISTED = "persisted";

    /**
     * True value, e.g. for {@link #INSTANCE_PERSISTED} key.
     */
    public static final String TRUE = "true";
    /**
     * False value, e.g. for {@link #INSTANCE_PERSISTED} key.
     */
    public static final String FALSE = "false";
    
}
