package org.netbeans.modules.bamboo.rest;

import org.netbeans.modules.bamboo.glue.BambooInstance;
import org.netbeans.modules.bamboo.glue.InstanceManageable;

import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.util.lookup.ServiceProvider;

import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 * @author spindizzy
 */
@ServiceProvider(service = InstanceManageable.class)
public class DefaultInstanceManager implements InstanceManageable {

    private static final Logger LOG = Logger.getLogger(DefaultInstanceManager.class.getName());

    private final InstanceContent content;
    private final Lookup lookup;

    public DefaultInstanceManager() {
        this.content = new InstanceContent();
        this.lookup = new AbstractLookup(content);
    }

    private void add(final BambooInstance instance) {
        if (instance != null) {
            content.add(instance);
        }
    }

    private void remove(final BambooInstance instance) {
        content.remove(instance);
    }

    @Override
    public Lookup getLookup() {
        return lookup;
    }

    @Override
    public InstanceContent getContent() {
        return content;
    }

    @Override
    public void addInstance(final BambooInstance instance) {
        add(instance);
    }

    @Override
    public void removeInstance(final BambooInstance instance) {
        try {
            instancesPrefs().remove(instance.getName());
            instance.getPreferences().removeNode();
            remove(instance);
        } catch (BackingStoreException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    @Override
    public void removeInstance(String name) {
        if (existsInstance(name)) {
            removeInstance(loadInstance(name));
        }
    }

    @Override
    public boolean existsInstance(final String name) {
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

    @Override
    public void loadInstances() {

        try {
            String[] names = instancesPrefs().childrenNames();

            for (String name : names) {
                add(loadInstance(name));
            }

            LOG.finer(String.format("loaded nodes: %s", names.length));
        } catch (BackingStoreException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    private BambooInstance loadInstance(final String name) {
        DefaultBambooInstance instance = null;
        try {
            BambooInstanceProperties props = new BambooInstanceProperties(instancesPrefs());
            props.put(BambooInstanceConstants.INSTANCE_NAME, name);

            instance = new DefaultBambooInstance();
            instance.setProperties(props);
        } catch (IllegalStateException e) {
            LOG.warning(e.getMessage());
        }
        return instance;
    }

    synchronized Preferences instancesPrefs() {
        return PreferenceWrapper.instancesPrefs();
    }

}
