package org.netbeans.modules.bamboo.ui.actions;

import java.awt.event.ActionEvent;
import java.util.Collection;
import java.util.MissingResourceException;
import java.util.Optional;
import javax.swing.Action;
import org.netbeans.modules.bamboo.model.rcp.ChangeVo;
import org.netbeans.modules.bamboo.model.rcp.InstanceInvokeable;
import org.netbeans.modules.bamboo.model.rcp.PlanVo;
import org.netbeans.modules.bamboo.model.rcp.ResultVo;
import org.netbeans.modules.bamboo.ui.util.DateFormatter;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.NbBundle;
import org.openide.util.RequestProcessor;
import org.openide.windows.IOProvider;
import org.openide.windows.InputOutput;
import org.openide.windows.OutputWriter;

import static java.util.Optional.empty;

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
    "Changes_Output_Title=Changes for result {0} number {1}",
    "No_Changes=No changes. Build reason: {0}"
})
public class ShowChangesAction extends AbstractContextAction implements Runnable {
    
    private static final RequestProcessor RP = new RequestProcessor(
            ShowChangesAction.class);

    private Lookup.Result<InstanceInvokeable> result;

    private Optional<PlanVo> plan;

    public ShowChangesAction() {
    }

    public ShowChangesAction(Lookup context) {
        super(Bundle.CTL_ShowChangesAction(), context);
        init();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        plan = (Optional<PlanVo>) allInstances().stream().findFirst();
        plan.ifPresent(p -> RP.post(this));
    }

    @Override
    public Action createContextAwareInstance(Lookup actionContext) {
        return new ShowChangesAction(actionContext);
    }

    @Override
    public void resultChanged(LookupEvent ev) {
        enableIfAvailable(allInstances());
    }

    private Collection<? extends InstanceInvokeable> allInstances() {
        return result.allInstances();
    }

    private void init() {
        result = getContext().lookupResult(InstanceInvokeable.class);
        result.addLookupListener(this);
        resultChanged(null);
        plan = empty();
    }

    @Override
    public void run() {
        PlanVo pVo = plan.get();
        Optional<Collection<ChangeVo>> optChanges = attachChangesIfAbsent(pVo);

        printResult(pVo, optChanges);
    }

    private void printResult(PlanVo pVo, Optional<Collection<ChangeVo>> optChanges) {
        ResultVo rVo = pVo.getResult();
        Object[] args = new Object[]{pVo.getName(), rVo.getNumber()};
        String name = NbBundle.getMessage(ShowChangesAction.class, "Changes_Output_Title", args);

        if (optChanges.isPresent()) {
            printChanges(name, optChanges.get());
        } else {
            printBuildReason(name, rVo); //print build reason when there are no changes
        }
    }

    private Optional<Collection<ChangeVo>> attachChangesIfAbsent(PlanVo pVo) {
        ResultVo rVo = pVo.getResult();
        Optional<Collection<ChangeVo>> optChanges = rVo.getChanges();
        if (!optChanges.isPresent()) {
            pVo.invoke(instance -> instance.attachChanges(rVo));
        }
        return optChanges;
    }

    private void printChanges(String name, Collection<ChangeVo> changes) {
        OutputWriter out = getOut(name);
        changes.forEach(change -> {
            StringBuilder builder = new StringBuilder();
            builder.append(DateFormatter.format(change.getDate())).append(": ");
            builder.append(change.getAuthor()).append(": ").append(change.getComment());
            out.println(builder.toString());
            out.println(change.getCommitUrl());

            change.getFiles().forEach(file -> {
                out.println(file.getName());
            });
        });
        out.close();
    }

    private void printBuildReason(String name, ResultVo result) {
        OutputWriter out = getOut(name);
        Object[] args = new Object[]{result.getBuildReason()};
        String reason = NbBundle.getMessage(ShowChangesAction.class, "No_Changes", args);
        out.println(reason);
        out.close();
    }

    private OutputWriter getOut(String name) {
        InputOutput io = getInputOutput(name);
        io.select();
        return io.getOut();
    }

    InputOutput getInputOutput(String name) {
        return IOProvider.getDefault().getIO(name, false);
    }
    
}
