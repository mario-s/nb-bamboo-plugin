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
package org.netbeans.modules.bamboo.client.glue;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 *
 * @author Mario Schroeder
 */
class HttpResponseCodeTest {

    /**
     * Test of getCode method, of class HttpResponseCode.
     */
    @Test
    void testGetCode_Unauthorized() {
        HttpResponseCode result = HttpResponseCode.getCode(401);
        assertEquals(HttpResponseCode.Unauthorized, result);
    }
    
     /**
     * Test of getCode method, of class HttpResponseCode.
     */
    @Test
    void testGetCode_Unknown() {
        HttpResponseCode result = HttpResponseCode.getCode(-10);
        assertEquals(HttpResponseCode.Unknown, result);
    }
}
