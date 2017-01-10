package org.netbeans.modules.bamboo.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.util.Optional;
import static java.util.Optional.ofNullable;
import lombok.extern.java.Log;
import org.netbeans.modules.bamboo.model.rcp.PlanVo;
import org.openide.explorer.ExplorerManager;
import org.openide.nodes.Node;
import org.openide.util.NbBundle;
import org.openide.windows.WindowManager;

import static org.netbeans.modules.bamboo.ui.Bundle.Select;
import static org.netbeans.modules.bamboo.ui.Bundle.Select_ToolTip;

/**
 *
 * @author spindizzy
 */
@Log
@NbBundle.Messages({
    "Select=Select",
    "Select_ToolTip=Select the Plan in the builder tree"
})
public class SelectNodeButton extends LinkButton implements ActionListener {

    private Optional<PlanVo> plan;

    public SelectNodeButton(PlanVo plan) {
        super(Select());
        this.plan = ofNullable(plan);
        init();
    }

    private void init() {
        setToolTipText(Select_ToolTip());
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        plan.ifPresent(p -> {
            try {
                ExplorerManager manager = findServicesTab().getExplorerManager();
                manager.setSelectedNodes(new Node[]{});
            } catch (PropertyVetoException ex) {
                log.warning(ex.getMessage());
            }
        });
    }

    ExplorerManager.Provider findServicesTab() {
        return (ExplorerManager.Provider) WindowManager.getDefault().findTopComponent("Services");
    }

}
