package org.netbeans.modules.bamboo.glue;

import org.openide.util.Task;

import java.beans.PropertyChangeListener;

import java.io.Serializable;
import java.util.Collection;

import java.util.prefs.Preferences;
import org.netbeans.modules.bamboo.model.ProjectVo;
import org.openide.util.Lookup;

/**
 * This interface represents a bamboo server.
 *
 * @author spindizzy
 */
public interface BambooInstance extends InstanceValues, Lookup.Provider, Serializable {

    /**
     * Get the informations to the version of the associated bamboo server.
     *
     * @return version as VersionInfo
     */
    VersionInfo getVersionInfo();

    Preferences getPreferences();

    /**
     * Removes the instance from the preferences.
     */
    void remove();

    /**
     * Synchonize this instance with the server.
     *
     * @return the sychronize task
     */
    Task synchronize();

    void addPropertyChangeListener(PropertyChangeListener listener);

    void removePropertyChangeListener(PropertyChangeListener listener);

    Collection<ProjectVo> getProjects();

    void setProjects(Collection<ProjectVo> results);

    /**
     * Sets the synchronization interval
     *
     * @param minutes time in minutes
     */
    void changeSyncInterval(int minutes);

}
