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

import org.netbeans.modules.bamboo.client.glue.HttpUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.netbeans.modules.bamboo.model.rcp.InstanceValues;

import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;

/**
 *
 * @author Mario Schroeder
 */
@ExtendWith(MockitoExtension.class)
class BambooClientFactoryTest {
    private static final String FOO = "foo";
    
    @Mock
    private HttpUtility httpUtility;
    
    @Mock
    private InstanceValues values;
    
    @InjectMocks
    private DefaultBambooClientFactory classUnderTest;
    
    @BeforeEach
    void setUp() {
        given(values.getUrl()).willReturn(FOO);
    }
    
    
    /**
     * Test of newClient method, of class DefaultBambooClientFactory.
     */
    @Test
    void testNewClient_UrlExists_ExpectPresent() {
        given(httpUtility.exists(FOO)).willReturn(true);
        
        assertTrue(classUnderTest.newClient(values).isPresent());
    }
    
    /**
     * Test of newClient method, of class DefaultBambooClientFactory.
     */
    @Test
    void testNewClient_UrlDoesNotExists_ExpectAbsent() {
        given(httpUtility.exists(FOO)).willReturn(false);
        
        assertFalse(classUnderTest.newClient(values).isPresent());
    }
    
}
