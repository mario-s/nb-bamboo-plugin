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
package org.netbeans.modules.bamboo.client.rest.call;

import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.netbeans.modules.bamboo.model.rcp.InstanceValues;

import static java.util.Collections.emptyMap;

/**
 * This class keeps the necessary values for an Api call together.
 * @author Mario Schroeder
 */
@Getter
@RequiredArgsConstructor
final class CallParameters<T> {
    private final Class<T> responseClass;
    private final InstanceValues values;
    @Setter
    private boolean json; //if the path ends with json it is true
    @Setter
    private String path = "";
    @Setter
    private Map<String, String> parameters =  emptyMap();     
    
}
