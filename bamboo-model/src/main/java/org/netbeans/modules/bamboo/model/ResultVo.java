package org.netbeans.modules.bamboo.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * This class represent the result for a plan.
 *
 * @author spindizzy
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class ResultVo extends AbstractVo {

    private int number;
    private String buildReason;
    private State state = State.Unknown;
    private LifeCycleState lifeCycleState = LifeCycleState.Finished;
    private long buildDurationInSeconds;
    private LocalDateTime buildStartedTime;
    private LocalDateTime buildCompletedTime;

    private Collection<ChangeVo> changes;

    public ResultVo() {
        this("");
    }

    public ResultVo(String key) {
        super(key);
        changes = new ArrayList<>();
    }

    public void setChanges(Collection<ChangeVo> changes) {
        if (changes != null) {
            this.changes = changes;
        }
    }
}
