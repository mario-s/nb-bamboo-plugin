package org.netbeans.modules.bamboo.ui.notification;

import java.util.logging.Level;
import javax.swing.Icon;
import lombok.extern.java.Log;
import org.netbeans.modules.bamboo.model.PlanVo;
import org.netbeans.modules.bamboo.model.ResultVo;
import org.netbeans.modules.bamboo.model.State;

import org.openide.awt.NotificationDisplayer;
import org.openide.awt.NotificationDisplayer.Category;
import org.openide.awt.NotificationDisplayer.Priority;
import org.openide.util.NbBundle;

import static org.netbeans.modules.bamboo.ui.notification.Bundle.Build;
import static org.netbeans.modules.bamboo.ui.notification.Bundle.Result_Failed;
import static org.netbeans.modules.bamboo.ui.notification.Bundle.Result_Successful;

/**
 * This class displays the notification in the status bar.
 *
 * @author spindizzy
 */
@Log
class NotifyDisplayer implements Runnable {

    private final Icon instanceIcon;

    private final BuildResult buildResult;

    NotifyDisplayer(Icon instanceIcon, BuildResult buildResult) {
        this.instanceIcon = instanceIcon;
        this.buildResult = buildResult;
    }

    @Override
    public void run() {
        if (isRelevant()) {
            PlanVo plan = buildResult.getPlan();
            String name = plan.getName();
            String details = getDetails(plan);

            if (log.isLoggable(Level.INFO)) {
                log.info(String.format("state of plan %s has changed to %s", name, details));
            }

            Priority priority = Priority.NORMAL;
            Category category = Category.INFO;
            if (isFailed(plan)) {
                priority = Priority.HIGH;
                category = Category.ERROR;
            }

            getNotificationDisplayer().notify(name, instanceIcon, details, null, priority, category);
        }
    }

    /**
     * Is the result change relevant for a notification? We are not interested in a change from success to success.
     */
    private boolean isRelevant() {
        boolean relevant = true;

        ResultVo oldResult = buildResult.getOldResult();
        ResultVo newResult = buildResult.getNewResult();

        if (State.Successful.equals(oldResult.getState()) && State.Successful.equals(newResult.getState())) {
            relevant = false;
        }

        if (log.isLoggable(Level.INFO)) {
            log.info(String.format("result change is relevant: %s", relevant));
        }

        return relevant;
    }

    @NbBundle.Messages({
        "Build=The Build",
        "Result_Failed=failed",
        "Result_Successful=was successful"
    })
    private String getDetails(PlanVo plan) {
        ResultVo result = plan.getResult();
        int number = result.getNumber();
        String strState = (isFailed(plan)) ? Result_Failed() : Result_Successful();

        return String.format("%s %s: %s", Build(), number, strState);
    }

    private boolean isFailed(PlanVo plan) {
        ResultVo result = plan.getResult();
        return isFailed(result);
    }

    private boolean isFailed(ResultVo result) {
        State state = result.getState();
        return State.Failed.equals(state);
    }

    NotificationDisplayer getNotificationDisplayer() {
        return NotificationDisplayer.getDefault();
    }
}
