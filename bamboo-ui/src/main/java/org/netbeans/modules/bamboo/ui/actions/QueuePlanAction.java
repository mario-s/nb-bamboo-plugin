package org.netbeans.modules.bamboo.ui.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import org.netbeans.modules.bamboo.model.Queueable;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.ContextAwareAction;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.NbBundle;
import org.openide.util.Utilities;

/**
 * This action queues a plan for the next build.
 * @author spindizzy
 */
@ActionID(
        category = "Bamboo",
        id = "org.netbeans.modules.bamboo.ui.actions.QueuePlanAction"
)
@ActionRegistration(
        displayName = "#CTL_QueuePlanAction"
)
@ActionReference(path = ActionConstants.PLAN_ACTION_PATH, position = 700)
@NbBundle.Messages({
    "CTL_QueuePlanAction=&Queue the Plan"
})
public class QueuePlanAction extends AbstractAction implements LookupListener, ContextAwareAction{
    
    private Lookup context;
    
    private Lookup.Result<Queueable> result;
    
    public QueuePlanAction() {
        this(Utilities.actionsGlobalContext());
    }
    
    private QueuePlanAction(Lookup context) {
        super(Bundle.CTL_QueuePlanAction());
        this.context = context;
    }
    
    void init() {
        result = context.lookupResult(Queueable.class);
        result.addLookupListener(this);
        resultChanged(null);
    }
    
    @Override
    public boolean isEnabled() {
        init();
        return super.isEnabled();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        init();
        result.allInstances().forEach(plan -> plan.queue());
    }

    @Override
    public void resultChanged(LookupEvent ev) {
        setEnabled(!result.allInstances().isEmpty());
    }

    @Override
    public Action createContextAwareInstance(Lookup actionContext) {
        return new QueuePlanAction(actionContext);
    }
}
