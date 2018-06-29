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

import javax.swing.Action;
import org.netbeans.modules.bamboo.model.rcp.ChangeVo;
import org.netbeans.modules.bamboo.model.rcp.ResultVo;
import org.netbeans.modules.bamboo.ui.util.DateFormatter;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;

import org.netbeans.api.io.Hyperlink;
import org.netbeans.api.io.OutputWriter;
import org.netbeans.modules.bamboo.ui.BrowserInstance;

import static org.netbeans.modules.bamboo.model.rcp.ResultExpandParameter.Changes;
import static java.lang.String.format;
import static org.openide.util.NbBundle.getMessage;

/**
 *
 * @author Mario Schroeder
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
    "Changes_Output_Title=Changes for {0} #{1}"
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
                printBuildReason(title, "No_Changes", res);
            }
        });
    }

    private Collection<ChangeVo> getChanges(ResultVo res) {
        return res.getChanges().orElse(emptyList());
    }

    private void printChanges(String name, Collection<ChangeVo> changes) {
        try (OutputWriter out = getOut(name)) {
            changes.forEach(change -> {
                StringBuilder builder = new StringBuilder();
                builder.append(DateFormatter.format(change.getDate())).append(SPC);
                builder.append(change.getAuthor()).append(SPC).append(change.getComment());
                out.println(builder.toString());

                final String commitUrl = change.getCommitUrl();
                out.println(commitUrl, Hyperlink.from(() -> BrowserInstance.Instance.showURL(commitUrl)));
                out.println();

                change.getFiles().forEach(file -> {
                    out.println(format(" %s", file.getName()));
                });
                out.println("\n");
            });
        }
    }

}
