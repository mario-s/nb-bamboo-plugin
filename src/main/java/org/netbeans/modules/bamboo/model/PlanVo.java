package org.netbeans.modules.bamboo.model;

import lombok.Data;

/**
 * This class is the plan related to a project.
 * @author spindizzy
 */
@Data
public class PlanVo extends AbstractOpenInBrowserVo{
    
    private String key;
    private String name;
    private String shortKey;
    private String shortName;
    private boolean enabled;
    
    private ResultVo result;

    private transient PlanType type;

    public PlanVo() {
        this.result = new ResultVo();
    }
}
