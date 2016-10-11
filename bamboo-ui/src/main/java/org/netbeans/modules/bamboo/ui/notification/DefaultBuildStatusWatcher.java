package org.netbeans.modules.bamboo.ui.notification;

import java.util.HashMap;
import java.util.Map;
import org.netbeans.modules.bamboo.model.BambooInstance;
import org.openide.util.lookup.ServiceProvider;
import org.netbeans.modules.bamboo.glue.BuildStatusWatchable;

/**
 *
 * @author spindizzy
 */
@ServiceProvider(service = BuildStatusWatchable.class, position = 1)
public class DefaultBuildStatusWatcher implements BuildStatusWatchable {
    
    private final Map<BambooInstance, BuildResultNotify> notifiers;

    public DefaultBuildStatusWatcher() {
        this.notifiers = new HashMap<>();
    }

    @Override
    public void addInstance(BambooInstance instance) {
        notifiers.put(instance, new BuildResultNotify(instance));
    }

    @Override
    public void removeInstance(BambooInstance instance) {
        notifiers.remove(instance);
    }
    
}
