package org.netbeans.modules.bamboo;

import org.netbeans.modules.bamboo.model.BambooInstanceProperties;
import java.util.prefs.Preferences;
import org.netbeans.modules.bamboo.model.DefaultBambooInstance;
import org.openide.util.Lookup;
import org.openide.util.NbPreferences;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;

/**
 * Glue to interact.
 * @author spindizzy
 */
public enum BambooManager implements Lookup.Provider{
    
    Instance;
    
    private final Lookup lookup;
    private final InstanceContent content;
    
    private BambooManager() {
        content = new InstanceContent();
        lookup = new AbstractLookup(content);
    }

    @Override
    public Lookup getLookup() {
        return lookup;
    }

    public InstanceContent getContent() {
        return content;
    }
    
    public static void addInstance(String name, String url, int sync) {
        DefaultBambooInstance instance = new DefaultBambooInstance(name, url);
        BambooInstanceProperties props = new BambooInstanceProperties(instancesPrefs());
        props.copyProperties(instance);
        instance.setProperties(props);
        Instance.content.add(instance);
    }
    
    private static Preferences instancesPrefs() {
        return NbPreferences.forModule(BambooManager.class).node("instances");
    }
}
