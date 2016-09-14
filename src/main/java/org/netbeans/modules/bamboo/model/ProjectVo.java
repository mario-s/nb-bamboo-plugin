package org.netbeans.modules.bamboo.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A class which represent the project.
 *
 * @author spindizzy
 */
public class ProjectVo extends AbstractOpenInBrowserVo {

    private String name;
    private List<PlanVo> plans;

    public ProjectVo() {
        plans = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PlanVo> getPlans() {
        return plans;
    }
    
    public void setPlans(List<PlanVo> plans) {
        List<PlanVo> old = this.plans;
        this.plans = plans;
        firePropertyChange(ModelProperties.Plans.toString(), old, plans);
    }

}
