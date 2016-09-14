package org.netbeans.modules.bamboo.model;

/**
 * This class is the plan related to a project.
 * @author spindizzy
 */
public class PlanVo extends AbstractOpenInBrowserVo{
    
    private String name;
    private String shortKey;
    private String shortName;
    private boolean enabled;
    private ResultVo result;
    private PlanType type;

    public PlanVo() {
        this.result = new ResultVo();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortKey() {
        return shortKey;
    }

    public void setShortKey(String shortKey) {
        this.shortKey = shortKey;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public PlanType getType() {
        return type;
    }

    public void setType(PlanType type) {
        this.type = type;
    }

    public ResultVo getResult() {
        return result;
    }
    
    public void setResult(ResultVo result) {
        ResultVo old = this.result;
        this.result = result;
        firePropertyChange(ModelProperties.Result.toString(), old, result);
    }
    
    
}
