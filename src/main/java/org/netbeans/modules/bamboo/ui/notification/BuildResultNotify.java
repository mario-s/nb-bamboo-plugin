package org.netbeans.modules.bamboo.ui.notification;

import java.awt.EventQueue;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.netbeans.modules.bamboo.glue.BambooInstance;
import org.netbeans.modules.bamboo.model.ModelProperties;
import org.netbeans.modules.bamboo.model.PlanVo;

/**
 * Listnere for all plans related to a {@link BambooInstance}.
 * 
 * @author spindizzy
 */
public class BuildResultNotify implements PropertyChangeListener {

    private final BambooInstance instance;
    
    private NotifyDelegator delegator;
    
    public BuildResultNotify(BambooInstance instance) {
        this.instance = instance;
        delegator = new NotifyDelegator();
        registerChangeListener();
    }

    private void registerChangeListener() {
        instance.getProjects().forEach(project -> {
            project.getPlans().forEach(plan -> {
                plan.addPropertyChangeListener(this);
            });
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName();
        if (ModelProperties.Result.toString().equals(propertyName)) {
            notify((PlanVo) evt.getSource());
        }
    }

    private void notify(PlanVo plan) {
        delegator.notify(plan);
    }

    void setDelegator(NotifyDelegator delegator) {
        this.delegator = delegator;
    }
}
