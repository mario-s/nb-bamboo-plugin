package org.netbeans.modules.bamboo.ui.notification;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collection;
import org.netbeans.modules.bamboo.model.BambooInstance;
import org.netbeans.modules.bamboo.model.ChangeEvents;
import org.netbeans.modules.bamboo.model.LookupContext;
import org.netbeans.modules.bamboo.model.PlanVo;
import org.netbeans.modules.bamboo.model.QueueEvent;
import org.netbeans.modules.bamboo.model.ResultVo;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;

/**
 * Listnere for all plans related to a {@link BambooInstance}.
 *
 * @author spindizzy
 */
public class BuildResultNotify implements PropertyChangeListener, LookupListener {

    private final BambooInstance instance;

    private NotifyDelegator delegator;

    private final SynchronizationListener synchronizationListener;

    private Lookup.Result<QueueEvent> result;

    public BuildResultNotify(BambooInstance instance) {
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
        if (ChangeEvents.Result.toString().equals(propertyName)) {
            PlanVo plan = (PlanVo) evt.getSource();
            ResultVo oldResult = (ResultVo) evt.getOldValue();
            ResultVo newResult = (ResultVo) evt.getNewValue();
            notify(new BuildResult(plan, oldResult, newResult));
        }
    }

    @Override
    public void resultChanged(LookupEvent ev) {
        Collection<? extends QueueEvent> events = result.allInstances();
        events.forEach(event -> {
            //TODO notify
        });
    }

    private void notify(BuildResult buildResult) {
        delegator.notify(buildResult);
    }

    void setDelegator(NotifyDelegator delegator) {
        this.delegator = delegator;
    }
}
