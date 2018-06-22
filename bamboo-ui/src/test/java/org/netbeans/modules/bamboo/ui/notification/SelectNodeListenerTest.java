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

import java.util.Optional;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.BDDMockito.given;

import org.mockito.Mock;


import org.mockito.runners.MockitoJUnitRunner;
import org.netbeans.modules.bamboo.model.rcp.BambooInstance;
import org.netbeans.modules.bamboo.model.rcp.PlanVo;
import org.netbeans.modules.bamboo.model.rcp.ProjectVo;

import static org.netbeans.modules.bamboo.ui.RootNodeConstants.BAMBOO_NODE_NAME;

import org.openide.explorer.ExplorerManager;
import org.openide.nodes.IndexedNode;
import org.openide.nodes.Node;
import org.openide.windows.TopComponent;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

/**
 *
 * @author Mario Schroeder
 */
@RunWith(MockitoJUnitRunner.class)
public class SelectNodeListenerTest {

    private static final String FOO = "foo";

    private PlanVo plan;

    private ExplorerManager explorerManager;

    @Mock
    private ExplorerManager.Provider provider;

    @Mock
    private BambooInstance instance;

    @Mock
    private TopComponent servicesTab;

    private SelectNodeListener classUnderTest;

    @Before
    public void setUp() {
        plan = new PlanVo(FOO);
        plan.setName(FOO);

        ProjectVo project = new ProjectVo("FOO");
        project.setName(FOO);
        project.setParent(instance);

        plan.setParent(project);

        explorerManager = createExplorerManager();

        classUnderTest = new SelectNodeListener(plan) {
            @Override
            Optional<TopComponent> findServicesTab() {
                return Optional.of(servicesTab);
            }
        };
        
        given(instance.getName()).willReturn(FOO);

        given(provider.getExplorerManager()).willReturn(explorerManager);
    }


    private ExplorerManager createExplorerManager() {
        ExplorerManager em = new ExplorerManager();
        
        Node planNode = new IndexedNode();
        planNode.setName(FOO);

        Node projectNode = newNode(FOO, planNode);
        Node instanceNode = newNode(FOO, projectNode);
        Node builderNode = newNode(BAMBOO_NODE_NAME, instanceNode);

        Node root = new IndexedNode();
        root.getChildren().add(new Node[]{builderNode});

        em.setRootContext(root);

        return em;
    }
    
    private Node newNode(String name, Node child) {
        Node node = new IndexedNode();
        node.setName(name);
        node.getChildren().add(new Node[]{child});
        return node;
    }

    /**
     * Test of actionPerformed method, of class SelectNodeButton.
     */
    @Test
    public void testActionPerformed() {
        classUnderTest.actionPerformed(null);
        verify(servicesTab).requestActive();
    }

    @Test
    public void testSelectNodes_AllSameNames_ExpectOne() {
        classUnderTest.selectNodes(provider, plan);
        Node[] selected = explorerManager.getSelectedNodes();
        assertThat(selected.length, is(1));
    }

    @Test
    public void testSelectNodes_OneDifferentName_ExpectNone() {
        plan.setName("bar");
        classUnderTest.selectNodes(provider, plan);
        Node[] selected = explorerManager.getSelectedNodes();
        assertThat(selected.length, is(0));
    }
    
     @Test
    public void testSelectNodes_NoMatchingRoot_ExpectZero() {
        explorerManager.setRootContext(Node.EMPTY);
        classUnderTest.selectNodes(provider, plan);
        Node[] selected = explorerManager.getSelectedNodes();
        assertThat(selected.length, is(0));
    }
}
