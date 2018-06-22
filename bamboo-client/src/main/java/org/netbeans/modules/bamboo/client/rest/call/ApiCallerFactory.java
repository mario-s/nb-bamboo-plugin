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
import lombok.AllArgsConstructor;
import org.netbeans.modules.bamboo.model.rcp.InstanceValues;

import static org.netbeans.modules.bamboo.client.glue.RestResources.JSON_PATH;

/**
 * This factory produces {@link ApiCaller} and subclasses.
 *
 * @author Mario Schroeder
 */
@AllArgsConstructor
public class ApiCallerFactory {

    private final InstanceValues values;

    public ApiCallable newCaller(Class clazz, final String path) {
        return new ApiCaller<>(create(clazz, path));
    }

    public ApiCallable newCaller(Class clazz, final String path, final Map<String, String> params) {
        return new ApiCaller<>(create(clazz, path, params));
    }

    public ApiCallRepeatable newRepeatCaller(Class clazz, final String path) {
        return new ApiCallRepeater<>(create(clazz, path));
    }

    public ApiCallRepeatable newRepeatCaller(Class clazz, final String path, final Map<String, String> params) {
        return new ApiCallRepeater<>(create(clazz, path, params));
    }

    private CallParameters create(Class clazz, String path, Map<String, String> params) {
        CallParameters callParams = create(clazz, path);
        callParams.setParameters(params);
        return callParams;
    }

    /**
     * This method creates a new instance of {@link CallParameters}. If the given path ends with json, it is a 
     * request to a json resource.
     * @param clazz the class tu use
     * @param path REST path
     * @return a new instance.
     */
    private CallParameters create(Class clazz, final String path) {
        CallParameters params = new CallParameters(clazz, values);
        params.setPath(path);
        params.setJson(path.endsWith(JSON_PATH));
        return params;
    }

}
