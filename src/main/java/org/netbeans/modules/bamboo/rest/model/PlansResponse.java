package org.netbeans.modules.bamboo.rest.model;

import java.util.ArrayList;
import java.util.Collection;
import lombok.Getter;
import lombok.Setter;

/**
 * @author spindizzy
 */
@Getter
@Setter
public class PlansResponse extends AbstractResponse {
    private Plans plans;

    @Override
    public Collection<Plan> asCollection(){
        Collection<Plan> coll = new ArrayList<>();
        if(plans != null){
            coll.addAll(plans.getPlan());
        }
        return coll;
    }

}
