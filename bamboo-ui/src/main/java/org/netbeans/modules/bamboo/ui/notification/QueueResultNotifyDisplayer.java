package org.netbeans.modules.bamboo.ui.notification;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.StatusType;
import org.netbeans.modules.bamboo.model.PlanVo;
import org.netbeans.modules.bamboo.model.event.QueueEvent;
import org.openide.awt.NotificationDisplayer.Category;
import org.openide.awt.NotificationDisplayer.Priority;
import org.openide.util.NbBundle.Messages;
import org.openide.util.Pair;

import static org.netbeans.modules.bamboo.ui.notification.Bundle.Build;
import static org.netbeans.modules.bamboo.ui.notification.Bundle.By_User;
import static org.netbeans.modules.bamboo.ui.notification.Bundle.Server_Response;
import static org.netbeans.modules.bamboo.ui.notification.Bundle.Start_Failed;
import static org.netbeans.modules.bamboo.ui.notification.Bundle.Start_Success;
import static java.lang.String.format;

/**
 * This class displays a ntification for manual build run.
 * @author spindizzy
 */
@Messages({"By_User=Manual run by user.",
    "Start_Success=started successful.",
    "Start_Failed=could not be started.",
    "Server_Response=The server responded with"})
class QueueResultNotifyDisplayer extends AbstractNotifyDisplayer {

    private static final String BR = "<br/>";

    private final QueueEvent event;

    QueueResultNotifyDisplayer(Icon icon, QueueEvent event) {
        super(icon);
        this.event = event;
    }

    private Response getResponse() {
        return event.getResponse();
    }

    private PlanVo getPlan() {
        return event.getPlan();
    }

    private boolean isOk() {
        return getResponse().getStatus() == Response.Status.OK.getStatusCode();
    }

    private Pair<Priority, Category> getCategory() {
        return isOk() ? INFO : ERROR;
    }

    private String getSummary() {
        String state = (isOk()) ? Start_Success() : Start_Failed();
        return format("%s %s", Build(), state);
    }

    private String getDetails() {
        StringBuilder builder = new StringBuilder();
        builder.append(By_User());
        if (!isOk()) {
            final StatusType statusInfo = getResponse().getStatusInfo();
            builder.append(BR).append(format("%s: %s", Server_Response(), statusInfo));
        }
        return builder.toString();
    }

    @Override
    public void run() {
        PlanVo plan = getPlan();
        String name = plan.getName();
        String summary = getSummary();

        JComponent balloonDetails = new JLabel(summary);
        JComponent popupDetails = newDetailsPanel(summary, getDetails());
        Pair<Priority, Category> cat = getCategory();

        notify(name, balloonDetails, popupDetails, cat);
    }

}
