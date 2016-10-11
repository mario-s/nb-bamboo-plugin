package org.netbeans.modules.bamboo.glue;

import org.netbeans.modules.bamboo.model.BambooInstance;
import org.openide.util.Lookup;
import org.openide.util.lookup.InstanceContent;

import java.util.Collection;


/**
 * @author spindizzy
 */
public interface InstanceManageable extends Lookup.Provider {
    InstanceContent getContent();

    void addInstance(final BambooInstance instance);

    void removeInstance(final String name);

    void removeInstance(final BambooInstance instance);

    boolean existsInstance(final String name);

    Collection<BambooInstance> loadInstances();

    void persist(BambooInstance instance);
}
