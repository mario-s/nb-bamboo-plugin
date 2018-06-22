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
package org.netbeans.modules.bamboo.model.convert;

import org.netbeans.modules.bamboo.model.rcp.ResultVo;
import org.netbeans.modules.bamboo.model.rest.Result;

/**
 *
 * @author Mario Schroeder
 */
public class ResultVoConverter extends AbstractVoConverter<Result, ResultVo> {

    @Override
    public ResultVo convert(Result src) {
        ResultVo target = new ResultVo(src.getKey());
        
        copyProperties(src, target);

        convertDates(src, target);

        return target;
    }

    private void convertDates(Result src, ResultVo target) {
        toDate(src.getBuildStartedTime()).ifPresent((date) -> target.setBuildStartedTime(date));
        toDate(src.getBuildCompletedTime()).ifPresent((date) -> target.setBuildCompletedTime(date));
    }

}
