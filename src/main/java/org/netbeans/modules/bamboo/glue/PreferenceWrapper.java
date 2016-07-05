package org.netbeans.modules.bamboo.glue;

import java.util.prefs.Preferences;
import org.openide.util.NbPreferences;

/**
 *
 */
public class PreferenceWrapper {
    
    private PreferenceWrapper(){}
    
    public static Preferences instancesPrefs() {
        return NbPreferences.forModule(InstanceManageable.class).node("instances");
    }
}
