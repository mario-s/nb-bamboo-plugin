package org.netbeans.modules.bamboo.model;

import org.netbeans.modules.bamboo.glue.PlansProvideable;
import org.netbeans.modules.bamboo.glue.InstanceValues;

/**
 *
 */
public interface BambooInstanceProduceable {
    
    PlansProvideable create(InstanceValues values);
}
