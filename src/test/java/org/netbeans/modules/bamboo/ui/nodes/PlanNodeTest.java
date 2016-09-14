package org.netbeans.modules.bamboo.ui.nodes;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Matchers.contains;
import org.netbeans.modules.bamboo.model.PlanVo;
import org.netbeans.modules.bamboo.model.ResultVo;
import org.netbeans.modules.bamboo.model.State;

/**
 *
 * @author spindizzy
 */
public class PlanNodeTest {
    
    private PlanVo plan;
    
    private PlanNode classUnderTest;
    
    
    @Before
    public void setUp() {
        plan = new PlanVo();
        plan.setShortName("test");
        plan.setResult(new ResultVo());
        classUnderTest = new PlanNode(plan);
    }

    /**
     * Test of propertyChange method, of class PlanNode.
     */
    @Test
    public void testPropertyChange_ShouldChangeName() {
        ResultVo result = new ResultVo();
        result.setNumber(1);
        result.setState(State.Failed);
        plan.setResult(result);
        assertTrue(classUnderTest.getHtmlDisplayName().contains(State.Failed.toString()));
    }

    
}
