package org.netbeans.modules.bamboo.ui.nodes;

import static java.util.Collections.singletonList;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import static org.hamcrest.CoreMatchers.is;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import org.netbeans.modules.bamboo.glue.InstanceManageable;

import org.openide.nodes.Node;

import static org.openide.util.Lookup.getDefault;

import javax.swing.Action;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.internal.util.reflection.Whitebox.getInternalState;
import org.mockito.runners.MockitoJUnitRunner;
import org.netbeans.modules.bamboo.model.BambooInstance;
import org.netbeans.modules.bamboo.model.event.InstancesLoadEvent;


/**
 * @author spindizzy
 */
@RunWith(MockitoJUnitRunner.class)
public class BambooRootNodeTest {
    private BambooRootNode classUnderTest;

    private InstanceManageable manager;
    
    @Mock
    private BambooInstance instance;

    @Before
    public void setUp() {
        classUnderTest = new BambooRootNode(false);
        manager = getDefault().lookup(InstanceManageable.class);
    }

    /**
     * Test of getActions method, of class BambooRootNode.
     */
    @Test
    public void testGetActions() {
        Action[] result = classUnderTest.getActions(false);
        assertThat(result.length == 0, is(false));
    }

    @Test
    public void testCreateChild() {
        manager.getContent().add(instance);

        Node[] result = classUnderTest.getChildren().getNodes();
        assertThat(result.length, is(1));
    }
    
    @Test
    public void testResultChanged_ExpectBlockerInFactory() {
        InstancesLoadEvent loadEvent = new InstancesLoadEvent(singletonList(instance));
        manager.getContent().add(loadEvent);
        
        classUnderTest.resultChanged(null);
        
        BambooInstanceNodeFactory nodeFactory = (BambooInstanceNodeFactory) getInternalState(classUnderTest, "nodeFactory");
        Optional<CountDownLatch> blocker = (Optional<CountDownLatch>) getInternalState(nodeFactory, "blocker");
        
        assertThat(blocker.isPresent(), is(true));
    }
}
