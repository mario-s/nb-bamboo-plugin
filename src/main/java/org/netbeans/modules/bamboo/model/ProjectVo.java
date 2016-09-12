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
    
    public void addPlan(PlanVo plan){
        plans.add(plan);
        getContent().add(plan);
    }
}
