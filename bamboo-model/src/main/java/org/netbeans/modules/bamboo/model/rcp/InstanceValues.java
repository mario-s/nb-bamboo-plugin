package org.netbeans.modules.bamboo.model.rcp;

import java.beans.PropertyChangeListener;

/**
 * Interface which will return the necessary values for a connection to the CI
 * server.
 * 
 * @author Mario Schroeder
 */
public interface InstanceValues extends OpenableInBrowser {

    void addPropertyChangeListener(PropertyChangeListener listener);
    /**
     * Name of the Bamboo instance.
     *
     * @return instance name
     */
    String getName();

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
     * @return password as characters
     */
    char[] getPassword();
    
    @Override
    default boolean isAvailable() {
        return false;
    }

    void removePropertyChangeListener(PropertyChangeListener listener);
}
