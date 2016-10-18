package org.netbeans.modules.bamboo.model.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.netbeans.modules.bamboo.model.LifeCycleState;
import org.netbeans.modules.bamboo.model.State;



@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Result extends Entity{
    private Link link;
    private State state;
    private Plan plan;
    private LifeCycleState lifeCycleState;
    private int number;
    private int id;
    private String buildReason;
    
    public Result() {
        state = State.Unknown;
        lifeCycleState = LifeCycleState.NotBuilt;
    }
}
