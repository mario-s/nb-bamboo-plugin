package org.netbeans.modules.bamboo.glue;

import java.util.Collection;
import org.openide.util.Lookup;
import org.openide.util.lookup.InstanceContent;


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
}
