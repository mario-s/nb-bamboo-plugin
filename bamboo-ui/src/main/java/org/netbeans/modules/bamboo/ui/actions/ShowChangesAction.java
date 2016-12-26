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
    "CTL_ShowChangesAction=&Show Changes"
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
        
        optChanges.ifPresent(changes -> printChanges(changes));
    }

    private void printChanges(Collection<ChangeVo> changes) {
        //TODO
    }
}
