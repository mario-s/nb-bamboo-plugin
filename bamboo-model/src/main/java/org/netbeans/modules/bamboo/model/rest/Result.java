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
package org.netbeans.modules.bamboo.model.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Objects;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.netbeans.modules.bamboo.model.LifeCycleState;
import org.netbeans.modules.bamboo.model.State;


/**
 * Result of a plan.
 * @author mario.schroeder
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Result implements ServiceInfoProvideable{
    @XmlAttribute
    private String key;
    @XmlElement
    private Link link;
    @XmlAttribute
    private State state;
    @XmlElement(name = "plan")
    private Plan plan;
    @XmlAttribute
    private LifeCycleState lifeCycleState;
    @XmlAttribute
    private int number;
    @XmlElement(name = "buildReason", type = String.class)
    private String buildReason;
    private int id;
    private long buildDurationInSeconds;
    private String buildStartedTime;
    private String buildCompletedTime;
    private int failedTestCount;
    private int successfulTestCount;
    
    @XmlElement
    private Changes changes;
    
    @XmlElement
    private JiraIssues jiraIssues;
    
    public Result() {
        state = State.Unknown;
        lifeCycleState = LifeCycleState.NotBuilt;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public LifeCycleState getLifeCycleState() {
        return lifeCycleState;
    }

    public void setLifeCycleState(LifeCycleState lifeCycleState) {
        this.lifeCycleState = lifeCycleState;
    }

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getBuildDurationInSeconds() {
        return buildDurationInSeconds;
    }

    public void setBuildDurationInSeconds(long buildDurationInSeconds) {
        this.buildDurationInSeconds = buildDurationInSeconds;
    }

    public String getBuildStartedTime() {
        return buildStartedTime;
    }

    public void setBuildStartedTime(String buildStartedTime) {
        this.buildStartedTime = buildStartedTime;
    }

    public String getBuildCompletedTime() {
        return buildCompletedTime;
    }

    public void setBuildCompletedTime(String buildCompletedTime) {
        this.buildCompletedTime = buildCompletedTime;
    }

    public int getFailedTestCount() {
        return failedTestCount;
    }

    public void setFailedTestCount(int failedTestCount) {
        this.failedTestCount = failedTestCount;
    }

    public int getSuccessfulTestCount() {
        return successfulTestCount;
    }

    public void setSuccessfulTestCount(int successfulTestCount) {
        this.successfulTestCount = successfulTestCount;
    }

    public Changes getChanges() {
        return changes;
    }

    public void setChanges(Changes changes) {
        this.changes = changes;
    }

    public JiraIssues getJiraIssues() {
        return jiraIssues;
    }

    public void setJiraIssues(JiraIssues jiraIssues) {
        this.jiraIssues = jiraIssues;
    }

    @Override
    public int hashCode() {
        return 41 * 3 + Objects.hashCode(this.key);
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
        final Result other = (Result) obj;
        return Objects.equals(this.key, other.key);
    }
    
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
