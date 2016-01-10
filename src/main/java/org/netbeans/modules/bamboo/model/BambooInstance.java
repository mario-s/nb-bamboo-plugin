package org.netbeans.modules.bamboo.model;

import java.io.Serializable;

import java.util.prefs.Preferences;


/**
 * @author spindizzy
 */
public interface BambooInstance extends OpenableInBrowser, Serializable {

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
