package org.netbeans.modules.bamboo.ui.notification;

import java.util.logging.Logger;
import javax.swing.Icon;
import org.netbeans.modules.bamboo.model.PlanVo;
import org.netbeans.modules.bamboo.model.ResultVo;
import org.netbeans.modules.bamboo.model.State;
import org.openide.awt.NotificationDisplayer;

/**
 * This class displays the notification in the status bar.
 *
 * @author spindizzy
 */
class NotifyDisplayer implements Runnable {

    private static final Logger LOG = Logger.getLogger(NotifyDisplayer.class.getName());

    private final Icon instanceIcon;

    private final PlanVo plan;

    NotifyDisplayer(Icon instanceIcon, PlanVo plan) {
        this.instanceIcon = instanceIcon;
        this.plan = plan;
    }

    @Override
    public void run() {
        String name = plan.getName();
        ResultVo resVo = plan.getResult();
        State state = resVo.getState();
        String details = getDetails(plan);

        LOG.info(String.format("state of plan %s has changed to %s", name, details));

        NotificationDisplayer.Priority priority = NotificationDisplayer.Priority.NORMAL;
        if (State.Failed.equals(state)) {
            priority = NotificationDisplayer.Priority.HIGH;
        }

        NotificationDisplayer.getDefault().notify(name, instanceIcon, details, null, priority);
    }

    private String getDetails(PlanVo plan) {
        ResultVo result = plan.getResult();
        int number = result.getNumber();
        State state = result.getState();
        return String.format("Build %s: %s", number, state);
    }

}
