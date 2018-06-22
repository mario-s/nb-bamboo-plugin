/* 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.netbeans.modules.bamboo.ui.notification;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.util.Optional;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import lombok.extern.java.Log;
import org.netbeans.modules.bamboo.model.rcp.PlanVo;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.ExplorerManager.Provider;
import org.openide.nodes.Node;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;

import static java.util.Optional.ofNullable;


import static org.netbeans.modules.bamboo.ui.RootNodeConstants.BAMBOO_NODE_NAME;
import org.openide.nodes.NodeNotFoundException;
import org.openide.nodes.NodeOp;

/**
 *
 * @author Mario Schroeder
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
                String[] names = new String[]{BAMBOO_NODE_NAME, instance.getName(), project.getName(), pl.getName()};
                findPath(em, names).ifPresent(node -> selectNode(em, node));
            });
        });
    }

    private Optional<Node> findPath(ExplorerManager em, String[] names) {
        Optional<Node> path = empty();
        Node root = em.getRootContext();
        
        try {
            path = of(NodeOp.findPath(root, names));
        } catch (NodeNotFoundException ex) {
            log.warning(ex.getMessage());
        }

        return path;
    }

    private void selectNode(ExplorerManager em, Node node) {
        
        try {
            em.setSelectedNodes(new Node[] {node});
        } catch (PropertyVetoException ex) {
            log.warning(ex.getMessage());
        }
    }

    Optional<TopComponent> findServicesTab() {
        return ofNullable(WindowManager.getDefault().findTopComponent(TAB_ID));
    }

}
