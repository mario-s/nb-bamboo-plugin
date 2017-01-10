package org.netbeans.modules.bamboo.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import lombok.extern.java.Log;
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

    public SelectNodeButton() {
        super(Select());
        init();
    }

    private void init() {
        setToolTipText(Select_ToolTip());
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            ExplorerManager manager = findServicesTab().getExplorerManager();
            manager.setSelectedNodes(new Node[]{});
        } catch (PropertyVetoException ex) {
            log.warning(ex.getMessage());
        }
    }

    ExplorerManager.Provider findServicesTab() {
        return (ExplorerManager.Provider)WindowManager.getDefault().findTopComponent("Services");
    }

}
