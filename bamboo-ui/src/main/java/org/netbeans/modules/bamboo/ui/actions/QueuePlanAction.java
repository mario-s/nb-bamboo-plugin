package org.netbeans.modules.bamboo.ui.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.netbeans.modules.bamboo.model.PlanVo;
import org.openide.util.NbBundle;

/**
 * This action queues a plan for the next build.
 * @author spindizzy
 */
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
