package org.netbeans.modules.bamboo.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Optional;
import java.util.function.Consumer;
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
public class PlanVo extends AbstractOpenInBrowserVo implements PropertyChangeListener, TraverseUp<ProjectVo>, Queueable {

    private String name;
    private String shortKey;
    private String shortName;
    private boolean enabled;
    private boolean notify;
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
        this.notify = true;
        init();
    }
    
    private void init() {
        addPropertyChangeListener(this);
    }

    @Override
    public Optional<ProjectVo> getParent() {
        return ofNullable(parent);
    }

    public void setNotify(boolean notify) {
        boolean old = this.notify;
        this.notify = notify;
        firePropertyChange(ModelChangedValues.Silent.toString(), old, notify);
    }

    public void setEnabled(boolean enabled) {
        boolean old = this.enabled;
        this.enabled = enabled;
        firePropertyChange(ModelChangedValues.Plan.toString(), old, enabled);
    }

    public void setResult(ResultVo result) {
        ResultVo old = this.result;
        //update only when the number is equal or higher
        if (result.getNumber() >= old.getNumber()) {
            this.result = result;
            firePropertyChange(ModelChangedValues.Result.toString(), old, result);
        }
    }

    @Override
    public boolean isAvailable() {
        return AvailabilityVerifier.isAvailable(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propName = evt.getPropertyName();
        if (ModelChangedValues.Silent.toString().equals(propName)) {
            invoke(instance -> instance.updateNotify(this));
        }
    }

    @Override
    public void queue() {
        invoke(instance -> instance.queue(this));
    }

    private void invoke(final Consumer<BambooInstance> action) {
        getParent().ifPresent(project -> {
            project.getParent().ifPresent(action);
        });
    }

}
