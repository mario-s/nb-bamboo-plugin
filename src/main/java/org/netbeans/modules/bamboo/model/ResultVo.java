package org.netbeans.modules.bamboo.model;

import lombok.Data;

/**
 * This class represent the result for a plan.
 * @author spindizzy
 */
@Data
public class ResultVo {
    private String key;
    private int number;
    private String buildReason;
    private State state = State.Unknown;
    private LifeCycleState lifeCycleState = LifeCycleState.Finished;
}
