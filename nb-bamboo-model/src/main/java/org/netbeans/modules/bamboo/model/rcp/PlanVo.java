/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.netbeans.modules.bamboo.model.rcp;

import org.netbeans.api.annotations.common.NonNull;
import org.netbeans.modules.bamboo.model.PlanType;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Optional;
import java.util.function.Consumer;

import static java.util.Optional.ofNullable;

/**
 * This class is the plan related to a project.
 *
 * @author Mario Schroeder
 */
public class PlanVo extends AbstractOpenInBrowserVo implements PropertyChangeListener, TraverseUp<ProjectVo>, Queueable {

    private String name;
    private String description;
    private String shortKey;
    private String shortName;
    private boolean enabled;
    private boolean notify;
    private ResultVo result;
    private PlanType type;
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
            this.result.setParent(this);
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

    @Override
    public void invoke(final Consumer<BambooInstance> action) {
        getParent().ifPresent(project -> {
            project.getParent().ifPresent(action);
        });
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

    public ResultVo getResult() {
        return result;
    }

    public PlanType getType() {
        return type;
    }

    public void setType(PlanType type) {
        this.type = type;
    }

    public void setParent(ProjectVo parent) {
        this.parent = parent;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean isNotify() {
        return notify;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
