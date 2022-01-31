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

import org.netbeans.modules.bamboo.client.glue.InstanceManageable;

import org.openide.nodes.Node;

import static org.openide.util.Lookup.getDefault;

import javax.swing.Action;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.netbeans.modules.bamboo.model.rcp.BambooInstance;
import org.netbeans.modules.bamboo.model.event.InstancesLoadEvent;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * @author Mario Schroeder
 */
@ExtendWith(MockitoExtension.class)
class BambooRootNodeTest {
    private BambooRootNode classUnderTest;

    private InstanceManageable manager;
    
    @Mock
    private BambooInstance instance;

    @BeforeEach
    void setUp() {
        classUnderTest = new BambooRootNode(false);
        manager = getDefault().lookup(InstanceManageable.class);
    }

    /**
     * Test of getActions method, of class BambooRootNode.
     */
    @Test
    void testGetActions() {
        Action[] result = classUnderTest.getActions(false);
        assertFalse(result.length == 0);
    }

    @Test
    void testCreateChild() {
        manager.getContent().add(instance);

        Node[] result = classUnderTest.getChildren().getNodes();
        assertEquals(1, result.length);
    }
    
    @Test
    void testResultChanged_ExpectBlockerInFactory() {
        InstancesLoadEvent loadEvent = new InstancesLoadEvent(singletonList(instance));
        manager.getContent().add(loadEvent);
        
        classUnderTest.resultChanged(null);
        
        BambooInstanceNodeFactory nodeFactory = (BambooInstanceNodeFactory) ReflectionTestUtils.getField(classUnderTest, "nodeFactory");
        Optional<CountDownLatch> blocker = (Optional<CountDownLatch>) ReflectionTestUtils.getField(nodeFactory, "blocker");
        
        assertTrue(blocker.isPresent());
    }
}
