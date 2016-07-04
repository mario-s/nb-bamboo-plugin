package org.netbeans.modules.bamboo.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


public class Plans extends Metrics {
    @JsonProperty(value = "plan")
    private List<Plan> plans;

    public List<Plan> getPlans() {
        return plans;
    }

    public void setPlans(final List<Plan> plans) {
        this.plans = plans;
    }
}
