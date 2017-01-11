package org.netbeans.modules.bamboo.ui.notification;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.java.Log;
import org.netbeans.modules.bamboo.model.rcp.PlanVo;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.ExplorerManager.Provider;
import org.openide.nodes.Node;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;

import static java.util.Optional.ofNullable;

import java.util.logging.Level;

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
                String [] names = new String[]{BAMBOO_NODE_NAME, instance.getName(), project.getName(), pl.getName()};
                List<Node> nodes = findNodes(em, names);
                if(!nodes.isEmpty()) {
                    selectNodes(em, nodes);
                }
            });

        });

    }

    private List<Node> findNodes(ExplorerManager em, String [] names) {
        List<Node> nodes = new ArrayList<>(names.length);
        Node root = em.getRootContext();
        
        for(String name : names) {
            Optional<Node> child = findNode(root, name);
            if(child.isPresent()) {
                if(log.isLoggable(Level.FINE)) {
                    log.fine(String.format("found node: %s", name));
                }
                Node ch = child.get();
                nodes.add(ch);
                root = ch;
            }else{
                return nodes;
            }
        }
        
        return nodes;
    }

    private Optional<Node> findNode(Node root, String childName) {
        return ofNullable(root.getChildren().findChild(childName));
    }

    private void selectNodes(ExplorerManager em, List<Node> nodeList) {
        Node[] nodes = nodeList.toArray(new Node[nodeList.size()]);
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
