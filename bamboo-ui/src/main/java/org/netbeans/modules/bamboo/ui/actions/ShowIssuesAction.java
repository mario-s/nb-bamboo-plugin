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
package org.netbeans.modules.bamboo.ui.actions;

import java.util.Collection;

import static java.util.Collections.emptyList;
import java.util.function.Consumer;

import javax.swing.Action;
import org.netbeans.modules.bamboo.model.rcp.ResultVo;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;

import org.netbeans.api.io.Hyperlink;
import org.netbeans.api.io.OutputWriter;
import org.netbeans.modules.bamboo.model.rcp.BambooInstance;
import org.netbeans.modules.bamboo.ui.BrowserInstance;

import org.netbeans.modules.bamboo.model.rcp.IssueVo;
import org.netbeans.modules.bamboo.model.rcp.PlanVo;
import static org.openide.util.NbBundle.getMessage;
import static org.netbeans.modules.bamboo.model.rcp.ResultExpandParameter.JIRA;

/**
 *
 * @author Mario Schroeder
 */
@ActionID(
        category = "Bamboo",
        id = "org.netbeans.modules.bamboo.ui.actions.ShowIssuesAction"
)
@ActionRegistration(
        displayName = "#CTL_ShowIssuesAction", lazy = false
)
@ActionReference(path = ActionConstants.PLAN_ACTION_PATH, position = 725)
@NbBundle.Messages({
    "CTL_ShowIssuesAction=&Show Issues",
    "Issues_Output_Title=Issues for {0} #{1}",
    "Issue_Text={0}: {1}"
})
public class ShowIssuesAction extends AbstractPlanAction {

    public ShowIssuesAction() {
    }

    public ShowIssuesAction(Lookup context) {
        super(Bundle.CTL_ShowIssuesAction(), context);
    }

    @Override
    public Action createContextAwareInstance(Lookup actionContext) {
        return new ShowIssuesAction(actionContext);
    }

    @Override
    protected void process(PlanVo plan) {
        ResultVo result = plan.getResult();
        attachIssues(plan, result);

        printResult(plan, result);
    }

    private void attachIssues(PlanVo plan, ResultVo result) {
        Consumer<BambooInstance> action = inst -> inst.expand(result, JIRA);
        plan.invoke(action);
    }

    private void printResult(PlanVo plan, ResultVo result) {
        Object[] args = new Object[]{plan.getName(), result.getNumber()};
        String title = getMessage(ShowIssuesAction.class, "Issues_Output_Title", args);
        Collection<IssueVo> issues = getIssues(result);

        if (!issues.isEmpty()) {
            printIssues(title, issues);
        } else {
            printBuildReason(title, "No_Issues", result);
        }
    }

    private Collection<IssueVo> getIssues(ResultVo result) {
        return result.getIssues().orElse(emptyList());
    }

    private void printIssues(String name, Collection<IssueVo> changes) {
        try (OutputWriter out = getOut(name)) {
            changes.forEach(issue -> {
                String issueText = getMessage(ShowIssuesAction.class, "Issue_Text", new Object[]{issue.getKey(), issue.getSummary()});
                String link = issue.getLink();
                out.println(issueText, Hyperlink.from(() -> BrowserInstance.Instance.showURL(link)));
            });
        }
    }

}
