package org.netbeans.modules.bamboo.ui.actions;

import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import org.netbeans.modules.bamboo.model.Queueable;

/**
 *
 * @author spindizzy
 */
@RunWith(MockitoJUnitRunner.class)
public class QueuePlanActionTest {

    private static final String FOO = "foo";

    @Mock
    private Queueable plan;

    private QueuePlanAction classUnderTest;

    @Before
    public void setUp() {

        classUnderTest = new QueuePlanAction(plan);
    }

    /**
     * Test of actionPerformed method, of class QueuePlanAction.
     */
    @Test
    public void testActionPerformed() {
        classUnderTest.actionPerformed(null);
        verify(plan).queue();
    }

}
