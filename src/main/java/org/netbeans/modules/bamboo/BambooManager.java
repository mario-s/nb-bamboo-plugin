package org.netbeans.modules.bamboo;

import java.util.Arrays;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import org.netbeans.modules.bamboo.model.BambooInstanceProperties;
import java.util.prefs.Preferences;
import org.netbeans.modules.bamboo.model.BambooInstance;
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

    public static void addInstance(BambooInstance instance) {
        DefaultBambooInstance clone = new DefaultBambooInstance(
                instance.getName(), instance.getUrl());

        BambooInstanceProperties props = new BambooInstanceProperties(instancesPrefs());
        props.copyProperties(clone);
        clone.setProperties(props);

        Instance.content.add(clone);
    }

    public static void removeInstance(BambooInstance instance) {
        try {
            instance.getPreferences().removeNode();
            Instance.content.remove(instance);
        } catch (BackingStoreException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    static void loadInstances() {
        Preferences prefs = instancesPrefs();
        try {
            String[] children = prefs.childrenNames();
            Arrays.asList(children).forEach(child -> {
                BambooInstanceProperties props = new BambooInstanceProperties(
                        prefs);
                props.put(BambooInstanceConstants.INSTANCE_NAME, child);
                DefaultBambooInstance instance = new DefaultBambooInstance();
                instance.setProperties(props);
                Instance.content.add(instance);
            });
            LOG.finer(String.format("loaded nodes: %s", children.length));
        } catch (BackingStoreException ex) {
            Exceptions.printStackTrace(ex);
        }
    }
    
     /**
     * This method returns the root preferences to save, load or delete all Bamboo instances.
     * @return {@link Preferences} the root preferences
     */
    public static Preferences instancesPrefs() {
        return NbPreferences.forModule(BambooManager.class).node("instances");
    }
}
