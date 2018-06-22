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
package org.netbeans.modules.bamboo.ui.actions;

/**
 *
 * @author Mario Schroeder
 */
public interface ActionConstants {
    
     /**
     * The name of the module.
     */
    String MODULE_NAME = "org-netbeans-modules-bamboo";

    /**
     * Path used to load actions for the server instance. A
     * {@code BambooInstance} object should be in the context lookup. May be
     * used e.g. for the context menu of an instance node.
     */
    String ACTION_PATH = MODULE_NAME + "/Actions/instance";
    
    String PLAN_ACTION_PATH = MODULE_NAME + ACTION_PATH + "Plan";
    
    String COMMON_ACTION_PATH = MODULE_NAME + ACTION_PATH + "Common";
    
}
