package org.netbeans.modules.bamboo.ui.actions;

import javax.swing.Action;
import static org.hamcrest.CoreMatchers.equalTo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.netbeans.modules.bamboo.LookupContext;

import static org.mockito.Mockito.verify;

import org.netbeans.modules.bamboo.model.Queueable;
import org.openide.util.Lookup;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;

/**
 *
 * @author spindizzy
 */
@RunWith(MockitoJUnitRunner.class)
public class QueuePlanActionTest {
   
    @Mock
    private Queueable plan;

    private QueuePlanAction classUnderTest;

    @Before
    public void setUp() {
        Lookup lookup = LookupContext.Instance.getLookup();
        classUnderTest = new QueuePlanAction(lookup);
    }
    
    @After
    public void shutDown() {
        LookupContext.Instance.remove(plan);
    }
    
    @Test
    public void testGetName_ExpectBundle() {
        String name = (String) classUnderTest.getValue(Action.NAME);
        assertThat(name, equalTo(Bundle.CTL_QueuePlanAction()));
    }
    
    @Test
    public void testIsEnabled_NoPlan_ExpectFalse() {
        boolean result = classUnderTest.isEnabled();
        assertThat(result, is(false));
    }

    /**
     * Test of actionPerformed method, of class QueuePlanAction.
     */
    @Test
    public void testActionPerformed_PlanNotEnabled_ExpectNoCall() {
        classUnderTest.actionPerformed(null);
        verify(plan, never()).queue();
    }
    
     /**
     * Test of actionPerformed method, of class QueuePlanAction.
     */
    @Test
    public void testActionPerformed_PlanEnabled_ExpectCall() {
        
        given(plan.isAvailable()).willReturn(true);
        given(plan.isEnabled()).willReturn(true);
        LookupContext.Instance.add(plan);
        
        classUnderTest.actionPerformed(null);
        verify(plan).queue();
    }
    
     /**
     * Test of actionPerformed method, of class QueuePlanAction.
     */
    @Test
    public void testResultChanged_PlanEnabled_ExpectEnabled() {
        
        given(plan.isAvailable()).willReturn(true);
        given(plan.isEnabled()).willReturn(true);
        LookupContext.Instance.add(plan);
        
        boolean result = classUnderTest.isEnabled();
        assertThat(result, is(true));
    }

}
