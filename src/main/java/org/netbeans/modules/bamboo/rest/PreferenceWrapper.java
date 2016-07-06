package org.netbeans.modules.bamboo.rest;

import java.util.prefs.Preferences;
import org.netbeans.modules.bamboo.glue.InstanceManageable;
import org.openide.util.NbPreferences;

/**
 *
 */
class PreferenceWrapper {
    
    private PreferenceWrapper(){}
    
    static Preferences instancesPrefs() {
        return NbPreferences.forModule(InstanceManageable.class).node("instances");
    }
}
