package org.netbeans.modules.bamboo.glue;

import java.util.List;
import org.netbeans.modules.bamboo.rest.model.Plan;

/**
 *
 */
public interface PlansProvideable extends BambooInstance{

    List<Plan> getPlans();
    
     void setPlans(List<Plan> plans);

}