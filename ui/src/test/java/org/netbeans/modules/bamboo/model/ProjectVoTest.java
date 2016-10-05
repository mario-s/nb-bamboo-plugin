package org.netbeans.modules.bamboo.model;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;

/**
 *
 * @author spindizzy
 */
public class ProjectVoTest {
    
    private ProjectVo classUnderTest;

    
    @Before
    public void setUp() {
        classUnderTest = newInstance();
    }

    private ProjectVo newInstance() {
        ProjectVo instance = new ProjectVo("");
        
        List<PlanVo> plans = new ArrayList<>();
        PlanVo plan = new PlanVo("");
        plans.add(plan);
        instance.setPlans(plans);
                
        return instance;
    }

    /**
     * Test of equals method, of class ProjectVo.
     */
    @Test
    public void testEquals_True() {
        ProjectVo instance = newInstance();
        boolean result = classUnderTest.equals(instance);
        assertThat(result, is(true));
    }

     /**
     * Test of equals method, of class ProjectVo.
     */
    @Test
    public void testEqualsNewResult_True() {
        ProjectVo instance = newInstance();
        ResultVo resultVo = new ResultVo();
        resultVo.setNumber(1);
        instance.getPlans().get(0).setResult(resultVo);
        boolean result = classUnderTest.equals(instance);
        assertThat(result, is(true));
    }

}
