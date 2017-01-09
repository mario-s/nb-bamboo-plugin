package org.netbeans.modules.bamboo.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static org.netbeans.modules.bamboo.ui.Bundle.Select;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;

/**
 *
 * @author spindizzy
 */
@NbBundle.Messages({
    "Select=Select",
    "Select_ToolTip=Select the Plan in the builder tree"
})
public class SelectNodeButton extends LinkButton implements ActionListener {

    public SelectNodeButton() {
        super(Select());
        init();
    }

    private void init() {
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        TopComponent servicesTab = findServicesTab();
    }

    TopComponent findServicesTab() {
        return WindowManager.getDefault().findTopComponent("Services");
    }
}
