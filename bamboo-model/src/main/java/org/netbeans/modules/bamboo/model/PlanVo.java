package org.netbeans.modules.bamboo.model;

import java.util.Optional;
import lombok.Getter;
import org.netbeans.api.annotations.common.NonNull;
import lombok.Setter;
import lombok.ToString;

import static java.util.Optional.ofNullable;
import static lombok.AccessLevel.NONE;

/**
 * This class is the plan related to a project.
 *
 * @author spindizzy
 */
@Getter
@Setter
@ToString
public class PlanVo extends AbstractOpenInBrowserVo implements TraverseUp<ProjectVo>, Queueable{

    private String name;
    private String shortKey;
    private String shortName;
    private boolean enabled;
    private boolean ignore;
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
    
    public void setEnabled(boolean enabled) {
        boolean old = this.enabled;
        this.enabled = enabled;
        firePropertyChange(ModelChangedValues.Plan.toString(), old, enabled);
    }

    public void setResult(ResultVo result) {
        ResultVo old = this.result;
        //update only when the number is equal or higher
        if(result.getNumber() >= old.getNumber()){
            this.result = result;
            firePropertyChange(ModelChangedValues.Result.toString(), old, result);
        }
    }

    @Override
    public boolean isAvailable() {
       return AvailabilityVerifier.isAvailable(this);
    }

    @Override
    public void queue() {
       getParent().ifPresent(project ->{
            project.getParent().ifPresent(instance ->{
                instance.queue(this);
            });
        });
    }

}
