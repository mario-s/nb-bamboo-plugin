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
package org.netbeans.modules.bamboo.model;

import java.util.Arrays;
import java.util.List;

/**
 * The states of a plan, which is also used in result.
 * 
 * @author mario
 */
public enum LifeCycleState {
    Queued,
    Pending,
    NotBuilt,
    InProgress,
    Finished;

    public static List<LifeCycleState> getSuccessfulBambooLifeCycle() {
        return Arrays.asList(Queued, Pending, InProgress, Finished);
    }

    public static List<LifeCycleState> getFailedBambooLifeCycle() {
        return Arrays.asList(Queued, Pending, InProgress, NotBuilt);
    }
}
