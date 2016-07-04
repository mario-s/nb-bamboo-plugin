package org.netbeans.modules.bamboo.rest;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;


/**
 * @author spindizzy
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AllPlansResponse extends AbstractAllResponse {
    @JsonProperty("plans")
    private Plans plans;

    public Plans getPlans() {
        return plans;
    }

    public void setPlans(final Plans plans) {
        this.plans = plans;
    }
}
