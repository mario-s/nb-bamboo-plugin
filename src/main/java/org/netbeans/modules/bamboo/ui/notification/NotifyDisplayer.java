package org.netbeans.modules.bamboo.ui.notification;

import javax.swing.Icon;
import lombok.extern.java.Log;
import org.netbeans.modules.bamboo.model.PlanVo;
import org.netbeans.modules.bamboo.model.ResultVo;
import org.netbeans.modules.bamboo.model.State;
import static org.netbeans.modules.bamboo.ui.notification.Bundle.Build;
import static org.netbeans.modules.bamboo.ui.notification.Bundle.Result_Failed;
import static org.netbeans.modules.bamboo.ui.notification.Bundle.Result_Successful;
import org.openide.awt.NotificationDisplayer;
import org.openide.awt.NotificationDisplayer.Category;
import org.openide.awt.NotificationDisplayer.Priority;
import org.openide.util.NbBundle;

/**
 * This class displays the notification in the status bar.
 *
 * @author spindizzy
 */
@Log
class NotifyDisplayer implements Runnable {
  
    private final Icon instanceIcon;

    private final PlanVo plan;
    
    NotifyDisplayer(Icon instanceIcon, PlanVo plan) {
        this.instanceIcon = instanceIcon;
        this.plan = plan;
    }

    @Override
    public void run() {
        String name = plan.getName();
        String details = getDetails(plan);

        log.info(String.format("state of plan %s has changed to %s", name, details));
        
        Priority priority = Priority.NORMAL;
        Category category = Category.INFO;
        if (isFailed(plan)) {
            priority = Priority.HIGH;
            category = Category.ERROR;
        }

        getNotificationDisplayer().notify(name, instanceIcon, details, null, priority, category);
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
    
    private boolean isFailed(PlanVo plan){
        ResultVo result = plan.getResult();
        State state = result.getState();
        return State.Failed.equals(state);
    }

    NotificationDisplayer getNotificationDisplayer() {
        return NotificationDisplayer.getDefault();
    }
}
