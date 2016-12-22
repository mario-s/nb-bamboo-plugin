package org.netbeans.modules.bamboo.ui.notification;

import java.util.HashMap;
import java.util.Map;
import org.netbeans.modules.bamboo.model.BambooInstance;
import org.openide.util.lookup.ServiceProvider;
import org.netbeans.modules.bamboo.client.glue.BuildStatusWatchable;

/**
 * This class observes the plans of all the instances.
 * 
 * @author spindizzy
 */
@ServiceProvider(service = BuildStatusWatchable.class, position = 1)
public class DefaultBuildStatusWatcher implements BuildStatusWatchable {
    
    private final Map<BambooInstance, PlanResultNotify> notifiers;

    public DefaultBuildStatusWatcher() {
        this.notifiers = new HashMap<>();
    }

    @Override
    public void addInstance(BambooInstance instance) {
        notifiers.put(instance, new PlanResultNotify(instance));
    }

    @Override
    public void removeInstance(BambooInstance instance) {
        notifiers.remove(instance);
    }

    Map<BambooInstance, PlanResultNotify> getNotifiers() {
        return notifiers;
    }
    
}
