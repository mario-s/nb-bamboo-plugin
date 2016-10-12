package org.netbeans.modules.bamboo.glue;

import org.netbeans.modules.bamboo.model.InstanceValues;

/**
 * Create a new clint for a bamboo instance.
 * @author spindizzy
 */
public interface BambooClientProduceable {
    
    BambooServiceAccessable newClient(InstanceValues values);
}
