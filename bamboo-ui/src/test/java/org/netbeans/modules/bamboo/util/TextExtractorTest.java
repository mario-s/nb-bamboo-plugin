package org.netbeans.modules.bamboo.util;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import org.junit.Ignore;

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
     * Test of extractLink method, of class TextExtractor.
     */
    @Test
    public void testExtractLink_EmptyString_ExpectEmpty() {
        String result = classUnderTest.extractLink("");
        assertEquals("", result);
    }

    /**
     * Test of extractLink method, of class TextExtractor.
     */
    @Test
    public void testExtractLink_FirstText_ExpectLink() {
        String result = classUnderTest.extractLink("test <a href=\"http://localhost\">test</a>");
        assertEquals("<a href=\"http://localhost\">test</a>", result);
    }

    /**
     * Test of extractLink method, of class TextExtractor.
     */
    @Test
    public void testExtractLink_LastText_ExpectLink() {
        String result = classUnderTest.extractLink("<a href=\"http://localhost\">test</a> test");
        assertEquals("<a href=\"http://localhost\">test</a>", result);
    }

    /**
     * Test of extractUrl method, of class TextExtractor.
     */
    @Test
    public void testExtractUrl_NotEmptyLink_ExpectUrl() {
        String result = classUnderTest.extractUrl("<a href=\"http://localhost\">test</a>");
        assertEquals("http://localhost", result);
    }
    
    /**
     * Test of extractUrl method, of class TextExtractor.
     */
    @Test
    public void testExtractUrl_InvalidLink_ExpectEmpty() {
        String result = classUnderTest.extractUrl("test</a>");
        assertEquals("", result);
    }

    /**
     * Test of extractNormalText method, of class TextExtractor.
     */
    @Test
    public void testExtractNormalText_EmptyString_expectEmpty() {
        String result = classUnderTest.extractNormalText("");
        assertEquals("", result);
    }
    
     /**
     * Test of extractNormalText method, of class TextExtractor.
     */
    @Test
    public void testExtractNormalText_EmbeddedString_expectString() {
        String result = classUnderTest.extractNormalText("<a href=\"http://localhost\">test 1</a>");
        assertEquals("test 1", result);
    }
    
      /**
     * Test of extractNormalText method, of class TextExtractor.
     */
    @Test
    public void testExtractNormalText_EmbeddedHtmlContent_expectString() {
        String result = classUnderTest.extractNormalText("<a href=\"http://localhost\">test <foo@bar.baz/></a>");
        assertEquals("test <foo@bar.baz/>", result);
    }

    /**
     * Test of substring method.
     */
    @Test
    public void testSubstring_ExpectOnlyFirst() {
        String foo = "foo";
        String bar = "bar";
        String complete = foo + bar;
        String result = classUnderTest.substring(complete, bar);
        assertThat(result, equalTo(foo));
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
    @Ignore("TODO")
    public void testRemove_Tags() {
        String text = "Manual run from: <b> Foo, Bar</b> by";;
    }
}
