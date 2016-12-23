package org.netbeans.modules.bamboo.model.convert;

import org.junit.Before;
import org.junit.Test;
import org.netbeans.modules.bamboo.model.rcp.VersionInfo;
import org.netbeans.modules.bamboo.model.rest.Info;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;

/**
 *
 * @author spindizzy
 */
public class VersionInfoConverterTest {

    private static final String FOO = "foo";

    private VersionInfoConverter classUnderTest;

    private Info source;

    @Before
    public void setUp() {
        classUnderTest = new VersionInfoConverter();
        source = new Info();
        source.setVersion(FOO);
    }

    /**
     * Test of convert method, of class VoConverter.
     */
    @Test
    public void testConvert_BlankBuildDate_ExpectVersion() {
        VersionInfo result = classUnderTest.convert(source);
        assertThat(result.getVersion(), equalTo(FOO));
    }

    /**
     * Test of convert method, of class VoConverter.
     */
    @Test
    public void testConvert_WrongBuildDate_ExpectMissingDate() {
        source.setBuildDate(FOO);
        VersionInfo result = classUnderTest.convert(source);
        assertThat(result.getBuildDate(), nullValue());
    }
    
     /**
     * Test of convert method, of class VoConverter.
     */
    @Test
    public void testConvert_CorrectBuildDate_ExpectDate() {
        source.setBuildDate("2014-12-02T07:43:02.000+01:00");
        VersionInfo result = classUnderTest.convert(source);
        assertThat(result.getBuildDate(), notNullValue());
    }

}
