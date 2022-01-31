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

import java.util.function.Consumer;

/**
 * This interface provides methods to invoke another method of the instance.
 * @author Mario Schroeder
 */
public interface InstanceInvokeable extends Availability{
    
    /**
     * Invokes an action on the instance.
     * @param action an action that returns the BambooInstance.
     */
    void invoke(final Consumer<BambooInstance> action);
}
