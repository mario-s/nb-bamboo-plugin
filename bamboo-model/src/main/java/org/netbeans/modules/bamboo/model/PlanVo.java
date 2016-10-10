package org.netbeans.modules.bamboo.model;

import lombok.Getter;
import lombok.Setter;
import org.netbeans.api.annotations.common.NonNull;

/**
 * This class is the plan related to a project.
 *
 * @author spindizzy
 */
@Getter
@Setter
public class PlanVo extends AbstractOpenInBrowserVo {

    private String name;
    private String shortKey;
    private String shortName;
    private boolean enabled;
    private ResultVo result;
    private PlanType type;
    //parent project
    private ProjectVo project;

    
    public PlanVo(String key) {
        this(key, "");
    }

    public PlanVo(@NonNull String key, @NonNull String name) {
        super(key);
        this.name = name;
        this.result = new ResultVo();
    }

    public void setResult(ResultVo result) {
        ResultVo old = this.result;
        this.result = result;
        firePropertyChange(ChangeEvents.Result.toString(), old, result);
    }

}
