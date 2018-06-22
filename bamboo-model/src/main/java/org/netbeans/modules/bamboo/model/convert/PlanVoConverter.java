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
package org.netbeans.modules.bamboo.model.convert;

import org.netbeans.modules.bamboo.model.rcp.PlanVo;
import org.netbeans.modules.bamboo.model.rest.Plan;

/**
 *
 * @author Mario Schroeder
 */
public class PlanVoConverter extends AbstractVoConverter<Plan, PlanVo> {
    
    private final String serverUrl;

    public PlanVoConverter(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    @Override
    public PlanVo convert(Plan src) {
        PlanVo target = new PlanVo(src.getKey(), src.getName());
        
        copyProperties(src, target);
        
        target.setServerUrl(serverUrl);

        return target;
    }
    
}
