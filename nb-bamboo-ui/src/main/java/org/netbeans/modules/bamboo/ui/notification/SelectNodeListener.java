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

import org.netbeans.modules.bamboo.model.rcp.PlanVo;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.ExplorerManager.Provider;
import org.openide.nodes.Node;
import org.openide.nodes.NodeNotFoundException;
import org.openide.nodes.NodeOp;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.util.Optional;

import static java.util.Optional.*;
import static org.netbeans.modules.bamboo.ui.RootNodeConstants.BAMBOO_NODE_NAME;

/**
 * Listener for a selection of a node.
 *
 * @author Mario Schroeder
 */
class SelectNodeListener implements ActionListener {

    private static final Logger LOG = LoggerFactory.getLogger(SelectNodeListener.class);

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
            LOG.debug("project is present");
            project.getParent().ifPresent(instance -> {
                LOG.debug("instance is present");
                String[] names = new String[]{BAMBOO_NODE_NAME, instance.getName(), project.getName(), pl.getName()};
                try {
                    em.setSelectedNodes(new Node[] {findPath(em, names)});
                } catch (NodeNotFoundException | PropertyVetoException ex) {
                    LOG.warn(ex.getMessage(), ex);
                }
            });
        });
    }

    private Node findPath(ExplorerManager em, String[] names) throws NodeNotFoundException {
        Optional<Node> path = empty();
        Node root = em.getRootContext();
        return NodeOp.findPath(root, names);
    }

    Optional<TopComponent> findServicesTab() {
        return ofNullable(WindowManager.getDefault().findTopComponent(TAB_ID));
    }

}
