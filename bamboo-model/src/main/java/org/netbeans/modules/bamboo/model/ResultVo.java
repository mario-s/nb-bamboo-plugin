package org.netbeans.modules.bamboo.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import static org.apache.commons.lang3.StringUtils.EMPTY;

/**
 * This class represent the result for a plan.
 *
 * @author spindizzy
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class ResultVo extends AbstractVo {
    
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_DATE_TIME;

    private int number;
    private String buildReason;
    private State state = State.Unknown;
    private LifeCycleState lifeCycleState = LifeCycleState.Finished;
    private long buildDurationInSeconds;
    private LocalDateTime buildStartedTime;
    private LocalDateTime buildCompletedTime;

    public ResultVo() {
        this("");
    }

    public ResultVo(String key) {
        super(key);
    }
    
    public String getFormatedBuildStartedTime() {
        return (buildStartedTime != null) ? FORMATTER.format(buildStartedTime) : EMPTY;
    }
    
    public String getFormatedBuildCompletedTime() {
        return (buildCompletedTime != null) ? FORMATTER.format(buildCompletedTime) : EMPTY;
    }
}
