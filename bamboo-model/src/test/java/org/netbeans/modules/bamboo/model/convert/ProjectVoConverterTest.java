package org.netbeans.modules.bamboo.model.convert;

import org.junit.Before;
import org.junit.Test;
import org.netbeans.modules.bamboo.model.rcp.ProjectVo;
import org.netbeans.modules.bamboo.model.rest.Project;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

/**
 *
 * @author Mario Schroeder
 */
public class ProjectVoConverterTest {
    private static final String FOO = "foo";
    
    private ProjectVoConverter classUnderTest;
    
    private Project source;
    
    @Before
    public void setUp() {
        classUnderTest = new ProjectVoConverter(FOO);
        source = new Project();
        source.setKey(FOO);
    }

    /**
     * Test of convert method, of class VoConverter.
     */
    @Test
    public void testConvert() {
        ProjectVo result = classUnderTest.convert(source);
        assertThat(result.getKey(), equalTo(FOO));
    }

}
