package org.netbeans.modules.bamboo.ui.notification;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;
import org.netbeans.modules.bamboo.model.rcp.PlanVo;
import org.netbeans.modules.bamboo.ui.LinkButton;
import org.openide.util.NbBundle.Messages;

import static java.util.Optional.ofNullable;
import static org.netbeans.modules.bamboo.ui.notification.Bundle.Ignore;
import static org.netbeans.modules.bamboo.ui.notification.Bundle.Ignore_Tooltip;

/**
 *
 * @author Mario Schroeder
 */
@Messages({
    "Ignore=Ignore",
    "Ignore_Tooltip=Stop watching changes of the plan"
})
final class IgnoreButton extends LinkButton implements ActionListener{
    
    private final Optional<PlanVo> plan;

    IgnoreButton(PlanVo plan) {
        super(Ignore());
        this.plan = ofNullable(plan);
        init();
    }
    
    private void init() {
        setToolTipText(Ignore_Tooltip());
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
       plan.ifPresent(p -> p.setNotify(false));
    }
}
