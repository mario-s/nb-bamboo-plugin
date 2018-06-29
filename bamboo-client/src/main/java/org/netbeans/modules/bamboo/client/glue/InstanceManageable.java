/* 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.netbeans.modules.bamboo.client.glue;

import org.netbeans.modules.bamboo.model.rcp.BambooInstance;
import org.openide.util.Lookup;
import org.openide.util.lookup.InstanceContent;

import java.util.Collection;

/**
 * This interface provides methods to to deal with {@link BambooInstance}.
 *
 * @author Mario Schroeder
 */
public interface InstanceManageable extends Lookup.Provider {
    
    /**
     * Get the content from this provider.
     * 
     * @return {@link InstanceContent}
     */
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
    boolean existsInstanceName(final String name);
    
    /**
     * This method returns <code>true</code> when an instance with the given url exist in the user's preferences, if
     * not <code>false</code>.
     *
     * @param url the url of the instance
     * @return boolean
     */
    boolean existsInstanceUrl(final String url);

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
