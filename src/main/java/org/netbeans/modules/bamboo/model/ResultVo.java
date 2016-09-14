package org.netbeans.modules.bamboo.model;

import java.util.Objects;

/**
 * This class represent the result for a plan.
 *
 * @author spindizzy
 */
public class ResultVo extends AbstractVo {

    private int number;
    private String buildReason;
    private State state = State.Unknown;
    private LifeCycleState lifeCycleState = LifeCycleState.Finished;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getBuildReason() {
        return buildReason;
    }

    public void setBuildReason(String buildReason) {
        this.buildReason = buildReason;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public LifeCycleState getLifeCycleState() {
        return lifeCycleState;
    }

    public void setLifeCycleState(LifeCycleState lifeCycleState) {
        this.lifeCycleState = lifeCycleState;
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 29 * hash + this.number;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ResultVo other = (ResultVo) obj;
        if (!Objects.equals(this.getKey(), other.getKey())) {
            return false;
        }
        return this.number == other.number;
    }

    
}
