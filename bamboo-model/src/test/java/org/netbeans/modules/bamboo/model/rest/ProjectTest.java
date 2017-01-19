package org.netbeans.modules.bamboo.model.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import static org.hamcrest.CoreMatchers.is;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Mario Schroeder
 */
public class ProjectTest {
    private static final String FOO = "foo";
    
    private Project classUnderTest;
    
    @Before
    public void setUp() {
        classUnderTest = new Project();
        classUnderTest.setKey(FOO);
        classUnderTest.setName(FOO);
    }

    @Test
    public void testEquals_SameInstance() {
        assertThat(classUnderTest.equals(classUnderTest), is(true));
    }
    
    @Test
    public void testEquals_DifferentInstance() {
        assertThat(classUnderTest.equals(createOther()), is(true));
    }

    private Project createOther() {
        Project other = new Project();
        other.setKey(FOO);
        other.setName(FOO);
        Plans plans = new Plans();
        
        List<Plan> planList = new ArrayList<>();
        Plan plan = new Plan();
        planList.add(plan);
        plans.setPlan(planList);
        
        Result result = new Result();
        result.setNumber(1);
        plan.setResult(result);
        
        other.setPlans(plans);
        return other;
    }

    
    @Test
    public void testPlansAsCollection() {
        Collection<Plan> plans = classUnderTest.plansAsCollection();
        assertThat(plans.isEmpty(), is(true));
    }

    
    
}
