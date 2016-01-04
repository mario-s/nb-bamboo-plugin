package org.netbeans.modules.bamboo.rest;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

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
