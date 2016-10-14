package org.netbeans.modules.bamboo.rest;

import java.util.Optional;
import org.netbeans.modules.bamboo.glue.BambooClientProduceable;
import org.netbeans.modules.bamboo.model.InstanceValues;
import org.openide.util.lookup.ServiceProvider;

import static java.util.Optional.empty;
import static java.util.Optional.of;

import org.netbeans.modules.bamboo.glue.BambooClient;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 *
 * @author spindizzy
 */
@ServiceProvider(service = BambooClientProduceable.class)
public class DefaultBambooClientFactory implements BambooClientProduceable {

    @Override
    public Optional<BambooClient> newClient(InstanceValues values) {
        Optional<BambooClient> opt = empty();
        String url = values.getUrl();
        if (isNotBlank(url)) {
            HttpUtility httpUtility = newUtility();
            if (httpUtility.exists(url)) {
                opt = of(new DefaultBambooClient(values, httpUtility));
            }
        }
        return opt;
    }

    HttpUtility newUtility() {
        return new HttpUtility();
    }
}
