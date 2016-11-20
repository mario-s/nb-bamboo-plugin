package org.netbeans.modules.bamboo.ui.nodes;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import org.netbeans.modules.bamboo.model.PlanVo;
import org.netbeans.modules.bamboo.model.ProjectVo;
import org.openide.nodes.Node;

/**
 *
 * @author spindizzy
 */
public class PlanNodeFactoryTest {

    private static final String FOO = "foo";
    private static final String BAR = "bar";
    private static final String BAZ = "baz";
    
    private ProjectVo project;

    private PlanNodeFactory classUnderTest;

    @Before
    public void setUp() {
        project = new ProjectVo("");
        classUnderTest = new PlanNodeFactory(project);
    }

    /**
     * Test of refreshNodes method, of class PlanNodeFactory.
     */
    @Test
    public void testRefreshNodes() {
        classUnderTest.refreshNodes();
    }

    /**
     * Test of createNodeForKey method, of class PlanNodeFactory.
     */
    @Test
    public void testCreateNodeForKey() {
        PlanVo key = new PlanVo(FOO, FOO);
        Node result = classUnderTest.createNodeForKey(key);
        assertThat(result.getName(), equalTo(FOO));
    }

    /**
     * Test of createKeys method, of class PlanNodeFactory.
     */
    @Test
    public void testCreateKeys_NoneIgnore_ExpectSorted() {
        List<PlanVo> toPopulate = new ArrayList<>();
        toPopulate.add(new PlanVo(FOO, FOO));
        toPopulate.add(new PlanVo(BAR, BAR));
        classUnderTest.createKeys(toPopulate);
        assertThat(toPopulate.get(0).getName(), equalTo(BAR));
    }
    
     /**
     * Test of createKeys method, of class PlanNodeFactory.
     */
    @Test
    public void testCreateKeys_OneIgnore_ExpectSorted() {
        List<PlanVo> toPopulate = new ArrayList<>();
        PlanVo fooPlan = new PlanVo(FOO, FOO);
        fooPlan.setIgnore(true);
        toPopulate.add(fooPlan);
        toPopulate.add(new PlanVo(BAZ, BAZ));
        toPopulate.add(new PlanVo(BAR, BAR));
        classUnderTest.createKeys(toPopulate);
        assertThat(toPopulate.get(2).getName(), equalTo(FOO));
    }

}
