package org.netbeans.modules.bamboo;

import java.io.Serializable;

import java.util.prefs.Preferences;


/**
 * @author spindizzy
 */
public interface BambooInstance extends InstanceValues, Serializable {

    Preferences getPreferences();

}
