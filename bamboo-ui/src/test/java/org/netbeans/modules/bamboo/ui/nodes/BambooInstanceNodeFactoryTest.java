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
import java.util.Collection;
import java.util.List;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.netbeans.modules.bamboo.glue.InstanceManageable;
import org.netbeans.modules.bamboo.model.BambooInstance;
import org.netbeans.modules.bamboo.model.event.InstancesLoadEvent;

import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.mock;


/**
 * @author spindizzy
 */
@RunWith(MockitoJUnitRunner.class)
public class BambooInstanceNodeFactoryTest {
    
    @Mock
    private InstanceManageable manager;
    
    private InstanceContent content;

    private BambooInstanceNodeFactory classUnderTest;

    @Before
    public void setUp() {
        content = new InstanceContent();

        Lookup lookup = new AbstractLookup(content);
        given(manager.getLookup()).willReturn(lookup);

        classUnderTest = new BambooInstanceNodeFactory(manager);
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
        BambooInstance instance = newInstance("foo");
        InstancesLoadEvent loadEvent = new InstancesLoadEvent(singletonList(instance));
        
        content.add(loadEvent);
        content.add(instance);
        
        classUnderTest.resultChanged(null);
        
        List<BambooInstance> toPopulate = new ArrayList<>();

        classUnderTest.createKeys(toPopulate);
        assertThat(toPopulate.isEmpty(), is(false));
    }
}
