package org.netbeans.modules.bamboo.ui.notification;

import org.netbeans.modules.bamboo.glue.BuildStatusNotifyFactory;
import org.netbeans.modules.bamboo.glue.BuildStatusNotifyable;
import org.netbeans.modules.bamboo.glue.ProjectsProvideable;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author spindizzy
 */
@ServiceProvider(service = BuildStatusNotifyFactory.class, position = 1)
public class DefaultBuildStatusNotifyFactory implements BuildStatusNotifyFactory{

    @Override
    public BuildStatusNotifyable create(ProjectsProvideable projectsProvider) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
