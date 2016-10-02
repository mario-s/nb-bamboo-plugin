package org.netbeans.modules.bamboo.ui.ext;

import org.junit.Before;
import org.junit.Test;

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

}
