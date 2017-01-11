package org.netbeans.modules.bamboo.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.util.Optional;
import lombok.extern.java.Log;
import org.netbeans.modules.bamboo.model.rcp.PlanVo;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.ExplorerManager.Provider;
import org.openide.nodes.Node;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;

import static java.util.Optional.ofNullable;
import static org.netbeans.modules.bamboo.ui.RootNodeConstants.BAMBOO_NODE_NAME;

/**
 *
 * @author spindizzy
 */
@Log
class SelectNodeListener implements ActionListener {

    static final String TAB_ID = "services";

    private final Optional<PlanVo> plan;

    SelectNodeListener(PlanVo plan) {
        this.plan = ofNullable(plan);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        plan.ifPresent(p -> {
            findServicesTab().ifPresent(tab -> {
                tab.open();
                tab.requestActive();
                if (tab instanceof ExplorerManager.Provider) {
                    selectNodes((ExplorerManager.Provider) tab, p);
                }
            });
        });
    }

    final void selectNodes(Provider provider, PlanVo pl) {
        ExplorerManager em = provider.getExplorerManager();
        Node root = em.getRootContext();

        pl.getParent().ifPresent(project -> {
            findNode(root, BAMBOO_NODE_NAME).ifPresent(builderNode -> {

                findNode(builderNode, project.getName()).ifPresent(projectNode -> {

                    findNode(projectNode, pl.getName()).ifPresent(planNode -> {

                        Node[] nodes = new Node[]{builderNode, projectNode, planNode};
                        selectNodes(em, nodes);

                    });
                });
            });
        });

    }

    private Optional<Node> findNode(Node root, String childName) {
        return ofNullable(root.getChildren().findChild(childName));
    }

    private void selectNodes(ExplorerManager em, Node[] nodes) {

        try {
            em.setSelectedNodes(nodes);
        } catch (PropertyVetoException ex) {
            log.warning(ex.getMessage());
        }
    }

    Optional<TopComponent> findServicesTab() {
        return ofNullable(WindowManager.getDefault().findTopComponent(TAB_ID));
    }

}
