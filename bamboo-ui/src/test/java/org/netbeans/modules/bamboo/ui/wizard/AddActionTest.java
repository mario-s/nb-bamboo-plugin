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

import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import static org.mockito.BDDMockito.given;

import org.mockito.Mock;

import static org.mockito.Mockito.verify;

import static org.mockito.internal.util.reflection.Whitebox.setInternalState;

import org.mockito.runners.MockitoJUnitRunner;

import org.netbeans.modules.bamboo.client.glue.InstanceManageable;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import static org.junit.Assert.assertFalse;
import org.mockito.InjectMocks;
import static org.netbeans.modules.bamboo.ui.wizard.Bundle.TXT_ADD;

/**
 * @author Mario Schroeder
 */
@RunWith(MockitoJUnitRunner.class)
public class AddActionTest {

    private static final String NAME = AddActionTest.class.getName();

    @Mock
    private InstanceManageable manager;
    @Mock
    private InstancePropertiesForm form;
    @Mock
    private AddInstanceWorker worker;
    @InjectMocks
    private AddAction classUnderTest;

    @Before
    public void setUp() {
        given(form.getInstanceName()).willReturn(NAME);
        
        setInternalState(classUnderTest, "instanceManager", manager);
        setInternalState(classUnderTest, "worker", worker);
    }

    /**
     * Test of actionPerformed method, of class AddAction.
     */
    @Test
    public void testActionPerformed_Ok() {
        given(form.getPassword()).willReturn(new char[]{'a'});
        ActionEvent event = new ActionEvent(this, 0, TXT_ADD());
        classUnderTest.actionPerformed(event);
        assertFalse(classUnderTest.isEnabled());
        verify(form).block();
    }
    
    @Test
    public void testPropertyChange_Cancel() {
        PropertyChangeEvent event = new PropertyChangeEvent(this, "test", null, -1);
        classUnderTest.propertyChange(event);
        verify(worker).cancel();
    }
}
