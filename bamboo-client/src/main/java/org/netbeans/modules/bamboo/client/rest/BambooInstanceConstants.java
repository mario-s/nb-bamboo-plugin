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
package org.netbeans.modules.bamboo.client.rest;

/**
 *
 * @author Mario Schroeder
 */
final class BambooInstanceConstants {

    private BambooInstanceConstants() {}
    
    static final String INSTANCE_USER = "username";
    
    static final String INSTANCE_PASSWORD = "password";

    /**
     * preferred jobs for the instance, list of job names, separated by |
     */
    static final String INSTANCE_PREF_JOBS = "pref_jobs";

    /**
     * Nonsalient plans for the instance, list of plan keys, separated by /
     */
    static final String INSTANCE_SUPPRESSED_PLANS = "suppressed_plans";

    static final String INSTANCE_PERSISTED = "persisted";

    /**
     * True value, e.g. for {@link #INSTANCE_PERSISTED} key.
     */
    static final String TRUE = "true";
    /**
     * False value, e.g. for {@link #INSTANCE_PERSISTED} key.
     */
    static final String FALSE = "false";
    
}
