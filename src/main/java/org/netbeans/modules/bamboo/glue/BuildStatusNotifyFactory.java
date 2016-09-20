package org.netbeans.modules.bamboo.glue;

/**
 * Interface for the factory to create a build state notify.
 * @author spindizzy
 */
public interface BuildStatusNotifyFactory {
    
    BuildStatusNotifyable create(ProjectsProvideable projectsProvider);
}
