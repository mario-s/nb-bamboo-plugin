package org.netbeans.modules.bamboo.rest;

import org.netbeans.modules.bamboo.glue.InstanceValues;
import org.netbeans.modules.bamboo.glue.ResultsProvideable;

/**
 *
 */
public interface BambooInstanceProduceable {
    
    ResultsProvideable create(InstanceValues values);
}
