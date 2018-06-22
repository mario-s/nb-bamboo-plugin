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
package org.netbeans.modules.bamboo.ui.nodes;

import static org.hamcrest.CoreMatchers.equalTo;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.BDDMockito.given;

import org.openide.util.Lookup;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;

import java.util.ArrayList;
import java.util.List;
import org.netbeans.modules.bamboo.model.rcp.BambooInstance;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.mock;


/**
 * @author Mario Schroeder
 */
public class BambooInstanceNodeFactoryTest {
    
    private InstanceContent content;

    private BambooInstanceNodeFactory classUnderTest;

    @Before
    public void setUp() {
        content = new InstanceContent();

        Lookup lookup = new AbstractLookup(content);

        classUnderTest = new BambooInstanceNodeFactory(lookup);
    }
    
    private BambooInstance newInstance(String name) {
        BambooInstance instance = mock(BambooInstance.class);

        given(instance.getName()).willReturn(name);
        
        return instance;
    }

    /**
     * Test of createKeys method, of class BambooInstanceNodeFactory.
     */
    @Test
    public void testCreateKeys_ExpectSorted() {
        BambooInstance instance1 = newInstance("a");
        BambooInstance instance2 = newInstance("b");

        content.add(instance1);
        content.add(instance2);

        List<BambooInstance> toPopulate = new ArrayList<>();

        classUnderTest.createKeys(toPopulate);

        assertThat(toPopulate.get(0).getName(), equalTo("a"));
    }
    
    @Test
    public void testCreateKeys_WithLoadEvent() {
        classUnderTest.block();
        
        BambooInstance instance = newInstance("foo");
        
        content.add(instance);
        
        classUnderTest.resultChanged(null);
        
        List<BambooInstance> toPopulate = new ArrayList<>();

        classUnderTest.createKeys(toPopulate);
        assertThat(toPopulate.isEmpty(), is(false));
    }
}
