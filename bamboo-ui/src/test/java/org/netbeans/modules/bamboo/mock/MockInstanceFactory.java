package org.netbeans.modules.bamboo.mock;

import java.util.Optional;
import org.netbeans.modules.bamboo.model.rcp.BambooInstance;
import org.netbeans.modules.bamboo.model.rcp.InstanceValues;
import org.netbeans.modules.bamboo.client.glue.BambooInstanceProduceable;

import org.openide.util.lookup.ServiceProvider;


/**
 * @author Mario Schroeder
 */
@ServiceProvider(service = BambooInstanceProduceable.class, position = 10)
public class MockInstanceFactory implements BambooInstanceProduceable {
    private BambooInstanceProduceable delegate;

    @Override
    public Optional<BambooInstance> create(final InstanceValues values) {
        if (delegate != null) {
            return delegate.create(values);
        }

        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setDelegate(final BambooInstanceProduceable delegate) {
        this.delegate = delegate;
    }
}
