package org.netbeans.modules.bamboo.model.rcp;

import org.netbeans.modules.bamboo.model.LifeCycleState;
import org.netbeans.modules.bamboo.model.State;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * This class represent the result for a plan.
 * <br/>
 * If the property changes is empty, that means there was no request to the server to get this information.
 *
 * @author spindizzy
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, of = {"number"})
public class ResultVo extends AbstractVo {

    private int number;
    private String buildReason;
    private State state = State.Unknown;
    private LifeCycleState lifeCycleState = LifeCycleState.Finished;
    private long buildDurationInSeconds;
    private LocalDateTime buildStartedTime;
    private LocalDateTime buildCompletedTime;

    private Optional<Collection<ChangeVo>> changes;

    public ResultVo() {
        this("");
    }

    public ResultVo(String key) {
        super(key);
        changes = empty();
    }

    
    public void setChanges(Collection<ChangeVo> changes) {
        if (changes != null) {
            this.changes = of(changes);
        }
    }
}
