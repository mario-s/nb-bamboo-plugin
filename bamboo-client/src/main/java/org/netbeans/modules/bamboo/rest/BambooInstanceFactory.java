package org.netbeans.modules.bamboo.rest;

import org.netbeans.modules.bamboo.glue.BambooServiceAccessable;
import org.netbeans.modules.bamboo.glue.BambooInstanceProduceable;
import org.netbeans.modules.bamboo.model.InstanceValues;
import org.netbeans.modules.bamboo.model.VersionInfo;

import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;

import java.util.Collection;
import java.util.Optional;
import org.netbeans.modules.bamboo.glue.BambooClientProduceable;
import org.netbeans.modules.bamboo.model.BambooInstance;
import org.netbeans.modules.bamboo.model.ProjectVo;

import static java.util.Optional.empty;
import static java.util.Optional.of;

/**
 */
@ServiceProvider(service = BambooInstanceProduceable.class)
public class BambooInstanceFactory implements BambooInstanceProduceable {

    private final BambooClientProduceable clientProducer;

    public BambooInstanceFactory() {
        this.clientProducer = Lookup.getDefault().lookup(BambooClientProduceable.class);
    }

    @Override
    public Optional<BambooInstance> create(final InstanceValues values) {
        Optional<BambooInstance> optInstance = empty();
        Optional<BambooServiceAccessable> optClient = clientProducer.newClient(values);
        if(optClient.isPresent()){
            BambooServiceAccessable client = optClient.get();
            DefaultBambooInstance instance = new DefaultBambooInstance(values);

            VersionInfo info = client.getVersionInfo();
            instance.setVersionInfo(info);

            Collection<ProjectVo> projects = client.getProjects();
            instance.setChildren(projects);
            
            optInstance = of(instance);
        }

        return optInstance;
    }
}
