package org.netbeans.modules.bamboo;

import org.netbeans.modules.bamboo.glue.BambooInstance;
import org.netbeans.modules.bamboo.glue.InstanceManageable;
import org.openide.util.Task;
import org.openide.util.TaskListener;

/**
 *
 * @author spindizzy
 */
final class SyncTaskListener implements TaskListener {
    
    private final InstanceManageable manager;
    private final BambooInstance instance;

    SyncTaskListener(final InstanceManageable manager, final BambooInstance instance) {
        this.manager = manager;
        this.instance = instance;
    }

    @Override
    public void taskFinished(final Task task) {
        if (task.isFinished()) {
            manager.addInstance(instance);
        }
    }
}
