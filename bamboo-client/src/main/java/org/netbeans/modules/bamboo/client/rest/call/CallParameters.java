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
import org.netbeans.modules.bamboo.model.rcp.InstanceValues;

import static java.util.Collections.emptyMap;

/**
 * This class keeps the necessary values for an Api call together.
 *
 * @author Mario Schroeder
 */
final class CallParameters<T> {

    private final Class<T> responseClass;
    private final InstanceValues values;

    public CallParameters(Class<T> responseClass, InstanceValues values) {
        this.responseClass = responseClass;
        this.values = values;
    }

    private boolean json; //if the path ends with json it is true

    private String path = "";

    private Map<String, String> parameters = emptyMap();

    public Class<T> getResponseClass() {
        return responseClass;
    }

    public InstanceValues getValues() {
        return values;
    }

    public void setJson(boolean json) {
        this.json = json;
    }

    public boolean isJson() {
        return json;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

}
