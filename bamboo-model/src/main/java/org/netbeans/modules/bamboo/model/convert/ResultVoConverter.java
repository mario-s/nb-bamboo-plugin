package org.netbeans.modules.bamboo.model.convert;

import org.netbeans.modules.bamboo.model.rcp.ResultVo;
import org.netbeans.modules.bamboo.model.rest.Result;

/**
 *
 * @author spindizzy
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
