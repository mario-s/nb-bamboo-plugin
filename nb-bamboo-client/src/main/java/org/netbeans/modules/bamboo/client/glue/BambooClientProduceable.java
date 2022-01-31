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

import java.util.Optional;
import org.netbeans.modules.bamboo.model.rcp.InstanceValues;

/**
 * Create a new client for a bamboo instance.
 * @author Mario Schroeder
 */
public interface BambooClientProduceable {
    
    /**
     * This method supplies a new client when the server for the given arguments can be accessed.
     * If the server is not avialable the result is empty.
     * @param values the necessary values to access the server.
     * @return an {@link Optional} of {@link BambooClient}.
     */
    Optional<BambooClient> newClient(InstanceValues values);
}
