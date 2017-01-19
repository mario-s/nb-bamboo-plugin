package org.netbeans.modules.bamboo.mock;

import org.netbeans.modules.bamboo.model.rcp.BambooInstance;
import org.netbeans.modules.bamboo.client.glue.BuildStatusWatchable;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Mario Schroeder
 */
@ServiceProvider(service = BuildStatusWatchable.class, position = 10)
public class MockBuildStateWatcher implements BuildStatusWatchable {

    private BuildStatusWatchable delegate;

    @Override
    public void addInstance(BambooInstance projectsProvider) {
        if (delegate != null) {
            delegate.addInstance(projectsProvider);
        } else {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    @Override
    public void removeInstance(BambooInstance projectsProvider) {
        if (delegate != null) {
            delegate.removeInstance(projectsProvider);
        } else {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    public void setDelegate(BuildStatusWatchable delegate) {
        this.delegate = delegate;
    }
}
