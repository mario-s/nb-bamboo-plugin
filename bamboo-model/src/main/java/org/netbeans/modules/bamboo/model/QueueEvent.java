package org.netbeans.modules.bamboo.model;

import lombok.Builder;
import lombok.Data;

/**
 * This event is fired when a build was triggered manual.
 * @author spindizzy
 */
@Data
@Builder
public class QueueEvent {
    private PlanVo plan;
    private LifeCycleState lifeCycleState;
}
