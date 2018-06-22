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

import static org.hamcrest.CoreMatchers.is;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import org.junit.runner.RunWith;

import static org.mockito.BDDMockito.given;

import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.netbeans.modules.bamboo.model.rcp.BambooInstance;
import org.openide.util.Lookup;

/**
 *
 * @author Mario Schroeder
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultBuildStatusWatcherTest {
    
    private DefaultBuildStatusWatcher classUnderTest;
    
    @Mock
    private BambooInstance instance;
    
    @Before
    public void setUp() {
        classUnderTest = new DefaultBuildStatusWatcher();
        
        given(instance.getLookup()).willReturn(Lookup.getDefault());
    }

    /**
     * Test of addInstance method, of class DefaultBuildStatusWatcher.
     */
    @Test
    public void testAddInstance_ExpectMapNotEmpty() {
        classUnderTest.addInstance(instance);
        assertThat(classUnderTest.getNotifiers().isEmpty(), is(false));
    }

    /**
     * Test of removeInstance method, of class DefaultBuildStatusWatcher.
     */
    @Test
    public void testRemoveInstance_ExpectMapEmpty() {
        classUnderTest.addInstance(instance);
        classUnderTest.removeInstance(instance);
        assertThat(classUnderTest.getNotifiers().isEmpty(), is(true));
    }
    
}
