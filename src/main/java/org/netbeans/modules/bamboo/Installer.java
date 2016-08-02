package org.netbeans.modules.bamboo;

import org.netbeans.modules.bamboo.glue.BambooInstance;
import org.netbeans.modules.bamboo.glue.BuildProject;
import org.netbeans.modules.bamboo.glue.InstanceManageable;
import org.netbeans.modules.bamboo.glue.ProjectsProvideable;
import org.netbeans.modules.bamboo.rest.BambooServiceAccessable;

import static org.openide.util.Lookup.getDefault;

import org.openide.windows.OnShowing;

import java.util.Collection;


@OnShowing
public final class Installer implements Runnable {
    @Override
    public void run() {
        InstanceManageable manager = getDefault().lookup(InstanceManageable.class);
        Collection<BambooInstance> instances = manager.loadInstances();

        if (!instances.isEmpty()) {
            BambooServiceAccessable client = getDefault().lookup(BambooServiceAccessable.class);

            instances.parallelStream().forEach(instance -> {
                    Collection<BuildProject> projects = client.getProjects(instance);

                    ((ProjectsProvideable) instance).setProjects(projects);

                    manager.addInstance(instance);
                });
        }
    }
}
