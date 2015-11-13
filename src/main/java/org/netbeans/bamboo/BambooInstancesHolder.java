package org.netbeans.bamboo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Deprecated
final class BambooInstancesHolder {

    private static class InstanceHolder {

        private static final BambooInstancesHolder INSTANCE = new BambooInstancesHolder();
    }

    private final List<BambooChangeListener> listeners = new ArrayList<BambooChangeListener>();

    private final Map<String, BambooInstanceable> instances = new HashMap<String, BambooInstanceable>();

    private BambooInstancesHolder() {
    }

    static BambooInstancesHolder getDefault() {
        return InstanceHolder.INSTANCE;
    }

    void addChangeListener(final BambooChangeListener listener) {
        listeners.add(listener);
    }

    void addInstance(final String name, final String url) {
        instances.put(url, new BambooInstanceImpl(name, url));
        fireChangeListeners();
    }

    void removeInstance(final String url) {
        instances.remove(url);
        fireChangeListeners();
    }

    Collection<BambooInstanceable> getInstances() {
        return instances.values();
    }

    private void fireChangeListeners() {
        ArrayList<BambooChangeListener> tempList;

        synchronized (listeners) {
            tempList = new ArrayList<BambooChangeListener>(listeners);
        }

        for (final BambooChangeListener l : tempList) {
            l.onInstancesChanged();
        }
    }
}
