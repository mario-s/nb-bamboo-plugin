package org.netbeans.modules.bamboo.ui.nodes;

import java.awt.Component;
import javax.swing.JPanel;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 *
 * @author Mario Schroeder
 */
public class BuildReasonEditorTest {

    private BuildReasonEditor classUnderTest;

    @Before
    public void setUp() {
        classUnderTest = new BuildReasonEditor();
    }

    /**
     * Test of supportsCustomEditor method, of class BuildReasonEditor.
     */
    @Test
    public void testSupportsCustomEditor_ExpectTrue() {
        assertThat(classUnderTest.supportsCustomEditor(), is(true));
    }

    /**
     * Test of getCustomEditor method, of class BuildReasonEditor.
     */
    @Test
    public void testGetCustomEditor_ExpectInstanceOfJEditor() {
        Component result = classUnderTest.getCustomEditor();
        assertThat(result instanceof JPanel, is(true));
    }

    /**
     * Test of getAsText method, of class BuildReasonEditor.
     */
    @Test
    public void testGetAsText_HtmlText_ExpectClearText() {
        String input = "<b>foo</b>";
        classUnderTest.setValue(input);
        assertThat(classUnderTest.getAsText(), equalTo("foo"));
    }
}
