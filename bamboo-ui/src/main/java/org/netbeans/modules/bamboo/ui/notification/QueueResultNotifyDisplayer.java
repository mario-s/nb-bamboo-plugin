package org.netbeans.modules.bamboo.ui.notification;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import lombok.extern.java.Log;
import org.netbeans.modules.bamboo.model.LifeCycleState;
import org.netbeans.modules.bamboo.model.PlanVo;
import org.netbeans.modules.bamboo.model.QueueEvent;
import org.openide.awt.NotificationDisplayer.Category;
import org.openide.awt.NotificationDisplayer.Priority;
import org.openide.util.NbBundle.Messages;
import org.openide.util.Pair;

import static org.netbeans.modules.bamboo.ui.notification.Bundle.Build;
import static org.netbeans.modules.bamboo.ui.notification.Bundle.By_User;
import static org.netbeans.modules.bamboo.ui.notification.Bundle.Start_Failed;
import static org.netbeans.modules.bamboo.ui.notification.Bundle.Start_Success;

/**
 *
 * @author spindizzy
 */
@Log
@Messages({"By_User=Manual run by user.",
    "Start_Success=started successful.",
    "Start_Failed=could not be started."})
class QueueResultNotifyDisplayer extends AbstractNotifyDisplayer {

    private final QueueEvent event;

    QueueResultNotifyDisplayer(Icon icon, QueueEvent event) {
        super(icon);
        this.event = event;
    }

    private Pair<Priority, Category> getCategory() {
        return isInQueue() ? INFO : ERROR;
    }

    private boolean isInQueue() {
        return LifeCycleState.Queued.equals(event.getLifeCycleState());
    }

    private PlanVo getPlan() {
        return event.getPlan();
    }

    private String getSummary() {
        String state = (isInQueue()) ? Start_Success() : Start_Failed();
        return String.format("%s %s", Build(), state);
    }

    @Override
    public void run() {
        PlanVo plan = getPlan();
        String name = plan.getName();
        String summary = getSummary();

        JComponent balloonDetails = new JLabel(summary);
        JComponent popupDetails = newDetailsPanel(summary, By_User());
        Pair<Priority, Category> cat = getCategory();

        notify(name, balloonDetails, popupDetails, cat);
    }

}
