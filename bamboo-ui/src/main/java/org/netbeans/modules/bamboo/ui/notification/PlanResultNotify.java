package org.netbeans.modules.bamboo.ui.notification;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.netbeans.modules.bamboo.model.BambooInstance;
import org.netbeans.modules.bamboo.model.ModelChangedValues;
import org.netbeans.modules.bamboo.model.PlanVo;
import org.netbeans.modules.bamboo.model.event.QueueEvent;
import org.netbeans.modules.bamboo.model.ResultVo;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;

/**
 * Listener for all plans with their results related to a {@link BambooInstance}.
 *
 * @author spindizzy
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
            ResultVo oldResult = (ResultVo) evt.getOldValue();
            ResultVo newResult = (ResultVo) evt.getNewValue();
            delegator.notify(new BuildResult(plan, oldResult, newResult));
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
