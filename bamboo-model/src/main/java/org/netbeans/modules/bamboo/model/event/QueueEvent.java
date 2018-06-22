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
package org.netbeans.modules.bamboo.model.event;

import javax.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.netbeans.modules.bamboo.model.rcp.PlanVo;

/**
 * This event is fired when a build was triggered manual.
 * @author Mario Schroeder
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueueEvent {
    private PlanVo plan;
    private Response response;
}
