/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.netbeans.modules.bamboo.model.rcp;

import org.netbeans.modules.bamboo.model.LifeCycleState;
import org.netbeans.modules.bamboo.model.State;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;

/**
 * This class represent the result for a plan. If the property changes is empty,
 * that means there was no request to the server to get this information.
 *
 * @author Mario Schroeder
 */
public class ResultVo extends AbstractVo implements TraverseUp<PlanVo> {

    private int number;
    private String buildReason;
    private State state = State.Unknown;
    private LifeCycleState lifeCycleState = LifeCycleState.Finished;
    private long buildDurationInSeconds;
    private LocalDateTime buildStartedTime;
    private LocalDateTime buildCompletedTime;
    private int failedTestCount;
    private int successfulTestCount;

    private PlanVo parent;

    private Optional<Collection<ChangeVo>> changes;

    private Optional<Collection<IssueVo>> issues;

    public ResultVo() {
        this("");
    }

    public ResultVo(String key) {
        super(key);
        changes = empty();
        issues = empty();
    }

    public void setChanges(Collection<ChangeVo> changes) {
        this.changes = ofNullable(changes);
    }

    public void setIssues(Collection<IssueVo> issues) {
        this.issues = ofNullable(issues);
    }

    @Override
    public Optional<PlanVo> getParent() {
        return ofNullable(parent);
    }

    /**
     * This method returns <code>true</code> when the changes are not empty,
     * otherwhise false. If true it means that the server was already contacted.
     *
     * @return true when changes otherwhise false.
     */
    public boolean requestedChanges() {
        return getChanges().isPresent();
    }


    public int getNumber() {
        return this.number;
    }

    public String getBuildReason() {
        return this.buildReason;
    }

    public State getState() {
        return this.state;
    }

    public LifeCycleState getLifeCycleState() {
        return this.lifeCycleState;
    }

    public long getBuildDurationInSeconds() {
        return this.buildDurationInSeconds;
    }

    public LocalDateTime getBuildStartedTime() {
        return this.buildStartedTime;
    }

    public LocalDateTime getBuildCompletedTime() {
        return this.buildCompletedTime;
    }

    public int getFailedTestCount() {
        return this.failedTestCount;
    }

    public int getSuccessfulTestCount() {
        return this.successfulTestCount;
    }

    public Optional<Collection<ChangeVo>> getChanges() {
        return this.changes;
    }

    public Optional<Collection<IssueVo>> getIssues() {
        return this.issues;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setBuildReason(String buildReason) {
        this.buildReason = buildReason;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setLifeCycleState(LifeCycleState lifeCycleState) {
        this.lifeCycleState = lifeCycleState;
    }

    public void setBuildDurationInSeconds(long buildDurationInSeconds) {
        this.buildDurationInSeconds = buildDurationInSeconds;
    }

    public void setBuildStartedTime(LocalDateTime buildStartedTime) {
        this.buildStartedTime = buildStartedTime;
    }

    public void setBuildCompletedTime(LocalDateTime buildCompletedTime) {
        this.buildCompletedTime = buildCompletedTime;
    }

    public void setFailedTestCount(int failedTestCount) {
        this.failedTestCount = failedTestCount;
    }

    public void setSuccessfulTestCount(int successfulTestCount) {
        this.successfulTestCount = successfulTestCount;
    }

    public void setParent(PlanVo parent) {
        this.parent = parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        ResultVo other = (ResultVo) o;
        return number == other.number &&
                buildDurationInSeconds == other.buildDurationInSeconds &&
                failedTestCount == other.failedTestCount &&
                successfulTestCount == other.successfulTestCount &&
                state == other.state &&
                lifeCycleState == other.lifeCycleState &&
                Objects.equals(buildReason, other.buildReason) &&
                Objects.equals(buildStartedTime, other.buildStartedTime) &&
                Objects.equals(buildCompletedTime, other.buildCompletedTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), number, buildReason, state, lifeCycleState, buildDurationInSeconds,
                buildStartedTime, buildCompletedTime, failedTestCount, successfulTestCount);
    }
}
