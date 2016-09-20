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
import org.openide.awt.NotificationDisplayer;
import org.openide.util.ImageUtilities;

/**
 *
 * @author spindizzy
 */
public class BuildResultNotify implements PropertyChangeListener {

    private static final Logger LOG = Logger.getLogger(BuildResultNotify.class.getName());

    @StaticResource
    private static final String ICON_BASE = "org/netbeans/modules/bamboo/resources/instance.png";

    private final BambooInstance instance;

    public BuildResultNotify(BambooInstance instance) {
        this.instance = instance;
        registerChangeListener();
    }

    private Icon getIcon() {
        return ImageUtilities.loadImageIcon(ICON_BASE, true);
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
            add((PlanVo) evt.getSource());
        }
    }

    private void add(PlanVo plan) {
        EventQueue.invokeLater(() -> {
            String name = plan.getName();
            ResultVo resVo = plan.getResult();
            String details = resVo.getState().toString();
            LOG.info(String.format("state of plan %s has changed to %s", name, details));
            NotificationDisplayer.getDefault().notify(name, getIcon(), details, null);
        });
    }
}
