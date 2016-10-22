package org.netbeans.modules.bamboo.glue;

import org.junit.Before;
import org.junit.Test;
import org.netbeans.modules.bamboo.glue.VoConverter.ResultVoConverter;
import org.netbeans.modules.bamboo.model.ResultVo;
import org.netbeans.modules.bamboo.model.rest.Result;

import static org.hamcrest.CoreMatchers.equalTo;
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
    }

    /**
     * Test of convert method, of class VoConverter.
     */
    @Test
    public void testConvert() {
        ResultVo result = classUnderTest.convert(source);
        assertThat(result.getKey(), equalTo(FOO));
    }

}
