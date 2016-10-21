package org.netbeans.modules.bamboo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This event is fired when a build was triggered manual.
 * @author spindizzy
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueueEvent {
    private PlanVo plan;
    private LifeCycleState lifeCycleState;
}
