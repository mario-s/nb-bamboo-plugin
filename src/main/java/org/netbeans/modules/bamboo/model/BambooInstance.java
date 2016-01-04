package org.netbeans.modules.bamboo.model;

import java.io.Serializable;

import java.util.prefs.Preferences;


/**
 * @author spindizzy
 */
public interface BambooInstance extends OpenableInBrowser, Serializable {
    /**
     * Path used to load actions for the server instance. A
     * {@code BambooInstance} object should be in the context lookup. May be
     * used e.g. for the context menu of an instance node.
     *
     * @see   ActionReference#path
     * @since 1.12
     */
    String ACTION_PATH = "org-netbeans-modules-bamboo/Actions/instance";

    /**
     * Name of the Bamboo instance.
     *
     * @return instance name
     */
    String getName();

    Preferences getPreferences();

    /**
     * @return Synchronization interval in minutes.
     */
    int getSyncInterval();

    /**
     * Get the user's name used for authentication.
     *
     * @return user name
     */
    String getUsername();

    /**
     * Get the password of the user.
     *
     * @return password as String
     */
    String getPassword();
}
