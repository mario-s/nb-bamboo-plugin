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
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.netbeans.modules.bamboo.model.rcp.InstanceValues;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import org.mockito.InjectMocks;

import org.netbeans.modules.bamboo.client.glue.BambooClient;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 *
 * @author Mario Schroeder
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(HttpUtility.class)
public class BambooClientFactoryTest {
    private static final String FOO = "foo";
    
    @Mock
    private HttpUtility httpUtility;
    
    @Mock
    private InstanceValues values;
    
    @InjectMocks
    private DefaultBambooClientFactory classUnderTest;
    
    @Before
    public void setUp() {
        given(values.getUrl()).willReturn(FOO);
    }
    
    
    /**
     * Test of newClient method, of class DefaultBambooClientFactory.
     */
    @Test
    public void testNewClient_UrlExists_ExpectPresent() {
        given(httpUtility.exists(FOO)).willReturn(true);
        
        Optional<BambooClient> result = classUnderTest.newClient(values);
        assertThat(result.isPresent(), is(true));
    }
    
    /**
     * Test of newClient method, of class DefaultBambooClientFactory.
     */
    @Test
    public void testNewClient_UrlDoesNotExists_ExpectAbsent() {
        given(httpUtility.exists(FOO)).willReturn(false);
        
        Optional<BambooClient> result = classUnderTest.newClient(values);
        assertThat(result.isPresent(), is(false));
    }
    
}
