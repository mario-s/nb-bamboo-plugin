package org.netbeans.modules.bamboo.rest;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import org.netbeans.modules.bamboo.glue.BambooInstance;
import org.netbeans.modules.bamboo.glue.InstanceManageable;

import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.util.lookup.ServiceProvider;

import java.util.ArrayList;
import java.util.Collection;
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
            instance.remove();

            Preferences prefs = instancesPrefs();
            Preferences node = prefs.node(instance.getName());
            node.removeNode();
            prefs.flush();

            remove(instance);
        } catch (BackingStoreException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    @Override
    public void removeInstance(final String name) {
        removeInstance(loadInstance(name));
    }

    @Override
    public boolean existsInstance(final String name) {
        boolean exists = false;

        try {
            if (isNotBlank(name)) {
                exists = instancesPrefs().nodeExists(name);
            }
        } catch (BackingStoreException ex) {
            Exceptions.printStackTrace(ex);
        }

        return exists;
    }

    @Override
    public Collection<BambooInstance> loadInstances() {
        Collection<BambooInstance> instances = new ArrayList<>();

        try {
            String[] names = instancesPrefs().childrenNames();

            for (String name : names) {
                instances.add(loadInstance(name));
            }

            LOG.finer(String.format("loaded nodes: %s", names.length));
        } catch (BackingStoreException ex) {
            Exceptions.printStackTrace(ex);
        }

        return instances;
    }

    private BambooInstance loadInstance(final String name) {
        DefaultBambooInstance instance = null;

        try {
            BambooInstanceProperties props = new BambooInstanceProperties(instancesPrefs());
            props.loadPreferences(name);

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
