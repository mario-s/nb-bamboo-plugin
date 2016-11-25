package org.netbeans.modules.bamboo.ui.nodes;

import static org.hamcrest.CoreMatchers.equalTo;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.BDDMockito.given;

import static org.mockito.Mockito.mock;

import org.openide.util.Lookup;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;

import java.util.ArrayList;
import java.util.List;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.netbeans.modules.bamboo.glue.InstanceManageable;
import org.netbeans.modules.bamboo.model.BambooInstance;


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

    /**
     * Test of createKeys method, of class BambooInstanceNodeFactory.
     */
    @Test
    public void testCreateKeys_ExpectSorted() {
        BambooInstance plan1 = mock(BambooInstance.class);
        BambooInstance plan2 = mock(BambooInstance.class);

        given(plan1.getName()).willReturn("b");
        given(plan2.getName()).willReturn("a");

        content.add(plan1);
        content.add(plan2);

        List<BambooInstance> toPopulate = new ArrayList<>();

        classUnderTest.createKeys(toPopulate);

        assertThat(toPopulate.get(0).getName(), equalTo("a"));
    }
}
