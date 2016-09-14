package org.netbeans.modules.bamboo.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

/**
 * A class which represent the project.
 *
 * @author spindizzy
 */
@Data
public class ProjectVo extends AbstractOpenInBrowserVo{
    private String name;
    private List<PlanVo> plans;

    public ProjectVo() {
        plans = new ArrayList<>();
    }
    
    public void setPlans(List<PlanVo> plans){
        List<PlanVo> old = this.plans;
        this.plans = plans;
        firePropertyChange(ModelProperties.Plans.toString(), old, plans);
    }
}
