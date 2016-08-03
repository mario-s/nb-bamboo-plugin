package org.netbeans.modules.bamboo.glue;

import java.io.Serializable;

import java.util.prefs.Preferences;


/**
 * @author spindizzy
 */
public interface BambooInstance extends InstanceValues, Serializable {
    Preferences getPreferences();

    void remove();

    void synchronize();
}
