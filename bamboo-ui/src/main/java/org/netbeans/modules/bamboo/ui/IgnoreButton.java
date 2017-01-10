package org.netbeans.modules.bamboo.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;
import org.netbeans.modules.bamboo.model.rcp.PlanVo;
import org.openide.util.NbBundle.Messages;

import static java.util.Optional.ofNullable;
import static org.netbeans.modules.bamboo.ui.Bundle.Ignore;
import static org.netbeans.modules.bamboo.ui.Bundle.Ignore_Tooltip;

/**
 *
 * @author spindizzy
 */
@Messages({
    "Ignore=Ignore",
    "Ignore_Tooltip=Stop watching changes of the plan"
})
public class IgnoreButton extends LinkButton implements ActionListener{
    
    private Optional<PlanVo> plan;
    
    public IgnoreButton() {
        this(null);
    }

    public IgnoreButton(PlanVo plan) {
        super(Ignore());
        init();
        setPlan(plan);
    }
    
    private void init() {
        setToolTipText(Ignore_Tooltip());
        addActionListener(this);
    }
    
    public final void setPlan(PlanVo plan) {
        this.plan = ofNullable(plan);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
       plan.ifPresent(p -> p.setNotify(false));
    }
}
