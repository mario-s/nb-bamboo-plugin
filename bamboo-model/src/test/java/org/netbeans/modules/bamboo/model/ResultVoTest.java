package org.netbeans.modules.bamboo.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import static org.hamcrest.CoreMatchers.is;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author spindizzy
 */
public class ResultVoTest {
    
    private ResultVo classUnderTest;
    
    private LocalDateTime localDate;
    
    @Before
    public void setUp() {
        classUnderTest = new ResultVo();
        localDate = LocalDateTime.now();
    }

    /**
     * Test of getFormatedBuildStartedTime method, of class ResultVo.
     */
    @Test
    public void testGetFormatedBuildStartedTime() {
        classUnderTest.setBuildStartedTime(localDate);
        String result = classUnderTest.getFormatedBuildStartedTime();
        assertThat(result.isEmpty(), is(false));
    }

    /**
     * Test of getFormatedBuildCompletedTime method, of class ResultVo.
     */
    @Test
    public void testGetFormatedBuildCompletedTime() {
        classUnderTest.setBuildCompletedTime(localDate);
        String result = classUnderTest.getFormatedBuildCompletedTime();
        assertThat(result.isEmpty(), is(false));
    }

}
