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
import com.fasterxml.jackson.annotation.JsonRootName;
import java.util.Collection;

import static java.util.Collections.emptyList;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 *
 * @author Mario Schroeder
 */
@EqualsAndHashCode
@JsonRootName(value = "project")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Project implements ServiceInfoProvideable{

    private String key;
    private Link link;
    private String name;
    private transient Plans plans;

    public Collection<Plan> plansAsCollection() {
        return (plans == null) ? emptyList() : plans.getPlan();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Plans getPlans() {
        return plans;
    }

    public void setPlans(Plans plans) {
        this.plans = plans;
    }


    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
