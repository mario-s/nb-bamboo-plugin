package org.netbeans.modules.bamboo.glue;

import org.junit.Before;
import org.junit.Test;
import org.netbeans.modules.bamboo.glue.VoConverter.PlanVoConverter;
import org.netbeans.modules.bamboo.glue.VoConverter.ProjectVoConverter;
import org.netbeans.modules.bamboo.glue.VoConverter.VersionInfoConverter;
import org.netbeans.modules.bamboo.model.PlanVo;
import org.netbeans.modules.bamboo.model.ProjectVo;
import org.netbeans.modules.bamboo.model.VersionInfo;
import org.netbeans.modules.bamboo.model.rest.Info;
import org.netbeans.modules.bamboo.model.rest.Plan;
import org.netbeans.modules.bamboo.model.rest.Project;

import static org.hamcrest.CoreMatchers.equalTo;
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
    public void testConvert() {
        VersionInfo result = classUnderTest.convert(source);
        assertThat(result.getVersion(), equalTo(FOO));
    }

}
