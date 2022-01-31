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
package org.netbeans.modules.bamboo.client.glue;

/**
 * Paths to REST Resources
 * 
 * @author Mario Schroeder
 */
public final class RestResources {
    
    public static final String JSON = ".json";
    public static final String PROJECTS = "/project" + JSON;
    public static final String PLANS = "/plan" + JSON;
    public static final String INFO = "/info" + JSON;
    public static final String RESULTS = "/result" + JSON;
    public static final String QUEUE = "/queue/%s";
    public static final String RESULT = "/result/%s";

    private RestResources() {
        // nothing to see here
    }
}
