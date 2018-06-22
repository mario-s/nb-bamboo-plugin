/* 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
import java.util.HashMap;
import java.util.Map;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import lombok.extern.java.Log;
import org.netbeans.modules.bamboo.client.glue.BuildStatusWatchable;
import org.netbeans.modules.bamboo.LookupContext;

import static org.netbeans.modules.bamboo.client.glue.InstanceConstants.PROP_SYNC_INTERVAL;
import static org.netbeans.modules.bamboo.client.rest.BambooInstanceConstants.INSTANCE_SUPPRESSED_PLANS;
import static org.openide.util.Lookup.getDefault;

/**
 * @author Mario Schroeder
 */
@Log
@ServiceProvider(service = InstanceManageable.class)
public class DefaultInstanceManager implements InstanceManageable, PropertyChangeListener {

    private final BuildStatusWatchable buildStatusWatcher;

    private final LookupContext lookupContext;

    /**
     * Holds all shown instances.
     */
    private final Map<String, String> instanceMap;

    public DefaultInstanceManager() {
        instanceMap = new HashMap<>();
        lookupContext = LookupContext.Instance;
        buildStatusWatcher = getDefault().lookup(BuildStatusWatchable.class);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propName = evt.getPropertyName();
        if (PROP_SYNC_INTERVAL.equals(propName) || INSTANCE_SUPPRESSED_PLANS.equals(propName)) {
            BambooInstance instance = (BambooInstance) evt.getSource();
            persist(instance);
        }
    }

    private void add(final BambooInstance instance) {
        if (instance != null) {
            instance.addPropertyChangeListener(this);
            lookupContext.add(instance);
            buildStatusWatcher.addInstance(instance);

            putInMap(instance);
        }
    }

    private BambooInstanceProperties loadInstanceProperties(final String name) {
        BambooInstanceProperties props = new BambooInstanceProperties(instancesPrefs());
        props.loadPreferences(name);
        return props;
    }

    private void putInMap(BambooInstance instance) {
        instanceMap.put(instance.getName(), instance.getUrl());
    }

    private void remove(final BambooInstance instance) {
        if (instance != null) {
            instance.removePropertyChangeListener(this);
            lookupContext.remove(instance);
            buildStatusWatcher.removeInstance(instance);

            instanceMap.remove(instance.getName());
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
    public boolean existsInstanceUrl(String url) {
        if (isNotBlank(url)) {
            return instanceMap.containsValue(url);
        }

        return false;
    }

    @Override
    public boolean existsInstanceName(final String name) {
        if (isNotBlank(name)) {
            return instanceMap.containsKey(name);
        }

        return false;
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
            BambooInstanceProperties props = loadInstanceProperties(name);
            //create a instance with a client anyway since the stored properties must have been valid
            instance = new DefaultBambooInstance(props);

            putInMap(instance);
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
