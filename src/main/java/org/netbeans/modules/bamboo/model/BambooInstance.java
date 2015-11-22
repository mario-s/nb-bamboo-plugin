package org.netbeans.modules.bamboo.model;

import java.io.Serializable;
import java.util.prefs.Preferences;

/**
 *
 * @author spindizzy
 */
public interface BambooInstance extends Serializable{
    
    /**
     * Path used to load actions for the server instance.
     * A {@code HudsonInstance} object should be in the context lookup.
     * May be used e.g. for the context menu of an instance node.
     * @see ActionReference#path
     * @since 1.12
     */
    String ACTION_PATH = "org-netbeans-modules-bamboo/Actions/instance";
    
    /**
     * Name of the Bamboo instance
     *
     * @return instance name
     */
    String getName();
 
    /**
     * URL of the Bamboo instance
     *
     * @return instance url
     */
    String getUrl();
    
    Preferences getPreferences();
}
