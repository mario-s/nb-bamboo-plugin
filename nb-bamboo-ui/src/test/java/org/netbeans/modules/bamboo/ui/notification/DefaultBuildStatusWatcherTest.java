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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.netbeans.modules.bamboo.model.rcp.BambooInstance;
import org.openide.util.Lookup;

import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 *
 * @author Mario Schroeder
 */
@ExtendWith(MockitoExtension.class)
class DefaultBuildStatusWatcherTest {
    
    private DefaultBuildStatusWatcher classUnderTest;
    
    @Mock
    private BambooInstance instance;
    
    @BeforeEach
    void setUp() {
        classUnderTest = new DefaultBuildStatusWatcher();
        
        given(instance.getLookup()).willReturn(Lookup.getDefault());
    }

    /**
     * Test of addInstance method, of class DefaultBuildStatusWatcher.
     */
    @Test
    void testAddInstance_ExpectMapNotEmpty() {
        classUnderTest.addInstance(instance);
        assertFalse(classUnderTest.getNotifiers().isEmpty());
    }

    /**
     * Test of removeInstance method, of class DefaultBuildStatusWatcher.
     */
    @Test
    void testRemoveInstance_ExpectMapEmpty() {
        classUnderTest.addInstance(instance);
        classUnderTest.removeInstance(instance);
        assertTrue(classUnderTest.getNotifiers().isEmpty());
    }
    
}
