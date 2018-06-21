package org.netbeans.modules.bamboo.client.rest;

import org.netbeans.modules.bamboo.client.glue.HttpUtility;
import java.util.Optional;
import org.netbeans.modules.bamboo.client.glue.BambooClientProduceable;
import org.netbeans.modules.bamboo.model.rcp.InstanceValues;
import org.openide.util.lookup.ServiceProvider;

import static java.util.Optional.empty;
import static java.util.Optional.of;

import org.netbeans.modules.bamboo.client.glue.BambooClient;


/**
 *
 * @author Mario Schroeder
 */
@ServiceProvider(service = BambooClientProduceable.class)
public class DefaultBambooClientFactory implements BambooClientProduceable {
    
    private final HttpUtility httpUtility;

    public DefaultBambooClientFactory() {
        this(new HttpUtility());
    }

    DefaultBambooClientFactory(HttpUtility httpUtility) {
        this.httpUtility = httpUtility;
    }

    @Override
    public Optional<BambooClient> newClient(InstanceValues values) {
        Optional<BambooClient> opt = empty();
        String url = values.getUrl();
        if (httpUtility.exists(url)) {
            opt = of(new DefaultBambooClient(values, httpUtility));
        }
        return opt;
    }
}
