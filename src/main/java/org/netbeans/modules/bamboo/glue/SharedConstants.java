package org.netbeans.modules.bamboo.glue;

/**
 * Constants shared across packages.
 */
public interface SharedConstants {
    
    /**
     * The name of the module.
     */
    String MODULE_NAME = "org-netbeans-modules-bamboo";

    /**
     * Path used to load actions for the server instance. A
     * {@code BambooInstance} object should be in the context lookup. May be
     * used e.g. for the context menu of an instance node.
     *
     * @see ActionReference#path
     */
    String ACTION_PATH = MODULE_NAME + "/Actions/instance";
    
    
    String INSTANCE_NAME = "name";
    
    String INSTANCE_URL = "url";
    
}
