package org.netbeans.modules.bamboo.ui.notification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.netbeans.modules.bamboo.model.PlanVo;
import org.netbeans.modules.bamboo.model.ResultVo;

/**
 * This class encapsulates the necessary objects for the message when the result of a plan has changed.
 * @author spindizzy
 */
@Getter
@AllArgsConstructor
final class BuildResult {
    private final PlanVo plan;
    private final ResultVo oldResult;
    private final ResultVo newResult;
}
