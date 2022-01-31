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
package org.netbeans.modules.bamboo.ui.util;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 *
 * @author Mario Schroeder
 */
class TextExtractorTest {

    private TextExtractor classUnderTest;

    @BeforeEach
    void setUp() {
        classUnderTest = new TextExtractor();
    }

    /**
     * Test of containsLink method, of class TextExtractor.
     */
    @Test
    void testContainsLink_HtmlString_ExpectTrue() {
        String text = "test <a href=\"http://localhost\">test</a>";
        assertTrue(classUnderTest.containsLink(text));
    }

    /**
     * Test of containsLink method, of class TextExtractor.
     */
    @Test
    void testContainsLink_NormalString_ExpectFalse() {
        String text = "test";
        assertFalse(classUnderTest.containsLink(text));
    }

    /**
     * Test to remove all tags
     */
    @Test
    void testRemoveTags_SimpleLink_ShouldBeNoMarkup() {
        String text = "Manual run from: <b>Foo, Bar</b> <a href=\"https://localhost\">by</a>";
        String expected = "Manual run from: Foo, Bar by";
        String result = classUnderTest.removeTags(text);
        assertEquals(expected, result);
    }
    
    /**
     * Test to remove all tags
     */
    @Test
    void testRemoveTags_LongLink_ShouldBeNoMarkup() {
        String text = "Changes by <a href=\"http://192.168.99.100:32771/authors/viewAuthor.action?authorName=Foo-BarS%20%3Cfoo%40bar.com%3E\">Foo-Bar &lt;foo@bar.com&gt;</a>";
        String expected = "Changes by Foo-Bar <foo@bar.com>";
        String result = classUnderTest.removeTags(text);
        assertEquals(expected, result);
    }

    @Test
    void testFindAllLinks_TwoLinks_ExpectTwo() {
        String text = "test <a href=\"http://localhost\">test</a> and <a href=\"https://localhost\">test</a>";
        List<String> result = classUnderTest.extractLinks(text);
        assertEquals(2, result.size());
    }

    @Test
    void testFindAllLinks_Empty_ExpectEmpty() {
        String text = "";
        List<String> result = classUnderTest.extractLinks(text);
        assertTrue(result.isEmpty());
    }
    
    @Test
    void testreplaceLinksWithText_TwoLinks_ExpectNone() {
        String text = "test <b/> <a href=\"http://localhost\">test</a> and <a href=\"https://localhost\">test</a>";
        String result = classUnderTest.replaceLinksWithText(text);
        String expected = "test <b/> test and test";
        assertEquals(expected, result);
    }
}
