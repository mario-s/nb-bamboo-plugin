package org.netbeans.modules.bamboo.client.glue;

import org.netbeans.modules.bamboo.model.rcp.BambooInstance;
import org.openide.util.Lookup;
import org.openide.util.lookup.InstanceContent;

import java.util.Collection;

/**
 * This interface provides methods to to deal with {@link BambooInstance}.
 *
 * @author spindizzy
 */
public interface InstanceManageable extends Lookup.Provider {

    InstanceContent getContent();

    /**
     * This method adds a new instance to the manager.
     *
     * @param instance the given instance to save
     */
    void addInstance(final BambooInstance instance);

    /**
     * This method removes the instance from the manager and user's preferences.
     *
     * @param name the name of the instance to remove
     */
    void removeInstance(final String name);

    /**
     * This method removes the instance from the manager and user's preferences.
     *
     * @param instance the instance to remove
     */
    void removeInstance(final BambooInstance instance);

    /**
     * This method returns <code>true</code> when an instance with the given name exist in the user's preferences, if
     * not <code>false</code>.
     *
     * @param name the name of the instance
     * @return boolean
     */
    boolean existsInstance(final String name);

    /**
     * Load all persisted instances from the user's preferences.
     *
     * @return a collection of the instances
     */
    Collection<BambooInstance> loadInstances();
    
    /**
     * Persist the instance in the user's preferences.
     *
     * @param instance the instance to save
     */
    void persist(BambooInstance instance);
}
