package org.netbeans.modules.bamboo.ui.actions;

/**
 *
 * @author spindizzy
 */
public interface ActionConstants {
    
     /**
     * The name of the module.
     */
    String MODULE_NAME = "org-netbeans-modules-bamboo";

    /**
     * Path used to load actions for the server instance. A
     * {@code BambooInstance} object should be in the context lookup. May be
     * used e.g. for the context menu of an instance node.
     */
    String ACTION_PATH = MODULE_NAME + "/Actions/instance";
    
    String PLAN_ACTION_PATH = MODULE_NAME + ACTION_PATH + "Plan";
    
    String COMMON_ACTION_PATH = MODULE_NAME + ACTION_PATH + "Common";
    
}
