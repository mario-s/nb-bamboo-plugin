package org.netbeans.modules.bamboo.rest;

import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.netbeans.modules.bamboo.model.PlanVo;
import org.netbeans.modules.bamboo.model.ResultVo;
import org.netbeans.modules.bamboo.rest.AbstractVoUpdater.PlansUpdater;

/**
 *
 * @author spindizzy
 */
public class PlansUpdaterTest {
    private static final String FOO = "foo";
    private static final String BAR = "bar";
    
    private PlansUpdater classUnderTest;
    
    private List<PlanVo> source;
    
    private List<PlanVo> target;
    
    @Before
    public void setUp() {
        classUnderTest = new PlansUpdater();
        source = new ArrayList<>();
        target = new ArrayList<>();
    }

    /**
     * Test of update method, of class ProjectsUpdater.
     */
    @Test
    public void testUpdate_SameContent_NoChange() {
        PlanVo left = new PlanVo(FOO, FOO);
        PlanVo right = new PlanVo(FOO, FOO);
        
        source.add(left);
        target.add(right);
        
        classUnderTest.update(source, target);

        assertThat(target.get(0), equalTo(right));
    }
    
    /**
     * Test of update method, of class ProjectsUpdater.
     */
    @Test
    public void testUpdate_DifferentContent_ExpectLeft() {
        PlanVo left = new PlanVo(FOO, FOO);
        PlanVo right = new PlanVo(BAR, BAR);
        
        source.add(left);
        target.add(right);
        
        classUnderTest.update(source, target);

        assertThat(target.get(0).getKey(), equalTo(FOO));
    }
    
     /**
     * Test of update method, of class ProjectsUpdater.
     */
    @Test
    public void testUpdate_DifferentContent_OnlyOne() {
        PlanVo left = new PlanVo(FOO, FOO);
        PlanVo right = new PlanVo(BAR, BAR);
        
        source.add(left);
        target.add(right);
        target.add(left);
        
        classUnderTest.update(source, target);

        assertThat(target.size(), is(1));
    }
    
     /**
     * Test of update method, of class ProjectsUpdater.
     */
    @Test
    public void testUpdate_AddContent_NewOne() {
        PlanVo left = new PlanVo(FOO, FOO);
        PlanVo right = new PlanVo(BAR, BAR);
        
        source.add(left);
        source.add(right);
        target.add(right);
        
        classUnderTest.update(source, target);

        assertThat(target.size(), is(2));
    }
    
     /**
     * Test of update method, of class ProjectsUpdater.
     */
    @Test
    public void testUpdate_NewResult() {
        PlanVo left = new PlanVo(FOO);
        ResultVo leftResult = new ResultVo();
        leftResult.setNumber(1);
        left.setResult(leftResult);
        
        PlanVo right = new PlanVo(FOO);
        ResultVo rightResult = new ResultVo();
        right.setResult(rightResult);
        
        source.add(left);
        target.add(right);
        
        classUnderTest.update(source, target);

        assertThat(target.get(0).getResult().getNumber(), is(1));
    }
    
}
