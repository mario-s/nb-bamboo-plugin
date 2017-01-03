package org.netbeans.modules.bamboo.model.convert;

import static org.hamcrest.CoreMatchers.notNullValue;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.netbeans.modules.bamboo.model.rcp.IssueVo;
import org.netbeans.modules.bamboo.model.rest.Issue;
import org.netbeans.modules.bamboo.model.rest.Link;

/**
 *
 * @author spindizzy
 */
public class IssueVoConverterTest {
    private static final String FOO = "foo";
    
    private IssueVoConverter classUnderTest;
    
    private Issue source;
    
    @Before
    public void setUp() {
        classUnderTest = new IssueVoConverter();
        source = new Issue();
        source.setKey(FOO);
        Link link = new Link();
        link.setHref(FOO);
        source.setLink(link);
    }

    /**
     * Test of convert method, of class IssueVoConverter.
     */
    @Test
    public void testConvert_ExpectNotNull() {
        IssueVo result = classUnderTest.convert(source);
        assertThat(result, notNullValue());
    }
    
}
