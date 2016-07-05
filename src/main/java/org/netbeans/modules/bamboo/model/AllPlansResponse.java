package org.netbeans.modules.bamboo.model;

/**
 * @author spindizzy
 */
public class AllPlansResponse extends AbstractAllResponse {
    private Plans plans;

    public Plans getPlans() {
        return plans;
    }

    public void setPlans(final Plans plans) {
        this.plans = plans;
    }
}
