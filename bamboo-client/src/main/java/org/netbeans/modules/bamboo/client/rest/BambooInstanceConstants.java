package org.netbeans.modules.bamboo.client.rest;

/**
 *
 * @author Mario Schroeder
 */
final class BambooInstanceConstants {

    private BambooInstanceConstants() {}
    
    static final String INSTANCE_USER = "username";
    
    static final String INSTANCE_PASSWORD = "password";

    /**
     * preferred jobs for the instance, list of job names, separated by |
     */
    static final String INSTANCE_PREF_JOBS = "pref_jobs";

    /**
     * Nonsalient plans for the instance, list of plan keys, separated by /
     */
    static final String INSTANCE_SUPPRESSED_PLANS = "suppressed_plans";

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
