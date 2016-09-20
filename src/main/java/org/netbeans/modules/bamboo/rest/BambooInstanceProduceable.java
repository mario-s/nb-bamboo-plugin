package org.netbeans.modules.bamboo.rest;

import org.netbeans.modules.bamboo.glue.BambooInstance;
import org.netbeans.modules.bamboo.glue.InstanceValues;


/**
 */
public interface BambooInstanceProduceable {
    BambooInstance create(InstanceValues values);
}
