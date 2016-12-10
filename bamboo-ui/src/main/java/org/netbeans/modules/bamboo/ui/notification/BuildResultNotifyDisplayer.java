package org.netbeans.modules.bamboo.ui.notification;

import java.util.logging.Level;
import javax.swing.Icon;
import javax.swing.JComponent;
import lombok.extern.java.Log;
import org.netbeans.modules.bamboo.model.PlanVo;
import org.netbeans.modules.bamboo.model.ResultVo;
import org.netbeans.modules.bamboo.model.State;
import org.openide.awt.NotificationDisplayer.Category;
import org.openide.awt.NotificationDisplayer.Priority;
import org.openide.util.NbBundle;
import org.openide.util.Pair;

import static org.netbeans.modules.bamboo.ui.notification.Bundle.Build;
import static org.netbeans.modules.bamboo.ui.notification.Bundle.Result_Failed;
import static org.netbeans.modules.bamboo.ui.notification.Bundle.Result_Successful;

/**
 * This class displays the build result in the status bar.
 *
 * @author spindizzy
 */
@Log
@NbBundle.Messages({
    "Result_Failed=failed",
    "Result_Successful=was successful"
})
class BuildResultNotifyDisplayer extends AbstractNotifyDisplayer {

    private final BuildResult buildResult;

    BuildResultNotifyDisplayer(Icon icon, BuildResult buildResult) {
        super(icon);
        this.buildResult = buildResult;
    }

    @Override
    public void run() {
        if (isRelevant()) {
            PlanVo plan = getPlan();
            String name = plan.getName();

            if (log.isLoggable(Level.INFO)) {
                log.info(String.format("state of plan %s has changed", name));
            }

            String summary = getSummary(plan);
            String reason = getBuildReason();

            JComponent balloonDetails = newBalloonComponent(summary, plan);
            JComponent popupDetails = newDetailsComponent(summary, reason);
            Pair<Priority, Category> cat = getCategory();

            notify(name, balloonDetails, popupDetails, cat);
        }
    }

    private JComponent newBalloonComponent(String summary, PlanVo plan) {
        return new BuildResultBalloonPanel(summary, plan);
    }

    private String getBuildReason() {
        return buildResult.getNewResult().getBuildReason();
    }

    private Pair<Priority, Category> getCategory() {
        PlanVo plan = getPlan();
        return (isFailed(plan)) ? ERROR : INFO;
    }

    private PlanVo getPlan() {
        return buildResult.getPlan();
    }

    private String getSummary(PlanVo plan) {
        ResultVo result = plan.getResult();
        int number = result.getNumber();
        String strState = (isFailed(plan)) ? Result_Failed() : Result_Successful();

        return String.format("%s %s %s", Build(), number, strState);
    }

    private boolean isFailed(PlanVo plan) {
        ResultVo result = plan.getResult();
        return isFailed(result);
    }

    private boolean isFailed(ResultVo result) {
        State state = result.getState();
        return State.Failed.equals(state);
    }

    private boolean isRelevant() {
        boolean relevant = true;

        if (isStillSuccessful()) {
            relevant = false;
        }

        if (log.isLoggable(Level.INFO)) {
            log.info(String.format("result change is relevant: %s", relevant));
        }

        return relevant;
    }

    private boolean isStillSuccessful() {
        ResultVo oldRes = buildResult.getOldResult();
        ResultVo newRes = buildResult.getNewResult();
        return State.Successful.equals(oldRes.getState()) && State.Successful.equals(newRes.getState());
    }


}
