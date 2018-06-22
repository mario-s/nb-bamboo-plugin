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

/**
 * This enum contains values for properties which change during an update of the model.
 * 
 * @author Mario Schroeder
 */
public enum ModelChangedValues {
    
    /**
     * This value indicates that the availability of the server has changed.
     */
    Available("available"),
    
    /**
     * This value indicates that sychronizing just started or stoped.
     */
    Synchronizing("synchronizing"), 
    /**
     * This value is associated with a change of all projects.
     */
    Projects("projects"), 
    /**
     * This value is associated with a change of all plans.
     */
    Plans("plans"), 
    /**
     * This value is associated with a change of one plan.
     */
    Plan("plan"), 
    /**
     * This values is fired when a the notification should be ignored or not.
     */
    Silent("silent"),
    /**
     * This value is associated with a change of the result of one plan.
     */
    Result("result");

    private ModelChangedValues(String value){
        this.value = value;
                
    }
    
    private final String value;

    @Override
    public String toString() {
        return value;
    }
    
}
