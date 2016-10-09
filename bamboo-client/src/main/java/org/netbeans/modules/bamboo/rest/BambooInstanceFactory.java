package org.netbeans.modules.bamboo.rest;

import org.netbeans.modules.bamboo.glue.BambooServiceAccessable;
import org.netbeans.modules.bamboo.glue.BambooInstanceProduceable;
import org.netbeans.modules.bamboo.glue.InstanceValues;
import org.netbeans.modules.bamboo.model.VersionInfo;

import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;

import java.util.Collection;
import java.util.Optional;
import org.netbeans.modules.bamboo.glue.BambooInstance;
import org.netbeans.modules.bamboo.model.ProjectVo;

import static java.util.Optional.empty;
import static java.util.Optional.of;

/**
 */
@ServiceProvider(service = BambooInstanceProduceable.class)
public class BambooInstanceFactory implements BambooInstanceProduceable {

    private final BambooServiceAccessable instanceAccessor;

    private HttpUtility httpUtility;

    public BambooInstanceFactory() {
        this.instanceAccessor = Lookup.getDefault().lookup(BambooServiceAccessable.class);
        this.httpUtility = new HttpUtility();
    }


    @Override
    public Optional<BambooInstance> create(final InstanceValues values) {
        Optional<BambooInstance> opt = empty();
        if (httpUtility.exists(values.getUrl())) {
            DefaultBambooInstance instance = new DefaultBambooInstance(values);

            VersionInfo info = instanceAccessor.getVersionInfo(values);
            instance.setVersionInfo(info);

            Collection<ProjectVo> projects = instanceAccessor.getProjects(values);
            instance.setProjects(projects);
            
            opt = of(instance);
        }

        return opt;
    }
}
