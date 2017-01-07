package org.netbeans.modules.bamboo.ui.actions;

import java.util.Collection;

import static java.util.Collections.emptyList;

import javax.swing.Action;
import org.netbeans.modules.bamboo.model.rcp.ResultVo;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;

import org.netbeans.api.io.Hyperlink;
import org.netbeans.api.io.OutputWriter;
import org.netbeans.modules.bamboo.ui.BrowserInstance;

import org.netbeans.modules.bamboo.model.rcp.IssueVo;
import static org.netbeans.modules.bamboo.model.rcp.ResultExpandParameter.Jira;
import static org.openide.util.NbBundle.getMessage;

/**
 *
 * @author spindizzy
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
    "Issues_Output_Title=Issues for {0} #{1}"
})
public class ShowIssuesAction extends AbstractResultAction {


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
    protected void doRun(ResultVo res) {
        attachIssues(res);

        printResult(res);
    }

    private void attachIssues(ResultVo res) {
        res.getParent().ifPresent(p -> p.invoke(instance -> instance.expand(res, Jira)));
    }

    private void printResult(ResultVo res) {
        res.getParent().ifPresent(plan -> {
            Object[] args = new Object[]{plan.getName(), res.getNumber()};
            String title = getMessage(ShowIssuesAction.class, "Issues_Output_Title", args);
            Collection<IssueVo> issues = getIssues(res);

            if (!issues.isEmpty()) {
                printIssues(title, issues);
            } else {
                printBuildReason(title, "No_Issues", res);
            }
        });
    }

    private Collection<IssueVo> getIssues(ResultVo res) {
        return res.getIssues().orElse(emptyList());
    }

    private void printIssues(String name, Collection<IssueVo> changes) {
        OutputWriter out = getOut(name);
        changes.forEach(issue -> {
            StringBuilder builder = new StringBuilder();
            builder.append(issue.getKey()).append(SPC).append(issue.getSummary()).append(" -> ").append(issue.getAssignee());
            out.println(builder.toString());

            final String link = issue.getLink();
            out.println(link, Hyperlink.from(() -> BrowserInstance.Instance.showURL(link)));
            out.println("\n");
        });
        out.close();
    }


}
