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
 * This interface contains parameters which can be added to a rest call to extend the content of entities.
 * 
 * @author Mario Schroeder
 */
public final class ExpandParameter {
    /**expand parameter key*/
    public static final String EXPAND = "expand";

    /**expand the plans of each project*/
    public static final String PROJECT_PLANS = "projects.project.plans.plan";
    
    /**expand the comments for a build result*/
    public static final String RESULT_COMMENTS = "results.result.comments";
    
}
