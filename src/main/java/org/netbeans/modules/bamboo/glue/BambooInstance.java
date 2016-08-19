package org.netbeans.modules.bamboo.glue;

import org.openide.util.Task;

import java.beans.PropertyChangeListener;

import java.io.Serializable;

import java.util.prefs.Preferences;


/**
 * This interface represents a bamboo server.
 *
 * @author spindizzy
 */
public interface BambooInstance extends InstanceValues, Serializable {
    /**
     * Get the informations to the version of the associated bamboo server.
     *
     * @return version as VersionInfo
     */
    VersionInfo getVersion();

    Preferences getPreferences();

    void remove();

    /**
     * Synchonize this instance with the server.
     *
     * @return the sychronize task
     */
    Task synchronize();

    void addPropertyChangeListener(PropertyChangeListener listener);

    void removePropertyChangeListener(PropertyChangeListener listener);
}
