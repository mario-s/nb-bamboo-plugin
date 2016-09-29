package org.netbeans.modules.bamboo.util;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author spindizzy
 */
public class HtmlUtilityTest {

    private HtmlUtility classUnderTest;

    @Before
    public void setUp() {
        classUnderTest = new HtmlUtility();
    }

    /**
     * Test of extractLink method, of class HtmlUtility.
     */
    @Test
    public void testExtractLink_EmptyString_ExpectEmpty() {
        String result = classUnderTest.extractLink("");
        assertEquals("", result);
    }

    /**
     * Test of extractLink method, of class HtmlUtility.
     */
    @Test
    public void testExtractLink_FirstText_ExpectLink() {
        String result = classUnderTest.extractLink("test <a href=\"http://localhost\">test</a>");
        assertEquals("<a href=\"http://localhost\">test</a>", result);
    }
    
    /**
     * Test of extractLink method, of class HtmlUtility.
     */
    @Test
    public void testExtractLink_LastText_ExpectLink() {
        String result = classUnderTest.extractLink("<a href=\"http://localhost\">test</a> test");
        assertEquals("<a href=\"http://localhost\">test</a>", result);
    }

    /**
     * Test of extractNormalText method, of class HtmlUtility.
     */
    @Test
    public void testExtractNormalText_EmptyString_expectEmpty() {
        String result = classUnderTest.extractNormalText("");
        assertEquals("", result);
    }

}
