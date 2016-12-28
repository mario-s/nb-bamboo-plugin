package org.netbeans.modules.bamboo.client.rest;

import org.netbeans.modules.bamboo.client.glue.BambooInstanceProduceable;
import org.netbeans.modules.bamboo.model.rcp.InstanceValues;
import org.netbeans.modules.bamboo.model.rcp.VersionInfo;

import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;

import java.util.Collection;
import java.util.Optional;
import org.netbeans.modules.bamboo.client.glue.BambooClientProduceable;
import org.netbeans.modules.bamboo.model.rcp.BambooInstance;
import org.netbeans.modules.bamboo.model.rcp.ProjectVo;

import static java.util.Optional.empty;
import static java.util.Optional.of;

import org.netbeans.modules.bamboo.client.glue.BambooClient;

/**
 */
@ServiceProvider(service = BambooInstanceProduceable.class)
public class DefaultBambooInstanceFactory implements BambooInstanceProduceable {

    private final BambooClientProduceable clientFactory;

    public DefaultBambooInstanceFactory() {
        this.clientFactory = Lookup.getDefault().lookup(BambooClientProduceable.class);
    }

    @Override
    public Optional<BambooInstance> create(final InstanceValues values) {
        Optional<BambooInstance> optInstance = empty();
        //create the client only when the url supplied by the values is valid
        Optional<BambooClient> optClient = clientFactory.newClient(values);
        if(optClient.isPresent()){
            AbstractBambooClient client = (AbstractBambooClient)optClient.get();
            DefaultBambooInstance instance = new DefaultBambooInstance(values, client);

            VersionInfo info = client.getVersionInfo();
            instance.setVersionInfo(info);

            Collection<ProjectVo> projects = client.getProjects();
            instance.setChildren(projects);
            
            optInstance = of(instance);
        }

        return optInstance;
    }
}