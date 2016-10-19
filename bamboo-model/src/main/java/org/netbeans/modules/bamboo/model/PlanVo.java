package org.netbeans.modules.bamboo.model;

import java.util.Optional;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import static java.util.Optional.ofNullable;
import static lombok.AccessLevel.NONE;

/**
 * This class is the plan related to a project.
 *
 * @author spindizzy
 */
@Getter
@Setter
public class PlanVo extends AbstractOpenInBrowserVo implements TraverseUp<ProjectVo>{

    private String name;
    private String shortKey;
    private String shortName;
    private boolean enabled;
    private ResultVo result;
    private PlanType type;
    @Getter(NONE)
    private ProjectVo parent;
    
    public PlanVo(String key) {
        this(key, "");
    }

    public PlanVo(@NonNull String key, @NonNull String name) {
        super(key);
        this.name = name;
        this.result = new ResultVo();
    }

    @Override
    public Optional<ProjectVo> getParent() {
       return ofNullable(parent);
    }

    public void setResult(ResultVo result) {
        ResultVo old = this.result;
        this.result = result;
        firePropertyChange(ChangeEvents.Result.toString(), old, result);
    }

}
