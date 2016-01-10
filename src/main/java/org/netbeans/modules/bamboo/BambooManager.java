package org.netbeans.modules.bamboo;

import org.netbeans.modules.bamboo.glue.DefaultInstanceValues;
import org.netbeans.modules.bamboo.model.BambooInstanceConstants;
import java.util.Arrays;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import org.netbeans.modules.bamboo.model.BambooInstanceProperties;
import java.util.prefs.Preferences;
import org.netbeans.modules.bamboo.glue.BambooInstance;
import org.netbeans.modules.bamboo.model.DefaultBambooInstance;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.NbPreferences;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;

/**
 * Glue to interact. It manages the load, save and delete of instances.
 *
 * @author spindizzy
 */
public enum BambooManager implements Lookup.Provider {

    Instance;

    private static final Logger LOG = Logger.getLogger(
            BambooManager.class.getName());

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

    public static void addInstance(DefaultInstanceValues values) {
        DefaultBambooInstance instance = new DefaultBambooInstance(
                values.getName(), values.getUrl());
        instance.setSyncInterval(values.getSyncTime());

        BambooInstanceProperties props = new BambooInstanceProperties(
                instancesPrefs());
        props.copyProperties(instance);
        instance.setProperties(props);

        Instance.content.add(instance);
    }

    public static void removeInstance(BambooInstance instance) {
        try {
            instance.getPreferences().removeNode();
            Instance.content.remove(instance);
        } catch (BackingStoreException ex) {
            Exceptions.printStackTrace(ex);
        }
    }
    
    public static boolean existsInstance(String name) {
        try {
            String[] names = instancesPrefs().childrenNames();
            for(String prefName : names) {
                if(prefName.equals(name)){
                    return true;
                }
            }
        } catch (BackingStoreException ex) {
            Exceptions.printStackTrace(ex);
        }
        return false;
    }

    /**
     * This method loads all existing instances.
     */
    static void loadInstances() {
        Preferences prefs = instancesPrefs();
        try {
            String[] names = prefs.childrenNames();
            for (String name : names) {
                loadInstance(prefs, name);
            }
            LOG.finer(String.format("loaded nodes: %s", names.length));
        } catch (BackingStoreException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    private static void loadInstance(Preferences prefs, String name) {
        BambooInstanceProperties props = new BambooInstanceProperties(
                prefs);
        props.put(BambooInstanceConstants.INSTANCE_NAME, name);
        DefaultBambooInstance instance = new DefaultBambooInstance();
        instance.setProperties(props);
        Instance.content.add(instance);
    }

    /**
     * This method returns the root preferences to save, load or delete all
     * Bamboo instances.
     *
     * @return {@link Preferences} the root preferences
     */
    public static Preferences instancesPrefs() {
        return NbPreferences.forModule(BambooManager.class).node("instances");
    }
}
