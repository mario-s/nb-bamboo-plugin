package org.netbeans.modules.bamboo.ui.nodes;

import static org.hamcrest.CoreMatchers.equalTo;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.BDDMockito.given;

import static org.mockito.Mockito.mock;

import org.netbeans.modules.bamboo.glue.ProjectsProvideable;

import org.openide.util.Lookup;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;

import java.util.ArrayList;
import java.util.List;


/**
 * @author spindizzy
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

    /**
     * Test of createKeys method, of class BambooInstanceNodeFactory.
     */
    @Test
    public void testCreateKeys_ExpectSorted() {
        ProjectsProvideable plan1 = mock(ProjectsProvideable.class);
        ProjectsProvideable plan2 = mock(ProjectsProvideable.class);

        given(plan1.getName()).willReturn("b");
        given(plan2.getName()).willReturn("a");

        content.add(plan1);
        content.add(plan2);

        List<ProjectsProvideable> toPopulate = new ArrayList<>();

        classUnderTest.createKeys(toPopulate);

        assertThat(toPopulate.get(0).getName(), equalTo("a"));
    }
}
