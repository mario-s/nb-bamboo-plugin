package org.netbeans.modules.bamboo.glue;

import org.netbeans.modules.bamboo.glue.BambooInstance;
import org.netbeans.modules.bamboo.glue.InstanceValues;
import org.netbeans.modules.bamboo.model.BambooInstanceConstants;
import org.netbeans.modules.bamboo.model.BambooInstanceProperties;
import org.netbeans.modules.bamboo.model.DefaultBambooInstance;

import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.NbPreferences;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;

import java.awt.EventQueue;

import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;


/**
 * Glue to interact. It manages the load, save and delete of instances.
 *
 * @author spindizzy
 */
public enum BambooManager implements Lookup.Provider {
    Instance;

    private static final Logger LOG = Logger.getLogger(BambooManager.class.getName());

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

    /**
     * Adds a new Instance to Lookup.
     *
     * @param values the instance values
     */
    public static void addInstance(final InstanceValues values) {
        EventQueue.invokeLater(() -> { add(values); });
    }

    /**
     * This methos should be only used for testing purpose.
     *
     * @param values
     */
    static void add(final InstanceValues values) {
        DefaultBambooInstance instance = new DefaultBambooInstance(values);
        BambooInstanceProperties props = new BambooInstanceProperties(instancesPrefs());
        props.copyProperties(instance);
        instance.setProperties(props);

        Instance.content.add(instance);
    }

    public static void removeInstance(final BambooInstance instance) {
        try {
            instance.getPreferences().removeNode();
            Instance.content.remove(instance);
        } catch (BackingStoreException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    public static boolean existsInstance(final String name) {
        try {
            String[] names = instancesPrefs().childrenNames();

            for (String prefName : names) {
                if (prefName.equals(name)) {
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
    public static void loadInstances() {
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

    private static void loadInstance(final Preferences prefs, final String name) {
        BambooInstanceProperties props = new BambooInstanceProperties(prefs);
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
