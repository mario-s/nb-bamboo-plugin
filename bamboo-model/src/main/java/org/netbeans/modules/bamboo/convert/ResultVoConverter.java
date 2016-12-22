package org.netbeans.modules.bamboo.convert;

import org.netbeans.modules.bamboo.model.ResultVo;
import org.netbeans.modules.bamboo.model.rest.Result;

/**
 *
 * @author Mario
 */
public class ResultVoConverter implements VoConverter<Result, ResultVo> {

    @Override
    public ResultVo convert(Result src) {
        ResultVo target = new ResultVo(src.getKey());
        
        target.setNumber(src.getNumber());
        target.setBuildReason(src.getBuildReason());
        target.setState(src.getState());
        target.setLifeCycleState(src.getLifeCycleState());

        convertDates(src, target);

        return target;
    }

    private void convertDates(Result src, ResultVo target) {
        LocalDateTimeConverter dateConverter = new LocalDateTimeConverter();
        dateConverter.convert(src.getBuildStartedTime()).ifPresent((date) -> target.setBuildStartedTime(date));
        dateConverter.convert(src.getBuildCompletedTime()).ifPresent((date) -> target.setBuildCompletedTime(date));
        target.setBuildDurationInSeconds(src.getBuildDurationInSeconds());
    }

}
