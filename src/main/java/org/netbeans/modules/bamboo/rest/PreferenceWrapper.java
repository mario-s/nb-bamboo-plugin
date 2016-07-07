package org.netbeans.modules.bamboo.rest;

import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import org.netbeans.modules.bamboo.glue.InstanceManageable;
import org.openide.util.NbPreferences;

/**
 *
 */
class PreferenceWrapper {
    
    private static final Logger LOG = Logger.getLogger(PreferenceWrapper.class.getName());
    
    private PreferenceWrapper(){}
    
    static Preferences instancesPrefs() {
        Preferences prefs = NbPreferences.forModule(InstanceManageable.class).node("instances");
        try {
            prefs.sync();
        } catch (BackingStoreException ex) {
            LOG.info(ex.getMessage());
        }
        return prefs;
    }
}
