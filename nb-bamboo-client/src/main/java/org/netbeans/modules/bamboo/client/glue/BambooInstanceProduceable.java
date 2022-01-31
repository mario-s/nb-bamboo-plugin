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

import org.netbeans.modules.bamboo.model.rcp.InstanceValues;
import org.netbeans.modules.bamboo.model.rcp.BambooInstance;
import java.util.Optional;


/**
 * This interface can be used for implementation which produce a new {@link BambooInstance}.
 */
public interface BambooInstanceProduceable {
    
    /**
     * Creates a new instance if the given values are for an existing server or empty if the url can not be reached.
     * @param values required values
     * @return a new instance or empty.
     */
    Optional<BambooInstance> create(InstanceValues values);
    
}
