package org.netbeans.modules.bamboo.rest;

import java.util.List;


public class Plans extends Metrics {
    private List<Plan> plan;

    public List<Plan> getPlan() {
        return plan;
    }

    public void setPlan(final List<Plan> plans) {
        this.plan = plans;
    }
}
