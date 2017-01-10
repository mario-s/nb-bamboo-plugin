package org.netbeans.modules.bamboo.ui;

import java.awt.event.ActionEvent;
import java.beans.PropertyVetoException;
import java.util.Optional;
import javax.swing.AbstractAction;
import lombok.extern.java.Log;
import org.netbeans.modules.bamboo.model.rcp.PlanVo;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.ExplorerManager.Provider;
import org.openide.nodes.Node;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;

import static java.util.Optional.ofNullable;

/**
 *
 * @author spindizzy
 */
@Log
class SelectNodeAction extends AbstractAction {
    
    static final String TAB_ID = "services";

    private final Optional<PlanVo> plan;

    SelectNodeAction(PlanVo plan) {
        this.plan = ofNullable(plan);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        plan.ifPresent(p -> {
            findServicesTab().ifPresent(tab -> {
                tab.open();
                if (tab instanceof ExplorerManager.Provider) {
                    selectNodes((ExplorerManager.Provider) tab, p);
                }
            });
        });
    }

    void selectNodes(Provider provider, PlanVo pl) {
        ExplorerManager manager = provider.getExplorerManager();
        try {
            manager.setSelectedNodes(new Node[]{});
        } catch (PropertyVetoException ex) {
            log.warning(ex.getMessage());
        }
    }

    Optional<TopComponent> findServicesTab() {
        return ofNullable(WindowManager.getDefault().findTopComponent(TAB_ID));

    }

}
