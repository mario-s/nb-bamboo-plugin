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
