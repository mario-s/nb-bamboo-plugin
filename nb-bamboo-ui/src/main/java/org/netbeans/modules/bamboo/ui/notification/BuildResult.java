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
package org.netbeans.modules.bamboo.ui.notification;

import org.netbeans.modules.bamboo.model.rcp.PlanVo;
import org.netbeans.modules.bamboo.model.rcp.ResultVo;

/**
 * This class encapsulates the necessary objects for the message when the result of a plan has changed.
 * @author Mario Schroeder
 */
final class BuildResult {
    private final PlanVo plan;
    private final ResultVo oldResult;
    private final ResultVo newResult;

    public BuildResult(PlanVo plan, ResultVo oldResult, ResultVo newResult) {
        this.plan = plan;
        this.oldResult = oldResult;
        this.newResult = newResult;
    }

    public PlanVo getPlan() {
        return plan;
    }

    public ResultVo getOldResult() {
        return oldResult;
    }

    public ResultVo getNewResult() {
        return newResult;
    }
}
