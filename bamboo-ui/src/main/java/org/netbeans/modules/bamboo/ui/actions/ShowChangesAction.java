package org.netbeans.modules.bamboo.ui.actions;

import java.util.Collection;

import static java.util.Collections.emptyList;

import javax.swing.Action;
import org.netbeans.modules.bamboo.model.rcp.ChangeVo;
import org.netbeans.modules.bamboo.model.rcp.ResultVo;
import org.netbeans.modules.bamboo.ui.util.DateFormatter;
import org.netbeans.modules.bamboo.ui.util.TextExtractor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;

import org.netbeans.api.io.Hyperlink;
import org.netbeans.api.io.OutputWriter;
import org.netbeans.modules.bamboo.ui.BrowserInstance;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.netbeans.modules.bamboo.model.rcp.ResultExpandParameter.Changes;
import static java.lang.String.format;
import static org.openide.util.NbBundle.getMessage;

/**
 *
 * @author spindizzy
 */
@ActionID(
        category = "Bamboo",
        id = "org.netbeans.modules.bamboo.ui.actions.ShowChangesAction"
)
@ActionRegistration(
        displayName = "#CTL_ShowChangesAction", lazy = false
)
@ActionReference(path = ActionConstants.PLAN_ACTION_PATH, position = 720)
@NbBundle.Messages({
    "CTL_ShowChangesAction=&Show Changes",
    "Changes_Output_Title=Changes for result {0} #{1}",
    "No_Changes=No changes. Build reason: {0}"
})
public class ShowChangesAction extends AbstractResultAction {

    public ShowChangesAction() {
    }

    public ShowChangesAction(Lookup context) {
        super(Bundle.CTL_ShowChangesAction(), context);
    }

    @Override
    public Action createContextAwareInstance(Lookup actionContext) {
        return new ShowChangesAction(actionContext);
    }

    @Override
    protected void doRun(ResultVo res) {
        attachChangesIfAbsent(res);

        printResult(res);
    }

    private void attachChangesIfAbsent(ResultVo res) {
        if (!res.requestedChanges()) {
            res.getParent().ifPresent(p -> p.invoke(instance -> instance.expand(res, Changes)));
        }
    }

    private void printResult(ResultVo res) {
        res.getParent().ifPresent(plan -> {
            Object[] args = new Object[]{plan.getName(), res.getNumber()};
            String title = getMessage(ShowChangesAction.class, "Changes_Output_Title", args);
            Collection<ChangeVo> changes = getChanges(res);

            if (!changes.isEmpty()) {
                printChanges(title, changes);
            } else {
                printBuildReason(title, res); //print build msg when there are no changes
            }

        });
    }

    private Collection<ChangeVo> getChanges(ResultVo res) {
        return (res.requestedChanges()) ? res.getChanges().get() : emptyList();
    }

    private void printChanges(String name, Collection<ChangeVo> changes) {
        OutputWriter out = getOut(name);
        changes.forEach(change -> {
            StringBuilder builder = new StringBuilder();
            builder.append(DateFormatter.format(change.getDate())).append(": ");
            builder.append(change.getAuthor()).append(": ").append(change.getComment());
            out.println(builder.toString());

            final String commitUrl = change.getCommitUrl();
            out.println(commitUrl, Hyperlink.from(() -> BrowserInstance.Instance.showURL(commitUrl)));
            out.println();

            change.getFiles().forEach(file -> {
                out.println(format(" %s", file.getName()));
            });
            out.println("\n");
        });
        out.close();
    }

    private void printBuildReason(String name, ResultVo result) {
        TextExtractor extractor = new TextExtractor();
        String reason = result.getBuildReason();
        String normalized = (!isBlank(reason)) ? extractor.removeTags(reason) : "";
        String msg = getMessage(ShowChangesAction.class, "No_Changes", new Object[]{normalized});
        OutputWriter out = getOut(name);
        out.println(msg);
        out.close();
    }

}
