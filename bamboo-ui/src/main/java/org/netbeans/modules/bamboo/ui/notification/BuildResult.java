package org.netbeans.modules.bamboo.ui.notification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.netbeans.modules.bamboo.model.rcp.PlanVo;
import org.netbeans.modules.bamboo.model.rcp.ResultVo;

/**
 * This class encapsulates the necessary objects for the message when the result of a plan has changed.
 * @author Mario Schroeder
 */
@Getter
@AllArgsConstructor
final class BuildResult {
    private final PlanVo plan;
    private final ResultVo oldResult;
    private final ResultVo newResult;
}
