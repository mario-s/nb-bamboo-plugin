/*
 * Copyright 2019 NetBeans.
 *
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
import org.apache.commons.lang3.builder.Builder;
import org.netbeans.modules.bamboo.model.rcp.PlanVo;

/**
 * Builder for {@link QueueEvent}.
 * 
 * @author Mario Schroeder
 */
public class QueueEventBuilder implements Builder<QueueEvent> {
    
    private PlanVo plan;
    private Response response;
    
    public QueueEventBuilder plan(PlanVo plan) {
        this.plan = plan;
        return this;
    }
    
    public QueueEventBuilder response(Response response) {
        this.response = response;
        return this;
    }

    @Override
    public QueueEvent build() {
        return new QueueEvent(plan, response);
    }
    
}
