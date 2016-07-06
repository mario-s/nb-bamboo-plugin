package org.netbeans.modules.bamboo.ui.nodes;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import static org.mockito.BDDMockito.given;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.netbeans.modules.bamboo.glue.PlansProvideable;
import org.netbeans.modules.bamboo.rest.model.Plan;
import org.openide.nodes.Node;

/**
 *
 * @author spindizzy
 */
@RunWith(MockitoJUnitRunner.class)
public class PlanNodeFactoryTest {

    @Mock
    private PlansProvideable plansProvideable;
    
    private PlanNodeFactory classUnderTest;
    
    private Plan plan;
    
    @Before
    public void setUp() {
        plan = new Plan();
        List<Plan> plans = new ArrayList<>();
        plans.add(plan);
        given(plansProvideable.getPlans()).willReturn(plans);
        
        classUnderTest = new PlanNodeFactory(plansProvideable);
    }

    /**
     * Test of createNodeForKey method, of class PlanNodeFactory.
     */
    @Test
    public void testCreateNodeForKey() {
        Node result = classUnderTest.createNodeForKey(plan);
        assertNotNull(result);
    }

    /**
     * Test of createKeys method, of class PlanNodeFactory.
     */
    @Test
    public void testCreateKeys() {
        List<Plan> toPopulate = new ArrayList<>();
        classUnderTest.createKeys(toPopulate);
        assertFalse(toPopulate.isEmpty());
    }
    
}
