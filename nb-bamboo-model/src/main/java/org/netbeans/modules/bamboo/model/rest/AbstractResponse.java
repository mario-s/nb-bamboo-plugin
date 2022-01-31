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

/**
 * @author Mario Schroeder
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class AbstractResponse<T> implements Responseable{
    private String expand;
    private Link link;

    public String getExpand() {
        return expand;
    }

    public void setExpand(String expand) {
        this.expand = expand;
    }

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }
    
    @Override
    public int getMaxResult() {
        Metrics metrics = getMetrics();
        return (metrics != null) ? metrics.getMaxResult() : 0;
    }

    @Override
    public int getSize() {
        Metrics metrics = getMetrics();
        return (metrics != null) ? metrics.getSize() : 0;
    }
    
    protected abstract Metrics getMetrics();
}
