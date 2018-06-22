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
package org.netbeans.modules.bamboo.ui.actions;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import org.mockito.runners.MockitoJUnitRunner;
import org.netbeans.modules.bamboo.client.glue.InstancePropertiesDisplayable;

/**
 *
 * @author Mario Schroeder
 */
@RunWith(MockitoJUnitRunner.class)
public class AddInstanceActionTest {
    
    private AddInstanceAction classUnderTest;
    
    @Mock
    private InstancePropertiesDisplayable dialogDisplayer;
    
    @Before
    public void setUp() {
        classUnderTest = new AddInstanceAction(){
            @Override
            InstancePropertiesDisplayable createDialog() {
                return dialogDisplayer;
            }
        };
    }
    
    /**
     * Test of actionPerformed method, of class AddInstanceAction.
     */
    @Test
    public void testActionPerformed() {
        classUnderTest.actionPerformed(null);
        verify(dialogDisplayer).show();
    }
    
}
