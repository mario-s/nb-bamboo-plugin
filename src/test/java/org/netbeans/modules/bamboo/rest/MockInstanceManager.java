package org.netbeans.modules.bamboo.rest;

import org.netbeans.modules.bamboo.glue.BambooInstance;
import org.netbeans.modules.bamboo.glue.InstanceManageable;
import org.netbeans.modules.bamboo.rest.DefaultInstanceManager;

import org.openide.util.Lookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.util.lookup.ServiceProvider;

import java.util.Collection;


/**
 * @author spindizzy
 */
@ServiceProvider(service = InstanceManageable.class, position = 10)
public class MockInstanceManager extends DefaultInstanceManager {
    private InstanceManageable delegate;

    @Override
    public void addInstance(final BambooInstance instance) {
        if (delegate != null) {
            delegate.addInstance(instance);
        } else {
            super.addInstance(instance);
        }
    }

    @Override
    public void removeInstance(final String name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removeInstance(final BambooInstance instance) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean existsInstance(final String name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Collection<BambooInstance> loadInstances() {
        return delegate.loadInstances();
    }

    public void setDelegate(final InstanceManageable delegate) {
        this.delegate = delegate;
    }
}
