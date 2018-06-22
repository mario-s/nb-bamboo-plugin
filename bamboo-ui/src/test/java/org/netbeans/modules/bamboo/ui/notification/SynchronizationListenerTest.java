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
package org.netbeans.modules.bamboo.ui.notification;

import java.beans.PropertyChangeEvent;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.modules.bamboo.model.rcp.BambooInstance;
import org.netbeans.modules.bamboo.model.rcp.ModelChangedValues;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.internal.util.reflection.Whitebox.getInternalState;

/**
 *
 * @author Mario Schroeder
 */
@RunWith(MockitoJUnitRunner.class)
public class SynchronizationListenerTest {
    @Mock
    private BambooInstance instance;
    
    private SynchronizationListener classUnderTest;
    
    @Before
    public void setUp() {
        classUnderTest = new SynchronizationListener(instance);
    }

    /**
     * Test of propertyChange method, of class SynchronizationListener.
     */
    @Test
    public void testPropertyChange_StartSynchronize() {
        PropertyChangeEvent event = newChangeEvent(true);
        classUnderTest.propertyChange(event);
        Optional<ProgressHandle> opt = getProgressHandle();
        assertThat(opt.isPresent(), is(true));
    }

    private PropertyChangeEvent newChangeEvent(boolean val) {
        return new PropertyChangeEvent(this, ModelChangedValues.Synchronizing.toString(), !val, val);
    }
    
      /**
     * Test of propertyChange method, of class SynchronizationListener.
     */
    @Test
    public void testPropertyChange_StartAndStopSynchronize() {
        PropertyChangeEvent event = newChangeEvent(true);
        classUnderTest.propertyChange(event);
        event = newChangeEvent(false);
        classUnderTest.propertyChange(event);
        Optional<ProgressHandle> opt = getProgressHandle();
        assertThat(opt.isPresent(), is(false));
    }

    private Optional<ProgressHandle> getProgressHandle() {
        return (Optional<ProgressHandle>) getInternalState(classUnderTest, "progressHandle");
    }
    
}
