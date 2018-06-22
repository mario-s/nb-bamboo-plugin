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

import java.awt.Dialog;
import java.beans.PropertyChangeEvent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;

import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * @author schroeder
 */
@RunWith(MockitoJUnitRunner.class)
public class InstanceDialogTest {
    @Mock
    private Dialog dialog;
    
    @Mock
    private InstancePropertiesForm form;
    
    private InstanceDialog classUnderTest;
    
    @Before
    public void setUp() {
        classUnderTest = new InstanceDialog(form){
            @Override
            Dialog createDialog() {
                return dialog;
            }
        };
    }

    /**
     * Test of propertyChange method, of class InstanceDialog.
     */
    @Test
    public void testPropertyChange() {
        PropertyChangeEvent event = new PropertyChangeEvent(this, WorkerEvents.INSTANCE_CREATED.name(), null, null);
        classUnderTest.propertyChange(event);
    }

    /**
     * Test of show method, of class InstanceDialog.
     */
    @Test
    public void testShow() {
        classUnderTest.show();
        verify(dialog).setVisible(true);
    }
    
}
