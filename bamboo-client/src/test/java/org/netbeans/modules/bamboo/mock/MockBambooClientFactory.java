package org.netbeans.modules.bamboo.mock;

import java.util.Optional;
import org.openide.util.lookup.ServiceProvider;

import org.netbeans.modules.bamboo.glue.BambooClientProduceable;
import org.netbeans.modules.bamboo.glue.BambooServiceAccessable;
import org.netbeans.modules.bamboo.model.InstanceValues;

import static java.util.Optional.empty;

/**
 * @author spindizzy
 */
@ServiceProvider(service = BambooClientProduceable.class, position = 10)
public class MockBambooClientFactory implements BambooClientProduceable {
    private BambooClientProduceable delegate;

    @Override
    public Optional<BambooServiceAccessable> newClient(InstanceValues values) {
       if(delegate != null){
           return delegate.newClient(values);
       }
       return empty();
    }

    public void setDelegate(BambooClientProduceable delegate) {
        this.delegate = delegate;
    }
}
