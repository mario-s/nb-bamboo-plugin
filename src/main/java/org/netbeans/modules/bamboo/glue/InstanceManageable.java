package org.netbeans.modules.bamboo.glue;

import org.openide.util.Lookup;
import org.openide.util.lookup.InstanceContent;


/**
 * @author spindizzy
 */
public interface InstanceManageable extends Lookup.Provider {
    InstanceContent getContent();

    void addInstance(final DefaultInstanceValues values);

    void removeInstance(final BambooInstance instance);

    boolean existsInstance(final String name);

    void loadInstances();
}
