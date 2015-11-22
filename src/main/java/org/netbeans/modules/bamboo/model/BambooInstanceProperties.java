package org.netbeans.modules.bamboo.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import static org.netbeans.modules.bamboo.model.BambooInstanceConstants.*;
import org.openide.util.Exceptions;
import org.openide.util.RequestProcessor;

/**
 * Instance properties for Bamboo instance
 */
public class BambooInstanceProperties extends HashMap<String, String> {

    private static final RequestProcessor RP = new RequestProcessor(
            BambooInstanceProperties.class);

    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    private final Preferences preferences;

    public BambooInstanceProperties(Preferences preferences) {
        this.preferences = preferences;
    }

    public void copyProperties(BambooInstance instance) {
        put(INSTANCE_NAME, instance.getName());
        put(INSTANCE_URL, instance.getUrl());
        put(INSTANCE_SYNC, "");
    }

    @Override
    public final synchronized String put(String key, String value) {
        String o = super.put(key, value);
        pcs.firePropertyChange(key, o, value);
        if (key.equals(INSTANCE_NAME)) {
            loadPreferences();
        }
        updatePreferences(key);
        return o;
    }

    @Override
    public synchronized String remove(Object key) {
        String o = super.remove((String) key);
        pcs.firePropertyChange((String) key, o, null);
        updatePreferences((String) key);
        return o;
    }

    public final boolean isPersisted() {
        String pers = get(INSTANCE_PERSISTED);
        return pers == null || TRUE.equals(pers);
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        pcs.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        pcs.removePropertyChangeListener(l);
    }

    public List<PropertyChangeListener> getCurrentListeners() {
        return Arrays.asList(pcs.getPropertyChangeListeners());
    }

    public static List<String> split(String prop) {
        if (prop != null && prop.trim().length() > 0) {
            String[] escaped = prop.split("(?<!/)/(?!/)");              //NOI18N
            List<String> list = new ArrayList<String>(escaped.length);
            for (String e : escaped) {
                list.add(e.replace("//", "/"));                         //NOI18N
            }
            return list;
        } else {
            return Collections.<String>emptyList();
        }
    }

    public static String join(List<String> pieces) {
        StringBuilder b = new StringBuilder();
        for (String piece : pieces) {
            assert !piece.startsWith("/") //NOI18N
                    && !piece.endsWith("/") : piece;                    //NOI18N
            String escaped = piece.replace("/", "//");                  //NOI18N
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
        if (name != null && !name.isEmpty()) {
            return simplifyServerLocation(name, true);
        } else {
            return null;
        }
    }

    private String simplifyServerLocation(String name, boolean forKey) {
        // http://deadlock.netbeans.org/hudson/ => deadlock.netbeans.org_hudson
        String display = name.replaceFirst("https?://", "").replaceFirst("/$",
                "");
        return forKey ? display.replaceAll("[/:]", "_") : display; // NOI18N
    }

    /**
     * Update persistent preferences in a background thread.
     */
    private void updatePreferences(final String... keys) {
        RP.post(new Runnable() {
            @Override
            public void run() {
                Preferences prefs = getPreferences();
                if (prefs != null) {
                    for (String key : keys) {
                        String val = get(key);
                        if (val == null) {
                            prefs.remove(key);
                        } else {
                            prefs.put(key, val);
                        }
                    }
                }
            }
        });
    }

    /**
     * Load preferences in background thread.
     */
    private void loadPreferences() {
        RP.post(new Runnable() {
            @Override
            public void run() {
                if (hasPreferences()) {
                    Preferences prefs = getPreferences();
                    if (prefs != null) {
                        try {
                            String[] keys = prefs.keys();
                            for (String key : keys) {
                                if (INSTANCE_NAME.equals(key)
                                        || INSTANCE_URL.equals(key)) {
                                    continue;
                                }
                                String val = prefs.get(key, null);
                                if (val != null) {
                                    put(key, val);
                                }
                            }
                        } catch (BackingStoreException ex) {
                            Exceptions.printStackTrace(ex);
                        }
                    }
                }
            }
        });
    }
}
