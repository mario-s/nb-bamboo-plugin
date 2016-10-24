package org.netbeans.modules.bamboo.ui.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.netbeans.modules.bamboo.model.PlanVo;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle;

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
public class QueuePlanAction extends AbstractAction{
    
    private final PlanVo plan;

    public QueuePlanAction(PlanVo plan) {
        super(Bundle.CTL_QueuePlanAction());
        this.plan = plan;
        setEnabled(plan.isEnabled());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        plan.getParent().ifPresent(project ->{
            project.getParent().ifPresent(instance ->{
                instance.queue(plan);
            });
        });
    }
}
