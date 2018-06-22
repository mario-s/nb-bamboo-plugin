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
package org.netbeans.modules.bamboo.client.rest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


/**
 * @author schroeder
 */
public class EncrypterTest {
    private Encrypter classUnderTest;

    @Before
    public void setUp() {
        classUnderTest = Encrypter.getInstance();
    }

    /**
     * Test of encrypt method, of class Encrypter.
     */
    @Test
    public void testEncrypt() {
        String str = "test";
        String result = classUnderTest.encrypt(str.toCharArray());
        assertFalse(result.equals(str));
    }

    /**
     * Test of encrypt method, of class Encrypter.
     */
    @Test
    public void testEncrypt_And_Decrypt() {
        String str = "test";
        String result = classUnderTest.decrypt(classUnderTest.encrypt(str));
        assertEquals(str, result);
    }
}
