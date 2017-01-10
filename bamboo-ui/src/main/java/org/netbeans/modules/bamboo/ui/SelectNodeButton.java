package org.netbeans.modules.bamboo.ui;


import org.netbeans.modules.bamboo.model.rcp.PlanVo;
import org.openide.util.NbBundle;

import static org.netbeans.modules.bamboo.ui.Bundle.Select;
import static org.netbeans.modules.bamboo.ui.Bundle.Select_ToolTip;


/**
 *
 * @author spindizzy
 */
@NbBundle.Messages({
    "Select=Select",
    "Select_ToolTip=Select the Plan in the builder tree"
})
public class SelectNodeButton extends LinkButton {

    public SelectNodeButton(PlanVo plan) {
        super(Select());
        
        init(plan);
    }

    private void init(PlanVo plan) {
        setAction(new SelectNodeAction(plan));
        setToolTipText(Select_ToolTip());
    }
}
