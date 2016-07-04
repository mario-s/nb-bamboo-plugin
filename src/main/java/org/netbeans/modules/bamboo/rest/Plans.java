package org.netbeans.modules.bamboo.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Plans extends Metrics {
    @JsonProperty("plan")
    private List<Plan> plans;

    public List<Plan> getPlans() {
        return plans;
    }

    public void setPlans(final List<Plan> plans) {
        this.plans = plans;
    }
}
