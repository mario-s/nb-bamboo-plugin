package org.netbeans.modules.bamboo.glue;


/**
 * This interface can be used for implementation which produce a new {@link BambooInstance}.
 */
public interface BambooInstanceProduceable {
    BambooInstance create(InstanceValues values);
}
