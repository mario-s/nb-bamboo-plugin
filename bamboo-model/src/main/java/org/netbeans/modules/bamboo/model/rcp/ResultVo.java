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
import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import static java.util.Optional.ofNullable;
import static lombok.AccessLevel.NONE;

/**
 * This class represent the result for a plan.
 * If the property changes is empty, that means there was no request to the server to get this information.
 *
 * @author Mario Schroeder
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, exclude = {"parent", "changes", "issues"})
public class ResultVo extends AbstractVo implements TraverseUp<PlanVo>{

    private int number;
    private String buildReason;
    private State state = State.Unknown;
    private LifeCycleState lifeCycleState = LifeCycleState.Finished;
    private long buildDurationInSeconds;
    private LocalDateTime buildStartedTime;
    private LocalDateTime buildCompletedTime;
    private int failedTestCount;
    private int successfulTestCount;
    
    @Getter(NONE)
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
        if (changes != null) {
            this.changes = of(changes);
        }
    }
    
    public void setIssues(Collection<IssueVo> issues) {
        if (issues != null) {
            this.issues = of(issues);
        }
    }

    @Override
    public Optional<PlanVo> getParent() {
        return ofNullable(parent);
    }
    
    /**
     * This method returns <code>true</code> when the changes are not empty, otherwhise false.
     * If true it means that the server was already contacted.
     * @return true when changes otherwhise false.
     */
    public boolean requestedChanges() {
        return getChanges().isPresent();
    }

}
