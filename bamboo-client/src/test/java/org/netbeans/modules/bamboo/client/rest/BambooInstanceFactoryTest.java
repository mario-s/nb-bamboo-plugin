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

import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.netbeans.modules.bamboo.client.glue.BambooClientProduceable;
import org.netbeans.modules.bamboo.model.rcp.BambooInstance;
import org.netbeans.modules.bamboo.model.rcp.InstanceValues;
import org.netbeans.modules.bamboo.model.rcp.VersionInfo;
import org.netbeans.modules.bamboo.mock.MockBambooClientFactory;
import org.netbeans.modules.bamboo.model.rcp.ProjectVo;

import static java.util.Collections.singletonList;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.openide.util.Lookup.getDefault;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Matchers.any;


/**
 *
 * @author Mario Schroeder
 */
@RunWith(MockitoJUnitRunner.class)
public class BambooInstanceFactoryTest {
    @Mock
    private BambooClientProduceable delegate;
    @Mock
    private InstanceValues values;
    @Mock
    private AbstractBambooClient client;
    
    
    private DefaultBambooInstanceFactory classUnderTest;
    
    @Before
    public void setUp() {
        MockBambooClientFactory factory = (MockBambooClientFactory) getDefault().lookup(BambooClientProduceable.class);
        factory.setDelegate(delegate);
        
        given(delegate.newClient(any(InstanceValues.class))).willReturn(of(client));
        
        classUnderTest = new DefaultBambooInstanceFactory();
    }
    
     /**
     * Test of create method, of class DefaultBambooInstanceFactory.
     */
    @Test
    public void testCreate_ValidValues_ExpectInstanceWithVersionInfo() {
        VersionInfo versionInfo = new VersionInfo();
        versionInfo.setBuildNumber(1);
        given(client.getVersionInfo()).willReturn(versionInfo);
        
        Optional<BambooInstance> result = classUnderTest.create(values);
        assertThat(result.get().getVersionInfo().getBuildNumber(), is(1));
    }
    
       /**
     * Test of create method, of class DefaultBambooInstanceFactory.
     */
    @Test
    public void testCreate_ValidValues_ExpectInstanceAvailable() {
        Optional<BambooInstance> result = classUnderTest.create(values);
        assertThat(result.get().isAvailable(), is(true));
    }

    /**
     * Test of create method, of class DefaultBambooInstanceFactory.
     */
    @Test
    public void testCreate_ValidValues_ExpectInstanceWithProject() {
        given(client.getProjects()).willReturn(singletonList(new ProjectVo("")));
        
        Optional<BambooInstance> result = classUnderTest.create(values);
        assertThat(result.get().getChildren().isEmpty(), is(false));
    }
    
     /**
     * Test of create method, of class DefaultBambooInstanceFactory.
     */
    @Test
    public void testCreate_InvalidUrl_ExpectEmpty() {
        given(delegate.newClient(any(InstanceValues.class))).willReturn(empty());
        Optional<BambooInstance> result = classUnderTest.create(values);
        assertThat(result.isPresent(), is(false));
    }
    
}
