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
package org.netbeans.modules.bamboo.ui.util;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

import static java.time.LocalDateTime.now;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 *
 * @author Mario Schroeder
 */
class DateFormatterTest {
    
    /**
     * Test of format method, of class DateFormatter.
     */
    @Test
    void testFormat_Null_ExpectEmptyString() {
        LocalDateTime src = null;
        String result = DateFormatter.format(src);
        assertTrue(result.isEmpty());
    }
    
     /**
     * Test of format method, of class DateFormatter.
     */
    @Test
    void testFormat_Now_ExpectNonEmptyString() {
        LocalDateTime src = now();
        String result = DateFormatter.format(src);
        assertFalse(result.isEmpty());
    }
    
}
