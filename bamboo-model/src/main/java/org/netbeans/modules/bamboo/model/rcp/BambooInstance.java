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
package org.netbeans.modules.bamboo.model.rcp;

import org.openide.util.Task;


import java.io.Serializable;
import java.util.Collection;

import java.util.prefs.Preferences;
import org.openide.util.Lookup;

/**
 * This interface represents a bamboo server.
 *
 * @author Mario Schroeder
 */
public interface BambooInstance extends 
        InstanceValues, Lookup.Provider, Serializable, TraverseDown<ProjectVo> {

    /**
     * Get the informations to the version of the associated bamboo server.
     *
     * @return version as VersionInfo
     */
    VersionInfo getVersionInfo();
    
    Preferences getPreferences();

    /**
     * Removes the instance from the preferences.
     */
    void remove();

    /**
     * Synchonize this instance with the server. It may also fire start/stop events which can be consumed
     * to show progress.
     * 
     * @param showProgress if set to true events will be fired when synchonization starts and stops.
     *
     * @return the sychronize task
     */
    Task synchronize(boolean showProgress);
    

    /**
     * This method updates the synchronization interval and restart any scheduled synchronization tasks.
     *
     * @param minutes time in minutes
     */
    void updateSyncInterval(int minutes);
    
    /**
     * This method will queue the plan for a build on the server, and fires an event with the response code from the 
     * server.
     * 
     * @param plan the {@link PlanVo} to queue for the build.
     */
    void queue(PlanVo plan);
    
    /**
     * Persist the notification state of the plan.
     * @param plan the plan which is silent or not.
     */
    void updateNotify(PlanVo plan);
    
    /**
     * This method returns the keys for those plans which are silent.
     * @return a collection of the plan keys.
     */
    Collection<String> getSuppressedPlans();
    
     /**
     * This expands the given result.
     * The property which maps to the parameter will be replaced no mater if is present or not.
     * 
     * @param result the result where the changes should be set.
     * @param param the parameter to get more information from the server.
     */
    void expand(ResultVo result, ResultExpandParameter param);
}
