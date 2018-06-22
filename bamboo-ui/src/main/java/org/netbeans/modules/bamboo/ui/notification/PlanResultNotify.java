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
package org.netbeans.modules.bamboo.ui.notification;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.netbeans.modules.bamboo.model.rcp.BambooInstance;
import org.netbeans.modules.bamboo.model.rcp.ModelChangedValues;
import org.netbeans.modules.bamboo.model.rcp.PlanVo;
import org.netbeans.modules.bamboo.model.event.QueueEvent;
import org.netbeans.modules.bamboo.model.rcp.ResultVo;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;

/**
 * Listener for all plans with their results related to a {@link BambooInstance}.
 *
 * @author Mario Schroeder
 */
class PlanResultNotify implements PropertyChangeListener, LookupListener {

    private final BambooInstance instance;

    private NotifyDelegator delegator;

    private final SynchronizationListener synchronizationListener;

    private Lookup.Result<QueueEvent> result;

    PlanResultNotify(BambooInstance instance) {
        this.instance = instance;
        delegator = new NotifyDelegator();
        synchronizationListener = new SynchronizationListener(instance);
        registerListeners();
    }

    private void registerListeners() {
        synchronizationListener.registerListener();

        instance.getChildren().forEach(project -> {
            project.getChildren().forEach(plan -> {
                plan.addPropertyChangeListener(this);
            });
        });

        result = instance.getLookup().lookupResult(QueueEvent.class);
        result.addLookupListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName();
        if (ModelChangedValues.Result.toString().equals(propertyName)) {
            PlanVo plan = (PlanVo) evt.getSource();
            if (plan.isNotify()) {
                ResultVo oldResult = (ResultVo) evt.getOldValue();
                ResultVo newResult = (ResultVo) evt.getNewValue();
                delegator.notify(new BuildResult(plan, oldResult, newResult));
            }
        }
    }

    @Override
    public void resultChanged(LookupEvent ev) {
        result.allInstances().forEach(event -> {
            delegator.notify(event);
        });
    }

    void setDelegator(NotifyDelegator delegator) {
        this.delegator = delegator;
    }
}
