package org.netbeans.modules.bamboo;

import org.netbeans.modules.bamboo.model.BambooInstance;
import org.netbeans.modules.bamboo.glue.InstanceManageable;

import static org.openide.util.Lookup.getDefault;

import org.openide.util.Task;
import org.openide.util.TaskListener;

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
                    TaskListener listener = new SyncTaskListener(manager, instance);
                    Task task = instance.synchronize();
                    task.addTaskListener(listener);
                });
        }
    }
}
