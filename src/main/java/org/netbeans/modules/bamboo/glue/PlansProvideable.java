package org.netbeans.modules.bamboo.glue;

import org.netbeans.modules.bamboo.rest.model.Plan;

import java.util.List;


/**
 * @author spindizzy
 */
@Deprecated
public interface PlansProvideable extends BambooInstance {
    List<Plan> getPlans();

    void setPlans(List<Plan> plans);
}
