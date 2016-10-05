package org.netbeans.modules.bamboo.rest;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import lombok.extern.java.Log;
import org.netbeans.modules.bamboo.glue.InstanceManageable;
import org.openide.util.NbPreferences;

/**
 *
 */
@Log
class PreferenceWrapper {
    
    private PreferenceWrapper(){}
    
    static Preferences instancesPrefs() {
        Preferences prefs = NbPreferences.forModule(InstanceManageable.class).node("instances");
        try {
            prefs.sync();
        } catch (BackingStoreException ex) {
            log.info(ex.getMessage());
        }
        return prefs;
    }
}
