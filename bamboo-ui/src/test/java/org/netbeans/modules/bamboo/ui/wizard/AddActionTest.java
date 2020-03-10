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


import org.mockito.Mock;


import org.netbeans.modules.bamboo.client.glue.InstanceManageable;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.verify;
import static org.netbeans.modules.bamboo.ui.wizard.Bundle.TXT_ADD;

/**
 * @author Mario Schroeder
 */
@ExtendWith(MockitoExtension.class)
class AddActionTest {

    @Mock
    private InstanceManageable manager;
    @Mock
    private InstancePropertiesForm form;
    @Mock
    private AddInstanceWorker worker;
    @InjectMocks
    private AddAction classUnderTest;

    @BeforeEach
    void setUp() {        
        ReflectionTestUtils.setField(classUnderTest, "instanceManager", manager);
        ReflectionTestUtils.setField(classUnderTest, "worker", worker);
    }

    /**
     * Test of actionPerformed method, of class AddAction.
     */
    @Test
    void testActionPerformed_Ok() {
        ActionEvent event = new ActionEvent(this, 0, TXT_ADD());
        classUnderTest.actionPerformed(event);
        assertFalse(classUnderTest.isEnabled());
        verify(form).block();
    }
    
    @Test
    void testPropertyChange_Cancel() {
        PropertyChangeEvent event = new PropertyChangeEvent(this, "test", null, -1);
        classUnderTest.propertyChange(event);
        verify(worker).cancel();
    }
}
