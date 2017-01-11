package org.netbeans.modules.bamboo.ui;

import java.util.Optional;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.BDDMockito.given;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;

import org.mockito.runners.MockitoJUnitRunner;
import org.netbeans.modules.bamboo.model.rcp.BambooInstance;
import org.netbeans.modules.bamboo.model.rcp.PlanVo;
import org.netbeans.modules.bamboo.model.rcp.ProjectVo;
import static org.netbeans.modules.bamboo.ui.RootNodeConstants.BAMBOO_NODE_NAME;
import org.openide.explorer.ExplorerManager;
import org.openide.nodes.IndexedNode;
import org.openide.nodes.Node;
import org.openide.windows.TopComponent;

/**
 *
 * @author spindizzy
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
    public void testSelectNodes_AllSameNames_ExpectFour() {
        classUnderTest.selectNodes(provider, plan);
        Node[] selected = explorerManager.getSelectedNodes();
        assertThat(selected.length, is(4));
    }

    @Test
    public void testSelectNodes_OneFidderentName_ExpectThree() {
        plan.setName("bar");
        classUnderTest.selectNodes(provider, plan);
        Node[] selected = explorerManager.getSelectedNodes();
        assertThat(selected.length, is(3));
    }
}
