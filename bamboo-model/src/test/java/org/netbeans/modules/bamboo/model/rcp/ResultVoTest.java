package org.netbeans.modules.bamboo.model.rcp;

import java.time.LocalDateTime;


import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

/**
 *
 * @author spindizzy
 */
public class ResultVoTest {
    
    private ResultVo classUnderTest;
    
    private LocalDateTime now;
    
    @Before
    public void setUp() {
        classUnderTest = new ResultVo();
        now = LocalDateTime.now();
    }

    /**
     * Test of getBuildStartedTime method, of class ResultVo.
     */
    @Test
    public void testGetBuildStartedTime() {
        classUnderTest.setBuildCompletedTime(now);
        LocalDateTime result = classUnderTest.getBuildCompletedTime();
        assertThat(result, equalTo(now));
    }

}
