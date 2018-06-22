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
package org.netbeans.modules.bamboo.model.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Mario Schroeder
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectsResponse extends AbstractResponse<Project> {
    private Projects projects;

    @Override
    public Collection<Project> asCollection() {
        Set<Project> coll = new HashSet<>();
        if(projects != null){
            coll.addAll(projects.getProject());
        }
        return coll;
    }

    @Override
    protected Metrics getMetrics() {
        return projects;
    }
}
