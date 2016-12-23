package org.netbeans.modules.bamboo.client.rest;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import org.netbeans.modules.bamboo.model.rcp.BambooInstance;
import org.netbeans.modules.bamboo.client.glue.InstanceManageable;

import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.util.lookup.ServiceProvider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import lombok.extern.java.Log;
import org.netbeans.modules.bamboo.client.glue.BuildStatusWatchable;
import org.netbeans.modules.bamboo.LookupContext;

import static org.netbeans.modules.bamboo.client.glue.InstanceConstants.PROP_SYNC_INTERVAL;
import static org.netbeans.modules.bamboo.client.rest.BambooInstanceConstants.INSTANCE_SUPPRESSED_PLANS;
import static org.openide.util.Lookup.getDefault;

/**
 * @author spindizzy
 */
@Log
@ServiceProvider(service = InstanceManageable.class)
public class DefaultInstanceManager implements InstanceManageable, PropertyChangeListener {

    private final BuildStatusWatchable buildStatusWatcher;

    private final LookupContext lookupContext;
    
    public DefaultInstanceManager() {
        lookupContext = LookupContext.Instance;
        buildStatusWatcher = getDefault().lookup(BuildStatusWatchable.class);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propName = evt.getPropertyName();
        if(PROP_SYNC_INTERVAL.equals(propName) || INSTANCE_SUPPRESSED_PLANS.equals(propName)){
            BambooInstance instance = (BambooInstance) evt.getSource();
            persist(instance);
        }
    }

    private void add(final BambooInstance instance) {
        if (instance != null) {
            instance.addPropertyChangeListener(this);
            lookupContext.add(instance);
            buildStatusWatcher.addInstance(instance);
        }
    }

    private void remove(final BambooInstance instance) {
        if (instance != null) {
            instance.removePropertyChangeListener(this);
            lookupContext.remove(instance);
            buildStatusWatcher.removeInstance(instance);
        }
    }

    @Override
    public Lookup getLookup() {
        return lookupContext.getLookup();
    }

    @Override
    public InstanceContent getContent() {
        return lookupContext.getContent();
    }

    @Override
    public void addInstance(final BambooInstance instance) {
        add(instance);
    }

    @Override
    public void removeInstance(final BambooInstance instance) {
        instance.remove();
        remove(instance);

        String name = instance.getName();

        removePrefs(name);
    }

    private void removePrefs(final String name) {
        try {
            Preferences prefs = instancesPrefs();
            Preferences node = prefs.node(name);

            if (node != null) {
                node.removeNode();
            }

            prefs.flush();
        } catch (BackingStoreException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    @Override
    public void removeInstance(final String name) {
        BambooInstance instance = loadInstance(name);

        if (instance != null) {
            removeInstance(instance);
        } else {
            removePrefs(name);
        }
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

            log.finer(String.format("loaded nodes: %s", names.length));
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
            //create a instance with a client anyway since the stored properties must have been valid
            instance = new DefaultBambooInstance(props);
        } catch (IllegalStateException e) {
            log.warning(e.getMessage());
        }

        return instance;
    }

    @Override
    public void persist(final BambooInstance instance) {
        BambooInstanceProperties props = new BambooInstanceProperties(instancesPrefs());
        props.copyProperties(instance);
    }

    synchronized Preferences instancesPrefs() {
        return PreferenceWrapper.instancesPrefs();
    }
}
