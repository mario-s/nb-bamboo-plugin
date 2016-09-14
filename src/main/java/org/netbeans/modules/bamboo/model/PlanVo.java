package org.netbeans.modules.bamboo.model;

import lombok.Data;

/**
 * This class is the plan related to a project.
 * @author spindizzy
 */
@Data
public class PlanVo extends AbstractOpenInBrowserVo{
    
    private String name;
    private String shortKey;
    private String shortName;
    private boolean enabled;
    
    private transient ResultVo result;

    private transient PlanType type;

    public PlanVo() {
        this.result = new ResultVo();
    }
    
    public void setResult(ResultVo result) {
        ResultVo old = this.result;
        this.result = result;
        firePropertyChange(ModelProperties.Result.toString(), old, result);
        getContent().add(result);
    }
}
