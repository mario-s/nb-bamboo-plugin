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

import java.util.Objects;
import javax.ws.rs.core.Response;
import org.netbeans.modules.bamboo.model.rcp.PlanVo;

/**
 * This event is fired when a build was triggered manual.
 * @author Mario Schroeder
 */
public class QueueEvent {
    private PlanVo plan;
    private Response response;
    
    public QueueEvent() {
        this(null, null);
    }

    public QueueEvent(PlanVo plan, Response response) {
        this.plan = plan;
        this.response = response;
    }

    public PlanVo getPlan() {
        return plan;
    }

    public void setPlan(PlanVo plan) {
        this.plan = plan;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.plan);
        hash = 97 * hash + Objects.hashCode(this.response);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final QueueEvent other = (QueueEvent) obj;
        if (!Objects.equals(this.plan, other.plan)) {
            return false;
        }
        return Objects.equals(this.response, other.response);
    }
    
    public static QueueEventBuilder builder() {
        return new QueueEventBuilder();
    }
}
