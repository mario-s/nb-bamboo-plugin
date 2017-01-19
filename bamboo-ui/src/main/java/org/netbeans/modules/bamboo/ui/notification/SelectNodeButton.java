package org.netbeans.modules.bamboo.ui.notification;


import org.netbeans.modules.bamboo.model.rcp.PlanVo;
import org.netbeans.modules.bamboo.ui.LinkButton;
import org.openide.util.NbBundle;

import static org.netbeans.modules.bamboo.ui.notification.Bundle.Select;
import static org.netbeans.modules.bamboo.ui.notification.Bundle.Select_ToolTip;



/**
 *
 * @author Mario Schroeder
 */
@NbBundle.Messages({
    "Select=Select",
    "Select_ToolTip=Select the plan in the builder tree"
})
final class SelectNodeButton extends LinkButton {

    SelectNodeButton(PlanVo plan) {
        super(Select());
        
        init(plan);
    }

    private void init(PlanVo plan) {
        addActionListener(new SelectNodeListener(plan));
        setToolTipText(Select_ToolTip());
    }
}
