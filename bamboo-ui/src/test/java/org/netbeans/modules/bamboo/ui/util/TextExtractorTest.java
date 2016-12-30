package org.netbeans.modules.bamboo.ui.util;

import java.util.List;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;


/**
 *
 * @author spindizzy
 */
public class TextExtractorTest {

    private TextExtractor classUnderTest;

    @Before
    public void setUp() {
        classUnderTest = new TextExtractor();
    }

    /**
     * Test of containsLink method, of class TextExtractor.
     */
    @Test
    public void testContainsLink_HtmlString_ExpectTrue() {
        String text = "test <a href=\"http://localhost\">test</a>";
        boolean result = classUnderTest.containsLink(text);
        assertThat(result, is(true));
    }

    /**
     * Test of containsLink method, of class TextExtractor.
     */
    @Test
    public void testContainsLink_NormalString_ExpectFalse() {
        String text = "test";
        boolean result = classUnderTest.containsLink(text);
        assertThat(result, is(false));
    }

    /**
     * Test to remove all tags
     */
    @Test
    public void testRemoveTags_SimpleLink_ShouldBeNoMarkup() {
        String text = "Manual run from: <b>Foo, Bar</b> <a href=\"https://localhost\">by</a>";
        String expected = "Manual run from: Foo, Bar by";
        String result = classUnderTest.removeTags(text);
        assertThat(result, equalTo(expected));
    }
    
    /**
     * Test to remove all tags
     */
    @Test
    public void testRemoveTags_LongLink_ShouldBeNoMarkup() {
        String text = "Changes by <a href=\"http://192.168.99.100:32771/authors/viewAuthor.action?authorName=Foo-BarS%20%3Cfoo%40bar.com%3E\">Foo-Bar &lt;foo@bar.com&gt;</a>";
        String expected = "Changes by <foo@bar.com>";
        String result = classUnderTest.removeTags(text);
        assertThat(result, equalTo(expected));
    }

    @Test
    public void testFindAllLinks_TwoLinks_ExpectTwo() {
        String text = "test <a href=\"http://localhost\">test</a> and <a href=\"https://localhost\">test</a>";
        List<String> result = classUnderTest.extractLinks(text);
        assertThat(result.size(), is(2));
    }

    @Test
    public void testFindAllLinks_Empty_ExpectEmpty() {
        String text = "";
        List<String> result = classUnderTest.extractLinks(text);
        assertThat(result.isEmpty(), is(true));
    }
    
    @Test
    public void testreplaceLinksWithText_TwoLinks_ExpectNone() {
        String text = "test <b/> <a href=\"http://localhost\">test</a> and <a href=\"https://localhost\">test</a>";
        String result = classUnderTest.replaceLinksWithText(text);
        String expected = "test <b/> test and test";
        assertThat(result, equalTo(expected));
    }
}
