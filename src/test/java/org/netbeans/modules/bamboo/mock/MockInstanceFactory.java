package org.netbeans.modules.bamboo.mock;

import org.netbeans.modules.bamboo.glue.BambooInstance;
import org.netbeans.modules.bamboo.glue.InstanceValues;
import org.netbeans.modules.bamboo.rest.BambooInstanceProduceable;

import org.openide.util.lookup.ServiceProvider;


/**
 * @author spindizzy
 */
@ServiceProvider(service = BambooInstanceProduceable.class, position = 10)
public class MockInstanceFactory implements BambooInstanceProduceable {
    private BambooInstanceProduceable delegate;

    @Override
    public BambooInstance create(final InstanceValues values) {
        if (delegate != null) {
            return delegate.create(values);
        }

        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setDelegate(final BambooInstanceProduceable delegate) {
        this.delegate = delegate;
    }
}
