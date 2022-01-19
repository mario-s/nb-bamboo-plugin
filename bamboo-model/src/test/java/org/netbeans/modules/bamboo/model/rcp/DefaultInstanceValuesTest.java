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
package org.netbeans.modules.bamboo.model.rcp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit test for {@link DefaultInstanceValues}.
 * @author Mario Schroeder
 */
class DefaultInstanceValuesTest {
    
    private DefaultInstanceValues classUnderTest;
    
    @BeforeEach
    void setUp() {
        classUnderTest = new DefaultInstanceValues();
        classUnderTest.setPassword(new char[]{'a'});
    }

    @Test
    @DisplayName("It should allow to create a new instance based on an existing one.") 
    void testCopyConstructor_NotNull() {
        var result = new DefaultInstanceValues(classUnderTest);
        assertEquals(1, result.getPassword().length);
    }

    @Test
    @DisplayName("It should allow to create a new instance without an existing reference.")
    void testCopyConstructor_Null() {
        var result = new DefaultInstanceValues(null);
        assertEquals(0, result.getPassword().length);
    }
    
    @Test
    @DisplayName("It's copy should not be equal to the source if a value was changed.")
    void testEquals() {
        var result = new DefaultInstanceValues(classUnderTest);
        result.setName("name");
        assertNotEquals(result, classUnderTest);
    }
   
    @Test
    @DisplayName("It should return the synchronization interval in milliseconds.")
    void testSyncInterval() {
        classUnderTest.setSyncInterval(3);
        assertNotEquals(3000, classUnderTest.getSyncIntervalInMillis());
    }
}
