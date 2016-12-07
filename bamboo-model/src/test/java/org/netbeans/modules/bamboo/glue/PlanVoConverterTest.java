package org.netbeans.modules.bamboo.glue;

import org.junit.Before;
import org.junit.Test;
import org.netbeans.modules.bamboo.glue.VoConverter.PlanVoConverter;
import org.netbeans.modules.bamboo.model.PlanVo;
import org.netbeans.modules.bamboo.model.rest.Plan;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

/**
 *
 * @author spindizzy
 */
public class PlanVoConverterTest {
    private static final String FOO = "foo";
    
    private PlanVoConverter classUnderTest;
    
    private Plan source;
    
    @Before
    public void setUp() {
        classUnderTest = new PlanVoConverter(FOO);
        source = new Plan();
        source.setKey(FOO);
        source.setName(FOO);
    }

    /**
     * Test of convert method, of class VoConverter.
     */
    @Test
    public void testConvert() {
        PlanVo result = classUnderTest.convert(source);
        assertThat(result.getKey(), equalTo(FOO));
    }

}
