package org.netbeans.modules.bamboo.ui.actions;

import java.awt.event.ActionEvent;
import java.util.Collection;
import java.util.Optional;
import javax.swing.Action;
import org.netbeans.modules.bamboo.model.rcp.ChangeVo;
import org.netbeans.modules.bamboo.model.rcp.InstanceInvokeable;
import org.netbeans.modules.bamboo.model.rcp.PlanVo;
import org.netbeans.modules.bamboo.model.rcp.ResultVo;
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
    "Changes_Output_Title=Changes for result {0} number {1}"
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
        PlanVo p = plan.get();
        ResultVo r = p.getResult();
        Optional<Collection<ChangeVo>> optChanges = r.getChanges();
        if (!optChanges.isPresent()) {
            p.invoke(instance -> instance.attachChanges(r));
        }

        Object[] args = new Object[]{p.getName(), r.getNumber()};
        String name = NbBundle.getMessage(ShowChangesAction.class, "Changes_Output_Title", args);

        optChanges.ifPresent(changes -> printChanges(name, changes));
    }

    private void printChanges(String name, Collection<ChangeVo> changes) {
        InputOutput io = IOProvider.getDefault().getIO(name, new Action[0]);
        io.select();
        OutputWriter out = io.getOut();
        changes.forEach(change -> {
            StringBuilder builder = new StringBuilder();
            builder.append(change.getDate()).append(": ");
            builder.append(change.getAuthor()).append(": ").append(change.getComment());
            out.println(builder.toString());
            out.println(change.getCommitUrl());
            
            change.getFiles().forEach(file -> {
                out.println(file.getName());
            });
        });
        out.close();
    }
}
