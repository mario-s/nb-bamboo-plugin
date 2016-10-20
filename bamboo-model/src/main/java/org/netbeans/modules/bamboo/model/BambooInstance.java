package org.netbeans.modules.bamboo.model;

import org.openide.util.Task;

import java.beans.PropertyChangeListener;

import java.io.Serializable;

import java.util.prefs.Preferences;
import org.openide.util.Lookup;

/**
 * This interface represents a bamboo server.
 *
 * @author spindizzy
 */
public interface BambooInstance extends 
        InstanceValues, Lookup.Provider, Serializable, TraverseDown<ProjectVo> {

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

    /**
     * This methos updates the synchronization interval and restart any scheduled synchronization tasks.
     *
     * @param minutes time in minutes
     */
    void updateSyncInterval(int minutes);

    /**
     * This method returns <code>true</code> when the CI server could be reached, if not it returns <code>false</code>.
     *
     * @return <code>true</code> when server is present, otherwhise <code>false</code>
     */
    boolean isAvailable();
    
    /**
     * This method will queue the plan for a build on the server, and fires an event with the response code from the 
     * server.
     * 
     * @param plan the {@link PlanVo} to queue for the build.
     */
    void queue(PlanVo plan);
    
}
