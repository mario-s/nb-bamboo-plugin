package org.netbeans.modules.bamboo.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import org.netbeans.modules.bamboo.model.PlanVo;
import org.openide.util.NbBundle.Messages;

import static java.util.Optional.ofNullable;
import static org.netbeans.modules.bamboo.ui.Bundle.Ignore;

/**
 *
 * @author spindizzy
 */
@Messages({
    "Ignore=Ignore"
})
public class IgnoreButton extends JButton implements ActionListener{
    
    private static final String TXT = "<html><a href=\"#\">" + Ignore();
    
    private Optional<PlanVo> plan;
    
    public IgnoreButton() {
        this(null);
    }

    public IgnoreButton(PlanVo plan) {
        super(TXT);
        init();
        setPlan(plan);
    }
    
    private void init() {
        setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        setBorderPainted(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setFocusable(false);
        setHorizontalAlignment(SwingConstants.LEFT);
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
