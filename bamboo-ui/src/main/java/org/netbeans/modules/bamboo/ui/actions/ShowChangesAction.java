package org.netbeans.modules.bamboo.ui.actions;

import java.awt.event.ActionEvent;

import static java.lang.String.format;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;

import static java.util.Collections.emptyList;

import java.util.Optional;
import javax.swing.Action;
import org.netbeans.modules.bamboo.model.rcp.ChangeVo;
import org.netbeans.modules.bamboo.model.rcp.InstanceInvokeable;
import org.netbeans.modules.bamboo.model.rcp.PlanVo;
import org.netbeans.modules.bamboo.model.rcp.ResultVo;
import org.netbeans.modules.bamboo.ui.util.DateFormatter;
import org.netbeans.modules.bamboo.ui.util.TextExtractor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.NbBundle;
import org.openide.util.RequestProcessor;

import static java.util.Optional.empty;

import lombok.extern.java.Log;

import static org.apache.commons.lang3.StringUtils.isBlank;

import org.netbeans.api.io.Hyperlink;
import org.netbeans.api.io.OutputWriter;
import org.netbeans.modules.bamboo.ui.BrowserInstance;

import static org.netbeans.modules.bamboo.model.rcp.ResultExpandParameter.Changes;
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
@Log
public class ShowChangesAction extends AbstractResultAction {
    
    private static final RequestProcessor RP = new RequestProcessor(
            ShowChangesAction.class);
    
    
    public ShowChangesAction() {
    }
    
    public ShowChangesAction(Lookup context) {
        super(Bundle.CTL_ShowChangesAction(), context);
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        plan = findFirst();
        plan.ifPresent(p -> RP.post(this));
    }
    
    @Override
    public Action createContextAwareInstance(Lookup actionContext) {
        return new ShowChangesAction(actionContext);
    } 
    
    @Override
    public void run() {
        PlanVo pVo = plan.get();
        Collection<ChangeVo> changes = attachChangesIfAbsent(pVo);
        
        printResult(pVo, changes);
    }
    
    private void printResult(PlanVo pVo, Collection<ChangeVo> changes) {
        ResultVo rVo = pVo.getResult();
        Object[] args = new Object[]{pVo.getName(), rVo.getNumber()};
        String name = getMessage(ShowChangesAction.class, "Changes_Output_Title", args);
        
        if (!changes.isEmpty()) {
            printChanges(name, changes);
        } else {
            printBuildReason(name, rVo); //print build msg when there are no changes
        }
    }
    
    private Collection<ChangeVo> attachChangesIfAbsent(PlanVo pVo) {
        ResultVo rVo = pVo.getResult();
        if (!rVo.getChanges().isPresent()) {
            pVo.invoke(instance -> instance.expand(rVo, Changes));
        }
        return (rVo.getChanges().isPresent()) ? rVo.getChanges().get() : emptyList();
    }
    
    private void printChanges(String name, Collection<ChangeVo> changes) {
        OutputWriter out = getOut(name);
        changes.forEach(change -> {
            StringBuilder builder = new StringBuilder();
            builder.append(DateFormatter.format(change.getDate())).append(": ");
            builder.append(change.getAuthor()).append(": ").append(change.getComment());
            out.println(builder.toString());
            
            final String commitUrl = change.getCommitUrl();
            out.println(commitUrl, Hyperlink.from(() -> {
                try {
                    BrowserInstance.Instance.showURL(new URL(commitUrl));
                } catch (MalformedURLException ex) {
                    log.warning(ex.getMessage());
                }
            }));
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
    
    private OutputWriter getOut(String name) {
        InputOutputProvider provider = new InputOutputProvider();
        return provider.getOut(name);
    }
        
}
