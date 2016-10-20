package org.netbeans.modules.bamboo.rest;

import org.junit.Before;
import org.junit.Test;
import org.netbeans.modules.bamboo.model.LifeCycleState;
import org.netbeans.modules.bamboo.model.ResultVo;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

/**
 *
 * @author spindizzy
 */
public class QueuedResultFactoryTest {
    private static final String FOO = "foo";
    
    private ResultVo current;
    
    @Before
    public void setUp() {
        current = new ResultVo(FOO);
    }
    

    /**
     * Test of newResult method, of class QueuedResultFactory.
     */
    @Test
    public void testNewResult_ExpectNotEqual() {
        ResultVo result = QueuedResultFactory.newResult(current);
        assertThat(result, not(equalTo(current)));
    }
    
     /**
     * Test of newResult method, of class QueuedResultFactory.
     */
    @Test
    public void testNewResult_ExpectNewNumber() {
        ResultVo result = QueuedResultFactory.newResult(current);
        assertThat(result.getNumber(), is(1));
    }
    
     /**
     * Test of newResult method, of class QueuedResultFactory.
     */
    @Test
    public void testNewResult_ExpectQueed() {
        ResultVo result = QueuedResultFactory.newResult(current);
        assertThat(result.getLifeCycleState(), equalTo(LifeCycleState.Queued));
    }
    
}
