package org.netbeans.modules.bamboo.rest;

import org.netbeans.modules.bamboo.glue.BambooClientProduceable;
import org.netbeans.modules.bamboo.glue.BambooServiceAccessable;
import org.netbeans.modules.bamboo.model.InstanceValues;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author spindizzy
 */
@ServiceProvider(service = BambooClientProduceable.class)
public class BambooClientFactory implements BambooClientProduceable{

    @Override
    public BambooServiceAccessable newClient(InstanceValues values) {
        return new BambooClient(values);
    }
}
