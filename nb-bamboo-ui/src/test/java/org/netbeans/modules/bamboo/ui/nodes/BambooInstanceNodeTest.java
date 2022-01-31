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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.List;
import javax.swing.Action;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.netbeans.modules.bamboo.model.rcp.BambooInstance;
import org.netbeans.modules.bamboo.model.rcp.ModelChangedValues;
import org.netbeans.modules.bamboo.model.event.ServerConnectionEvent;
import org.openide.util.lookup.InstanceContent;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.netbeans.modules.bamboo.client.glue.InstanceConstants.PROP_SYNC_INTERVAL;

import static org.netbeans.modules.bamboo.ui.nodes.Bundle.Unavailable;
import static org.netbeans.modules.bamboo.ui.nodes.Bundle.Disconnected;

/**
 *
 * @author Mario Schroeder
 */
@ExtendWith(MockitoExtension.class)
class BambooInstanceNodeTest {
    private static final String FOO = "foo";
    
    private InstanceContent content;

    @Mock
    private BambooInstance instance;
    @Mock
    private PropertyChangeEvent event;

    private BambooInstanceNode classUnderTest;

    @BeforeEach
    void setUp() {
        content = new InstanceContent();
        
        given(instance.getName()).willReturn(FOO);
        
        classUnderTest = new BambooInstanceNode(instance) {
            @Override
            protected List<? extends Action> findActions(String path) {
                return emptyList();
            }
        };
    }

    /**
     * Test of propertyChange method, of class BambooInstanceNode.
     */
    @Test
    void testPropertyChange_NoValidProperty_ExpectEmptyHtml() {
        classUnderTest.propertyChange(event);
        assertNull(classUnderTest.getHtmlDisplayName());
    }

    /**
     * Test of propertyChange method, of class BambooInstanceNode.
     */
    @Test
    void testPropertyChange_AvailabilityPropertyFalse_ExpectUnavailable() {
        given(instance.getSyncInterval()).willReturn(1);
        
        content.add(new ServerConnectionEvent(FOO, false));
        classUnderTest.resultChanged(null);
        String htmlDisplayName = classUnderTest.getHtmlDisplayName();
        assertTrue(htmlDisplayName.contains(Unavailable()));
    }
    
    /**
     * Test of propertyChange method, of class BambooInstanceNode.
     */
    @Test
    void testPropertyChange_SyncIntervalZero_ExpectDisconnected() {
        given(event.getPropertyName()).willReturn(PROP_SYNC_INTERVAL);
        given(instance.getSyncInterval()).willReturn(0);
        classUnderTest.propertyChange(event);
        String htmlDisplayName = classUnderTest.getHtmlDisplayName();
        assertTrue(htmlDisplayName.contains(Disconnected()));
    }

    /**
     * Test of propertyChange method, of class BambooInstanceNode.
     */
    @Test
    void testPropertyChange_AvailabilityPropertyTrue_ExpectEmptyHtml() {
        given(event.getPropertyName()).willReturn(ModelChangedValues.Available.toString());
        classUnderTest.propertyChange(event);
        assertNull(classUnderTest.getHtmlDisplayName());
    }
    
    @Test
    void testDestroy_ShouldStopSynchronization() throws IOException{
        classUnderTest.destroy();
        verify(instance).removePropertyChangeListener(any(PropertyChangeListener.class));
    }
}
