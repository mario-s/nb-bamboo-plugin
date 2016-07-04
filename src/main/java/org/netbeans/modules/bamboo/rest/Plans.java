package org.netbeans.modules.bamboo.rest;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Plans extends Metrics {
    @JsonProperty("plan")
    private List<Plan> plans;
    private int size;
    @JsonProperty("start-index")
    private int startIndex;
    @JsonProperty("max-result")
    private int maxResult;

    public List<Plan> getPlans() {
        return plans;
    }

    public void setPlans(final List<Plan> plans) {
        this.plans = plans;
    }

    public int getSize() {
        return size;
    }

    public void setSize(final int size) {
        this.size = size;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(final int startIndex) {
        this.startIndex = startIndex;
    }

    public int getMaxResult() {
        return maxResult;
    }

    public void setMaxResult(final int maxResult) {
        this.maxResult = maxResult;
    }
}
