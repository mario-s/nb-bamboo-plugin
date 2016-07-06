package org.netbeans.modules.bamboo.glue;

import org.netbeans.modules.bamboo.glue.BambooInstance;
import java.util.List;
import org.netbeans.modules.bamboo.rest.Plan;

/**
 *
 */
public interface PlansProvideable extends BambooInstance{

    List<Plan> getPlans();
    
     void setPlans(List<Plan> plans);

}
