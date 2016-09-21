package org.netbeans.modules.bamboo.ui.notification;

import java.awt.EventQueue;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.logging.Logger;
import javax.swing.Icon;
import org.netbeans.api.annotations.common.StaticResource;
import org.netbeans.modules.bamboo.glue.BambooInstance;
import org.netbeans.modules.bamboo.model.ModelProperties;
import org.netbeans.modules.bamboo.model.PlanVo;
import org.netbeans.modules.bamboo.model.ResultVo;
import org.netbeans.modules.bamboo.model.State;
import org.openide.awt.NotificationDisplayer;
import org.openide.awt.NotificationDisplayer.Priority;
import org.openide.util.ImageUtilities;

/**
 *
 * @author spindizzy
 */
public class BuildResultNotify implements PropertyChangeListener {

    private static final Logger LOG = Logger.getLogger(BuildResultNotify.class.getName());

    @StaticResource
    private static final String ICON_BASE = "org/netbeans/modules/bamboo/resources/instance.png";
    
    private final Icon instanceIcon;

    private final BambooInstance instance;
    
    public BuildResultNotify(BambooInstance instance) {
        this.instance = instance;
        this.instanceIcon = ImageUtilities.loadImageIcon(ICON_BASE, false);
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

    private String getDetails(PlanVo plan) {
        ResultVo result = plan.getResult();
        int number = result.getNumber();
        State state = result.getState();
        return String.format("Build %s: %s", number, state);
    }

    private void notify(PlanVo plan) {
        EventQueue.invokeLater(() -> {
            String name = plan.getName();
            ResultVo resVo = plan.getResult();
            State state = resVo.getState();
            String details = getDetails(plan);

            LOG.info(String.format("state of plan %s has changed to %s", name, details));

            Priority priority = Priority.NORMAL;
            if (State.Failed.equals(state)) {
                priority = Priority.HIGH;
            }

            NotificationDisplayer.getDefault().notify(name, instanceIcon, details, null, priority);
        });
    }
}
