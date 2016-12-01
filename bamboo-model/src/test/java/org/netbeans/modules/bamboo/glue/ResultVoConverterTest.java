package org.netbeans.modules.bamboo.glue;

import org.junit.Before;
import org.junit.Test;
import org.netbeans.modules.bamboo.glue.VoConverter.ResultVoConverter;
import org.netbeans.modules.bamboo.model.ResultVo;
import org.netbeans.modules.bamboo.model.rest.Result;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;

/**
 *
 * @author spindizzy
 */
public class ResultVoConverterTest {

    private static final String FOO = "foo";

    private ResultVoConverter classUnderTest;

    private Result source;

    @Before
    public void setUp() {
        classUnderTest = new ResultVoConverter();
        source = new Result();
        source.setKey(FOO);
        source.setBuildStartedTime("2016-12-02T07:43:02.000+01:00");
        source.setBuildCompletedTime("2016-12-02T07:43:02.000+01:00");
    }

    /**
     * Test of convert method, of class VoConverter.
     */
    @Test
    public void testConvert_ExpectKey() {
        ResultVo result = classUnderTest.convert(source);
        assertThat(result.getKey(), equalTo(FOO));

    }

    /**
     * Test of convert method, of class VoConverter.
     */
    @Test
    public void testConvert_ExpectCompletionTime() {
        ResultVo result = classUnderTest.convert(source);
        assertThat(result.getBuildCompletedTime(), notNullValue());
    }

    /**
     * Test of convert method, of class VoConverter.
     */
    @Test
    public void testConvert_ExpectStartTime() {
        ResultVo result = classUnderTest.convert(source);
        assertThat(result.getBuildStartedTime(), notNullValue());
    }

}
