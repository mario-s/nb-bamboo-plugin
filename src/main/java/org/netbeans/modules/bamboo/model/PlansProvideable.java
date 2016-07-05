package org.netbeans.modules.bamboo.model;

import org.netbeans.modules.bamboo.glue.BambooInstance;
import java.util.List;

/**
 *
 */
public interface PlansProvideable extends BambooInstance{

    List<Plan> getPlans();
    
     void setPlans(List<Plan> plans);

}
