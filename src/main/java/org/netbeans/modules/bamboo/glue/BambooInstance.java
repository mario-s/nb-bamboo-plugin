package org.netbeans.modules.bamboo.glue;

import java.beans.PropertyChangeListener;

import java.io.Serializable;

import java.util.prefs.Preferences;


/**
 * This interface represents a bamboo server.
 *
 * @author spindizzy
 */
public interface BambooInstance extends InstanceValues, Serializable {
    Preferences getPreferences();

    void remove();

    void synchronize();

    void addPropertyChangeListener(PropertyChangeListener listener);

    void removePropertyChangeListener(PropertyChangeListener listener);
}
