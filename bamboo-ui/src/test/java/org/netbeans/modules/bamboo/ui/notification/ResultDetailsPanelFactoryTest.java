package org.netbeans.modules.bamboo.ui.notification;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import org.netbeans.modules.bamboo.model.rcp.PlanVo;
import org.netbeans.modules.bamboo.model.rcp.ResultVo;

/**
 *
 * @author Mario Schroeder
 */
public class ResultDetailsPanelFactoryTest {
    private static final String FOO = "foo";
    
    private ResultDetailsPanelFactory classUnderTest;
    
    @Before
    public void setUp() {
        classUnderTest = new ResultDetailsPanelFactory();
    }

    /**
     * Test of create method, of class ResultDetailsPanelFactory.
     */
    @Test
    public void testCreate_StringString_ExpectPanel() {
        ResultDetailsPanel result = classUnderTest.create(FOO, FOO);
        assertThat(result, not(nullValue()));
    }

    /**
     * Test of create method, of class ResultDetailsPanelFactory.
     */
    @Test
    public void testCreate_StringBuildResult__ExpectPanel() {
        BuildResult buildResult = new BuildResult(new PlanVo(FOO), new ResultVo(), new ResultVo());
        ResultDetailsPanel result = classUnderTest.create(FOO, buildResult);
        assertThat(result, not(nullValue()));
    }
    
}
