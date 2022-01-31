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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit test for {@link DefaultInstanceValues}.
 * @author Mario Schroeder
 */
class DefaultInstanceValuesTest {
    
    private DefaultInstanceValues classUnderTest;
    
    @BeforeEach
    void setUp() {
        classUnderTest = new DefaultInstanceValues();
        classUnderTest.setToken(new char[]{'a'});
    }

    @Test
    @DisplayName("It should allow to create a new instance based on an existing one.") 
    void testCopyConstructor_NotNull() {
        var result = new DefaultInstanceValues(classUnderTest);
        assertNotNull(result.getToken(), "expected token by default");
    }
    
    @Test
    @DisplayName("It's copy should not be equal to the source if name was changed.")
    void testEquals_Name() {
        var result = new DefaultInstanceValues(classUnderTest);
        result.setName("name");
        assertNotEquals(result, classUnderTest);
    }
    
    @Test
    @DisplayName("It's copy should not be equal to the source if token was changed.")
    void testEquals_Token() {
        var result = new DefaultInstanceValues(classUnderTest);
        result.setToken("name".toCharArray());
        assertNotEquals(result, classUnderTest);
    }
   
    @Test
    @DisplayName("It should return the synchronization interval in milliseconds.")
    void testSyncInterval() {
        classUnderTest.setSyncInterval(3);
        assertNotEquals(3000, classUnderTest.getSyncIntervalInMillis());
    }
    
    @Test
    @DisplayName("It should not return null for char arrays.")
    void testNullChars() {
        assertNotNull(classUnderTest.getToken());
    }
    
    @Test
    @DisplayName("It should not return null for char arrays, even when we set them null.")
    void testSetNullChars() {
        classUnderTest.setToken(null);
        assertNotNull(classUnderTest.getToken());
    }
}
