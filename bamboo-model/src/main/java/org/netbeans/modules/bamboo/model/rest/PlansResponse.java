package org.netbeans.modules.bamboo.model.rest;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

/**
 * @author spindizzy
 */
@Getter
@Setter
@XmlRootElement
public class PlansResponse extends AbstractResponse<Plan> {
    private Plans plans;

    @Override
    public Collection<Plan> asCollection(){
        Set<Plan> coll = new HashSet<>();
        if(plans != null){
            coll.addAll(plans.getPlan());
        }
        return coll;
    }

    @Override
    protected Metrics getMetrics() {
        return plans;
    }
}
