package org.netbeans.modules.bamboo.rest;

import org.netbeans.modules.bamboo.glue.InstanceValues;
import org.netbeans.modules.bamboo.glue.VersionInfo;

import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;

import java.util.Collection;
import org.netbeans.modules.bamboo.glue.BambooInstance;
import org.netbeans.modules.bamboo.model.ProjectVo;


/**
 */
@ServiceProvider(service = BambooInstanceProduceable.class)
public class BambooInstanceFactory implements BambooInstanceProduceable {
    private BambooServiceAccessable instanceAccessor;

    public BambooInstanceFactory() {
        initClient();
    }

    private void initClient() {
        this.instanceAccessor = Lookup.getDefault().lookup(BambooServiceAccessable.class);
    }

    @Override
    public BambooInstance create(final InstanceValues values) {
        DefaultBambooInstance instance = new DefaultBambooInstance(values);

        VersionInfo info = instanceAccessor.getVersionInfo(values);
        instance.setVersionInfo(info);

        Collection<ProjectVo> projects = instanceAccessor.getProjects(values);

        instance.setProjects(projects);

        return instance;
    }
}
