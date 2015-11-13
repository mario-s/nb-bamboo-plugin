package org.netbeans.bamboo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.netbeans.bamboo.BambooChangeListener;
import org.netbeans.bamboo.BambooInstance;

final class BambooManagerImpl {

    private static class InstanceHolder {

        private static final BambooManagerImpl INSTANCE = new BambooManagerImpl();
    }

    private final List<BambooChangeListener> listeners = new ArrayList<BambooChangeListener>();

    private final Map<String, BambooInstance> instances = new HashMap<String, BambooInstance>();

    private BambooManagerImpl() {
    }

    public static BambooManagerImpl getDefault() {
        return InstanceHolder.INSTANCE;
    }

    public void addChangeListener(final BambooChangeListener listener) {
        listeners.add(listener);
    }

    public void addInstance(final String name, final String url) {
        instances.put(url, new BambooInstanceImpl(name, url));
        fireChangeListeners();
    }

    public void removeInstance(final String url) {
        instances.remove(url);
        fireChangeListeners();
    }

    public Collection<BambooInstance> getInstances() {
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
