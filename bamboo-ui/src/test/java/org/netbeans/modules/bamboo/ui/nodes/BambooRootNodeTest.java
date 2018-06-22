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
package org.netbeans.modules.bamboo.ui.nodes;

import static java.util.Collections.singletonList;

import java.util.Optional;
import java.util.concurrent.CountDownLatch;

import static org.hamcrest.CoreMatchers.is;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import org.netbeans.modules.bamboo.client.glue.InstanceManageable;

import org.openide.nodes.Node;

import static org.openide.util.Lookup.getDefault;

import javax.swing.Action;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static org.mockito.internal.util.reflection.Whitebox.getInternalState;

import org.mockito.runners.MockitoJUnitRunner;
import org.netbeans.modules.bamboo.model.rcp.BambooInstance;
import org.netbeans.modules.bamboo.model.event.InstancesLoadEvent;


/**
 * @author Mario Schroeder
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
