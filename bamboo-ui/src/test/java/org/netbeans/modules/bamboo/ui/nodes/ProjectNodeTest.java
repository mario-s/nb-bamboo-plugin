package org.netbeans.modules.bamboo.ui.nodes;

import java.awt.Image;

import static java.util.Collections.singletonList;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import org.netbeans.modules.bamboo.model.rcp.PlanVo;
import org.netbeans.modules.bamboo.model.rcp.ProjectVo;
import org.openide.nodes.Sheet;

/**
 *
 * @author spindizzy
 */
public class ProjectNodeTest {
    
    private ProjectNode classUnderTest;
    
    private ProjectVo project;
    
    @Before
    public void setUp() {
        project = new ProjectVo("");
        project.setChildren(singletonList(new PlanVo("")));
        classUnderTest = new ProjectNode(project);
    }

    /**
     * Test of getIcon method, of class ProjectNode.
     */
    @Test
    public void testGetIcon_ExpectNotNull() {
        Image result = classUnderTest.getIcon(1);
        assertThat(result, notNullValue());

    }
    
     /**
     * Test of getActions method, of class ProjectNode.
     */
    @Test
    public void testCreateSheet_ExpectOneSheet() {
        Sheet result = classUnderTest.createSheet();
        assertThat(result.toArray().length, is(1));
    }
}
