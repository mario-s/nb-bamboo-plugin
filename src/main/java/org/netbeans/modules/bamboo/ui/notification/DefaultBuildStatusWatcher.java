package org.netbeans.modules.bamboo.ui.notification;

import java.util.HashMap;
import java.util.Map;
import org.netbeans.modules.bamboo.glue.BambooInstance;
import org.openide.util.lookup.ServiceProvider;
import org.netbeans.modules.bamboo.glue.BuildStatusWatchable;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;

/**
 *
 * @author spindizzy
 */
@ServiceProvider(service = BuildStatusWatchable.class, position = 1)
public class DefaultBuildStatusWatcher implements BuildStatusWatchable, LookupListener{
    
    private final Map<BambooInstance, BuildResultNotify> notifiers;

    public DefaultBuildStatusWatcher() {
        this.notifiers = new HashMap<>();
    }

    @Override
    public void addInstance(BambooInstance projectsProvider) {
    }

    @Override
    public void resultChanged(LookupEvent ev) {
    }

    @Override
    public void removeInstance(BambooInstance projectsProvider) {
    }
    
}
