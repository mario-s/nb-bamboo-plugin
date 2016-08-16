package org.netbeans.modules.bamboo;

import org.netbeans.modules.bamboo.glue.BambooInstance;
import org.netbeans.modules.bamboo.glue.InstanceManageable;

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
            instances.parallelStream().forEach(instance -> {
                    instance.synchronize();
                    manager.addInstance(instance);
                });
        }
    }
}
