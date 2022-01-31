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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 *
 * @author Mario Schroeder
 */
class BuildReasonEditorTest {

    private BuildReasonEditor classUnderTest;

    @BeforeEach
    public void setUp() {
        classUnderTest = new BuildReasonEditor();
    }

    /**
     * Test of supportsCustomEditor method, of class BuildReasonEditor.
     */
    @Test
    void testSupportsCustomEditor_ExpectTrue() {
        assertTrue(classUnderTest.supportsCustomEditor());
    }

    /**
     * Test of getCustomEditor method, of class BuildReasonEditor.
     */
    @Test
    void testGetCustomEditor_ExpectInstanceOfJEditor() {
        Component result = classUnderTest.getCustomEditor();
        assertTrue(result instanceof JPanel);
    }

    /**
     * Test of getAsText method, of class BuildReasonEditor.
     */
    @Test
    void testGetAsText_HtmlText_ExpectClearText() {
        String input = "<b>foo</b>";
        classUnderTest.setValue(input);
        assertEquals("foo", classUnderTest.getAsText());
    }
}
