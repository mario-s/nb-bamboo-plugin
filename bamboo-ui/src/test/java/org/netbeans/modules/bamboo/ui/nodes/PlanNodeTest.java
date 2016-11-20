package org.netbeans.modules.bamboo.ui.nodes;

import java.lang.reflect.InvocationTargetException;

import static org.hamcrest.CoreMatchers.is;

import org.junit.Before;
import org.junit.Test;
import org.netbeans.modules.bamboo.model.LifeCycleState;

import static org.junit.Assert.*;

import org.netbeans.modules.bamboo.model.PlanVo;
import org.netbeans.modules.bamboo.model.ResultVo;
import org.netbeans.modules.bamboo.model.State;
import org.openide.nodes.Node;
import org.openide.nodes.Node.Property;

/**
 *
 * @author spindizzy
 */
public class PlanNodeTest {

    private static final String FOO = "foo";

    private PlanVo plan;

    private PlanNode classUnderTest;

    @Before
    public void setUp() {
        plan = new PlanVo(FOO);
        plan.setShortName(FOO);
        ResultVo resultVo = new ResultVo(FOO);
        resultVo.setNumber(1);
        plan.setResult(resultVo);
        plan.setIgnore(true);
        classUnderTest = new PlanNode(plan);
    }

    /**
     * Test of getHtmlDisplayName method, of class PlanNode.
     */
    @Test
    public void testGetHtmlDisplayName_ExpectNotFinishedLifyCycle() {
        String htmlDisplayName = classUnderTest.getHtmlDisplayName();
        assertFalse(htmlDisplayName.contains(LifeCycleState.Finished.name()));
    }

    /**
     * Test of propertyChange method, of class PlanNode.
     */
    @Test
    public void testChange_Name() {
        ResultVo result = new ResultVo();
        result.setNumber(2);
        result.setState(State.Failed);
        plan.setResult(result);
        String htmlDisplayName = classUnderTest.getHtmlDisplayName();
        assertTrue(htmlDisplayName.contains(State.Failed.toString()));
    }
    
     /**
     * Test of propertyChange method, of class PlanNode.
     */
    @Test
    public void testChange_Watching() {
        String htmlDisplayName = classUnderTest.getHtmlDisplayName();
        assertTrue(htmlDisplayName.contains(Bundle.TXT_Plan_Not_Watched()));
    }

    /**
     * Test of propertyChange method, of class PlanNode.
     */
    @Test
    public void testChange_BuildNumber() throws IllegalAccessException, InvocationTargetException {

        Property[] oldProps = getProperties(0);
        Object oldValue = oldProps[1].getValue();

        ResultVo result = new ResultVo();
        result.setNumber(2);
        result.setState(State.Failed);
        plan.setResult(result);

        Property[] newProps = getProperties(0);

        Object newValue = newProps[1].getValue();

        assertThat(oldValue.equals(newValue), is(false));
    }

    private Property[] getProperties(int setIndex) {
        Node.PropertySet[] oldSets = classUnderTest.getPropertySets();
        Node.PropertySet oldPropSet = oldSets[setIndex];
        return oldPropSet.getProperties();
    }

}
