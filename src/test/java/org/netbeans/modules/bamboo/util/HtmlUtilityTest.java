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
     * Test of extractHtmlContent method, of class HtmlUtility.
     */
    @Test
    public void testExtractHtmlContent_EmptyString_ExpectEmpty() {
        String result = classUnderTest.extractHtmlContent("");
        assertEquals("", result);
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
