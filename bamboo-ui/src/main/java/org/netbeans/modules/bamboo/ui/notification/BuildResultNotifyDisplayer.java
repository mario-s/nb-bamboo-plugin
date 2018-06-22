/* 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.netbeans.modules.bamboo.ui.notification;

import java.util.logging.Level;
import javax.swing.Icon;
import javax.swing.JComponent;
import lombok.extern.java.Log;
import org.netbeans.modules.bamboo.model.rcp.PlanVo;
import org.netbeans.modules.bamboo.model.rcp.ResultVo;
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
 * @author Mario Schroeder
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
            final PlanVo plan = getPlan();
            final String name = plan.getName();

            log.log(Level.INFO, "state of plan {0} has changed", name);

            final String summary = getSummary(plan);

            JComponent balloonDetails = new ResultDetailsPanel(summary, new IgnoreButton(plan));
            JComponent popupDetails = newDetailsComponent(summary);

            Pair<Priority, Category> cat = getCategory();

            notify(name, balloonDetails, popupDetails, cat);
        }
    }

    private JComponent newDetailsComponent(String summary) {
        ResultDetailsPanelFactory factory = new ResultDetailsPanelFactory();
        return factory.create(summary, buildResult);
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

        log.log(Level.INFO, "result change is relevant: {0}", relevant);

        return relevant;
    }

    private boolean isStillSuccessful() {
        ResultVo oldRes = buildResult.getOldResult();
        ResultVo newRes = buildResult.getNewResult();
        return State.Successful.equals(oldRes.getState()) && State.Successful.equals(newRes.getState());
    }

}
