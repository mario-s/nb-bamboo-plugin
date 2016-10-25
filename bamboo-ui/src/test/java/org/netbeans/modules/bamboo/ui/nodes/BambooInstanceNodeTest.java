package org.netbeans.modules.bamboo.ui.nodes;

import java.beans.PropertyChangeEvent;
import java.util.List;
import javax.swing.Action;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.netbeans.modules.bamboo.model.BambooInstance;
import org.netbeans.modules.bamboo.model.ModelChangedValues;
import org.netbeans.modules.bamboo.model.event.ServerConnectionEvent;
import org.openide.util.Lookup;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;

import static java.util.Collections.emptyList;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;

/**
 *
 * @author spindizzy
 */
@RunWith(MockitoJUnitRunner.class)
public class BambooInstanceNodeTest {
    private static final String FOO = "foo";
    
    private InstanceContent content;

    @Mock
    private BambooInstance instance;
    @Mock
    private PropertyChangeEvent event;

    private BambooInstanceNode classUnderTest;

    @Before
    public void setUp() {
        content = new InstanceContent();
        
        Lookup lookup = new AbstractLookup(content);
        given(instance.getLookup()).willReturn(lookup);
        given(instance.getName()).willReturn(FOO);
        
        classUnderTest = new BambooInstanceNode(instance) {
            @Override
            protected List<? extends Action> findActions(String path) {
                return emptyList();
            }
        };
    }

    /**
     * Test of propertyChange method, of class BambooInstanceNode.
     */
    @Test
    public void testPropertyChange_NoValidProperty_ExpectEmptyHtml() {
        classUnderTest.propertyChange(event);
        String htmlDisplayName = classUnderTest.getHtmlDisplayName();
        assertThat(htmlDisplayName, not(notNullValue()));
    }

    /**
     * Test of propertyChange method, of class BambooInstanceNode.
     */
    @Test
    public void testPropertyChange_AvailabilityPropertyFalse_ExpectNoEmptyHtml() {
        content.add(new ServerConnectionEvent(FOO, false));
        classUnderTest.resultChanged(null);
        String htmlDisplayName = classUnderTest.getHtmlDisplayName();
        assertThat(htmlDisplayName, notNullValue());
    }

    /**
     * Test of propertyChange method, of class BambooInstanceNode.
     */
    @Test
    public void testPropertyChange_AvailabilityPropertyTrue_ExpectEmptyHtml() {
        given(event.getPropertyName()).willReturn(ModelChangedValues.Available.toString());
        given(instance.isAvailable()).willReturn(true);
        classUnderTest.propertyChange(event);
        String htmlDisplayName = classUnderTest.getHtmlDisplayName();
        assertThat(htmlDisplayName, not(notNullValue()));
    }
}
