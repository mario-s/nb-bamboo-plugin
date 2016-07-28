package org.netbeans.modules.bamboo.rest;

import org.netbeans.modules.bamboo.glue.InstanceValues;
import static org.netbeans.modules.bamboo.rest.BambooInstanceConstants.*;

import org.openide.util.Exceptions;
import org.openide.util.RequestProcessor;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;


/**
 * Instance properties for Bamboo instance.
 */
public class BambooInstanceProperties extends HashMap<String, String> {
    /** Use serialVersionUID for interoperability. */
    private static final long serialVersionUID = 1L;

    private static final RequestProcessor RP = new RequestProcessor(BambooInstanceProperties.class);

    private static final Logger LOG = Logger.getLogger(BambooInstanceProperties.class.getName());

    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    private final Preferences preferences;

    public BambooInstanceProperties(final Preferences preferences) {
        this.preferences = preferences;
    }

    /**
     * Puts all the values into the Properties and updates preferences.
     *
     * @param values {@link InstanceValues}
     */
    public void copyProperties(final InstanceValues values) {
        put(INSTANCE_NAME, values.getName());
        put(INSTANCE_URL, values.getUrl());
        put(INSTANCE_SYNC, Integer.toString(values.getSyncInterval()));
        put(INSTANCE_USER, values.getUsername());
        put(INSTANCE_PASSWORD, new String(values.getPassword()));
    }

    /**
     * Loads the preferences for the instance.
     *
     * @param instanceName
     */
    public void loadPreferences(final String instanceName) {
        putAndFireChange(INSTANCE_NAME, instanceName);
        BambooInstanceProperties.this.loadPreferences();
    }

    @Override
    public final String put(final String key, final String value) {
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

    public List<PropertyChangeListener> getCurrentListeners() {
        return Arrays.asList(pcs.getPropertyChangeListeners());
    }

    public static List<String> split(final String prop) {
        if ((prop != null) && (prop.trim().length() > 0)) {
            String[] escaped = prop.split("(?<!/)/(?!/)");
            List<String> list = new ArrayList<>(escaped.length);

            for (String e : escaped) {
                list.add(e.replace("//", "/"));
            }

            return list;
        } else {
            return Collections.<String>emptyList();
        }
    }

    public static String join(final List<String> pieces) {
        StringBuilder b = new StringBuilder();

        for (String piece : pieces) {
            assert !piece.startsWith("/") && !piece.endsWith("/") : piece;

            String escaped = piece.replace("/", "//");

            if (b.length() > 0) {
                b.append('/');
            }

            b.append(escaped);
        }

        return b.toString();
    }

    /**
     * Get Preferences that this properties use as persistent storage.
     */
    public Preferences getPreferences() {
        String nodeName = getNodeName();

        if (nodeName != null) {
            return preferences.node(nodeName);
        } else {
            return null;
        }
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
        String name = get(INSTANCE_NAME);

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
                        if (INSTANCE_NAME.equals(key)) {
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
            LOG.info(ex.getMessage());
        }
    }

    @Override
    public void clear() {
        super.clear();

        try {
            preferences.removeNode();
            preferences.flush();
        } catch (BackingStoreException ex) {
            LOG.info(ex.getMessage());
        }
    }
}
