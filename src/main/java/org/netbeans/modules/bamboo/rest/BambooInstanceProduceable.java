package org.netbeans.modules.bamboo.rest;

import org.netbeans.modules.bamboo.glue.InstanceValues;
import org.netbeans.modules.bamboo.glue.ProjectsProvideable;

/**
 *
 */
public interface BambooInstanceProduceable {
    
    ProjectsProvideable create(InstanceValues values);
}
