package org.netbeans.modules.bamboo.model;

import org.netbeans.modules.bamboo.BambooInstance;
import org.netbeans.modules.bamboo.InstanceValues;

/**
 *
 */
public interface BambooInstanceProduceable {
    
    BambooInstance create(InstanceValues values);
}
