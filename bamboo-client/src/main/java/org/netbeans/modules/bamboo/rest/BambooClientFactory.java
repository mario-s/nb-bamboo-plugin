package org.netbeans.modules.bamboo.rest;

import java.util.Optional;
import org.netbeans.modules.bamboo.glue.BambooClientProduceable;
import org.netbeans.modules.bamboo.glue.BambooServiceAccessable;
import org.netbeans.modules.bamboo.model.InstanceValues;
import org.openide.util.lookup.ServiceProvider;

import static java.util.Optional.empty;
import static java.util.Optional.of;

/**
 *
 * @author spindizzy
 */
@ServiceProvider(service = BambooClientProduceable.class)
public class BambooClientFactory implements BambooClientProduceable {

    private final HttpUtility httpUtility;

    public BambooClientFactory() {
        this(new HttpUtility());
    }

    BambooClientFactory(HttpUtility httpUtility) {
        this.httpUtility = httpUtility;
    }

    @Override
    public Optional<BambooServiceAccessable> newClient(InstanceValues values) {
        Optional<BambooServiceAccessable> opt = empty();
        String url = values.getUrl();
        if (httpUtility.exists(url)) {
            opt = of(new BambooClient(values));
        }
        return opt;
    }
}
