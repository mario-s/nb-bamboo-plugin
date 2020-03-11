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
package org.netbeans.modules.bamboo.ui.wizard;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.AbstractAction;
import javax.swing.event.DocumentEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.netbeans.modules.bamboo.client.glue.InstanceManageable;
import org.netbeans.modules.bamboo.mock.MockInstanceManager;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.openide.util.Lookup.getDefault;
import static org.mockito.Mockito.inOrder;

/**
 *
 * @author Mario Schroeder
 */
@ExtendWith(MockitoExtension.class)
class InstancePropertiesFormTest {
    
    private static final String FOO = "foo";
    
    @Mock
    private InstanceManageable delegate;

    @Mock
    private PropertyChangeListener listener;
    
    private PropertyChangeEvent event;

    @Mock
    private DocumentEvent docEvent;

    @Mock
    private AbstractAction applyAction;

    private InstancePropertiesForm classUnderTest;

    @BeforeEach
    void setUp() {
        MockInstanceManager manager
                = (MockInstanceManager) getDefault().lookup(InstanceManageable.class);
        manager.setDelegate(delegate);
        
        event = new PropertyChangeEvent(this, FOO, FOO, FOO);

        classUnderTest = new InstancePropertiesForm() {
            @Override
            void inform(String message) {
                listener.propertyChange(event);
            }

            @Override
            void error(String message) {
                listener.propertyChange(event);
            }

            @Override
            void clearMessages() {
            }
        };

        classUnderTest.setApplyAction(applyAction);
    }

    /**
     * Test of block method, of class InstancePropertiesForm.
     */
    @Test
    void testBlock_ExpectProgressVisisble() {
        classUnderTest.block();
        assertTrue(classUnderTest.getProgressBar().isVisible());
    }

    /**
     * Test of unblock method, of class InstancePropertiesForm.
     */
    @Test
    void testUnblock_ExpectProgressNotVisisble() {
        classUnderTest.unblock();
        assertFalse(classUnderTest.getProgressBar().isVisible());
    }

    /**
     * Test of setFocus method, of class InstancePropertiesForm.
     */
    @Test
    void testSetFocus() {
        classUnderTest.setFocus(0);
    }

    /**
     * Test of insertUpdate method, of class InstancePropertiesForm.
     */
    @Test
    void testInsertUpdate_NoName_ExpectActionDisabled() {
        classUnderTest.insertUpdate(docEvent);
        verify(applyAction).setEnabled(false);
        verify(listener).propertyChange(event);
    }

    /**
     * Test of removeUpdate method, of class InstancePropertiesForm.
     */
    @Test
    void testRemoveUpdate_NoName_ExpectActionDisabled() {
        classUnderTest.removeUpdate(docEvent);
        verify(applyAction).setEnabled(false);
    }

    /**
     * Test of insertUpdate method, of class InstancePropertiesForm.
     */
    @Test
    void testInsertUpdate_ExistingName_ExpectActionDisabled() {
        classUnderTest.getTxtName().setText(FOO);
        
        InOrder order = inOrder(applyAction, delegate, listener);
        order.verify(applyAction).setEnabled(false);
        order.verify(delegate).existsInstanceName(FOO);
        verify(listener).propertyChange(event);
    }
}
