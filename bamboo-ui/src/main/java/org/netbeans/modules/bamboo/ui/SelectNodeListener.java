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
import org.netbeans.modules.bamboo.model.rcp.BambooInstance;
import org.netbeans.modules.bamboo.model.rcp.ProjectVo;
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

        pl.getParent().ifPresent(project -> {
            log.fine("project is present");
            project.getParent().ifPresent(instance -> {
                log.fine("instance is present");
                findNodes(em, instance, project, pl);
            });

        });

    }

    private void findNodes(ExplorerManager em, BambooInstance instance, ProjectVo project, PlanVo pl) {
        Node root = em.getRootContext();
        
        findNode(root, BAMBOO_NODE_NAME).ifPresent(builderNode -> {
            log.fine("builder node is present");

            findNode(builderNode, instance.getName()).ifPresent(instanceNode -> {
                log.fine("instance node is present");
                findNode(instanceNode, project.getName()).ifPresent(projectNode -> {
                    log.fine("project node is present");

                    findNode(projectNode, pl.getName()).ifPresent(planNode -> {
                        log.fine("plan node is present");

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
