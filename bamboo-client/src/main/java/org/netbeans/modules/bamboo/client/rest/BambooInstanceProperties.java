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


import static org.netbeans.modules.bamboo.client.rest.BambooInstanceConstants.*;

import org.openide.util.Exceptions;
import org.openide.util.RequestProcessor;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import lombok.extern.java.Log;

import static org.netbeans.modules.bamboo.client.glue.InstanceConstants.PROP_NAME;
import static org.netbeans.modules.bamboo.client.glue.InstanceConstants.PROP_SYNC_INTERVAL;
import static org.netbeans.modules.bamboo.client.glue.InstanceConstants.PROP_URL;

import org.netbeans.modules.bamboo.model.rcp.BambooInstance;

/**
 * Instance properties for Bamboo instance.
 */
@Log
public class BambooInstanceProperties extends HashMap<String, String> {

    /**
     * Use serialVersionUID for interoperability.
     */
    private static final long serialVersionUID = 1L;

    private static final RequestProcessor RP = new RequestProcessor(BambooInstanceProperties.class);

    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    private final transient Preferences preferences;

    public BambooInstanceProperties(final Preferences preferences) {
        this.preferences = preferences;
    }

    /**
     * Puts all the values into the Properties and updates preferences.
     *
     * @param instance {@link BambooInstance}
     */
    public void copyProperties(final BambooInstance instance) {
        put(PROP_NAME, instance.getName());
        put(PROP_URL, instance.getUrl());
        put(PROP_SYNC_INTERVAL, Integer.toString(instance.getSyncInterval()));
        put(INSTANCE_USER, instance.getUsername());
        put(INSTANCE_PASSWORD, new String(instance.getPassword()));
        put(INSTANCE_SUPPRESSED_PLANS, StringUtil.join(instance.getSuppressedPlans()));
    }

    /**
     * Loads the preferences for the instance.
     *
     * @param instanceName the name of the instance to load
     */
    public void loadPreferences(final String instanceName) {
        putAndFireChange(PROP_NAME, instanceName);
        loadPreferences();
    }

    @Override
    public String put(final String key, final String value) {
        String o = putAndFireChange(key, value);
        updatePreferences(key);

        return o;
    }

    private String putAndFireChange(final String key, final String value) {
        String o = super.put(key, value);
        pcs.firePropertyChange(key, o, value);

        return o;
    }

    @Override
    public synchronized String remove(final Object key) {
        String o = super.remove((String) key);
        pcs.firePropertyChange((String) key, o, null);
        updatePreferences((String) key);

        return o;
    }

    /**
     * This method returns <code>true</code> if the instance is persisted.
     * @return boolean
     */
    public final boolean isPersisted() {
        String pers = get(INSTANCE_PERSISTED);

        return (pers == null) || TRUE.equals(pers);
    }

    public void addPropertyChangeListener(final PropertyChangeListener l) {
        pcs.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(final PropertyChangeListener l) {
        pcs.removePropertyChangeListener(l);
    }

    /**
     * Returns all listeners which are currently registered.
     * @return a collection of property change listeners.
     */
    public List<PropertyChangeListener> getCurrentListeners() {
        return Arrays.asList(pcs.getPropertyChangeListeners());
    }

    /**
     * Get Preferences used by the properties as persistent storage, might return null.
     *
     * @return the {@link Preferences} for the instance
     */
    public Preferences getPreferences() {
        Preferences prefs = null;
        String nodeName = getNodeName();

        if (nodeName != null) {
            try {
                prefs = preferences.node(nodeName);
            } catch (IllegalStateException exc) {
                log.log(Level.FINE, exc.getMessage(), exc);
            }
        }

        return prefs;
    }

    /**
     * Check if there are existing preferences for this properties.
     */
    private boolean hasPreferences() {
        String nodeName = getNodeName();

        if (nodeName != null) {
            try {
                return preferences.nodeExists(nodeName);
            } catch (BackingStoreException ex) {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Get Preferences node name for this properties instance.
     */
    private String getNodeName() {
        String name = get(PROP_NAME);

        if ((name != null) && !name.isEmpty()) {
            return simplifyServerLocation(name, true);
        } else {
            return null;
        }
    }

    private String simplifyServerLocation(final String name, final boolean forKey) {
        // http://deadlock.netbeans.org/hudson/ => deadlock.netbeans.org_hudson
        String display = name.replaceFirst("https?://", "").replaceFirst("/$", "");

        return forKey ? display.replaceAll("[/:]", "_") : display;
    }

    /**
     * Update persistent preferences.
     */
    private void updatePreferences(final String... keys) {
        RP.post(() -> {
            Preferences prefs = getPreferences();

            if (prefs != null) {
                for (String key : keys) {
                    String val = get(key);

                    if (val == null) {
                        prefs.remove(key);
                    } else {
                        if (INSTANCE_PASSWORD.equals(key)) {
                            val = Encrypter.getInstance().encrypt(val);
                        }

                        prefs.put(key, val);
                    }
                }
            }
        });
    }

    /**
     * Load preferences.
     */
    private void loadPreferences() {
        if (hasPreferences()) {
            Preferences prefs = getPreferences();

            if (prefs != null) {
                try {
                    String[] keys = prefs.keys();

                    for (String key : keys) {
                        if (PROP_NAME.equals(key)) {
                            continue;
                        }

                        put(prefs, key);
                    }
                } catch (BackingStoreException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
        }
    }

    private void put(final Preferences prefs, final String key) {
        try {
            String val = prefs.get(key, null);

            if (val != null) {
                if (INSTANCE_PASSWORD.equals(key)) {
                    val = Encrypter.getInstance().decrypt(val);
                }

                put(key, val);
            }
        } catch (IllegalStateException ex) {
            log.info(ex.getMessage());
        }
    }

    @Override
    public void clear() {
        super.clear();

        try {
            preferences.removeNode();
            preferences.flush();
        } catch (BackingStoreException ex) {
            log.info(ex.getMessage());
        }
    }
}
