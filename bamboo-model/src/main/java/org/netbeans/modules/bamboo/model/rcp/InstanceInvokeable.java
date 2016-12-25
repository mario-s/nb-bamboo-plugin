package org.netbeans.modules.bamboo.model.rcp;

import java.util.function.Consumer;

/**
 * This interface provides methods to invoke another method of the instance.
 * @author spindizzy
 */
public interface InstanceInvokeable extends Availability{
    
    void invoke(final Consumer<BambooInstance> action);
}
