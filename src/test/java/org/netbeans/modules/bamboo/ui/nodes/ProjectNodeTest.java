package org.netbeans.modules.bamboo.ui.nodes;

import java.awt.Image;
import javax.swing.Action;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.netbeans.modules.bamboo.model.ProjectVo;

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
        classUnderTest = new ProjectNode(project);
    }

    /**
     * Test of getIcon method, of class ProjectNode.
     */
    @Test
    public void testGetIcon() {
        Image result = classUnderTest.getIcon(1);
        assertThat(result, notNullValue());

    }

    /**
     * Test of getActions method, of class ProjectNode.
     */
    @Test
    public void testGetActions() {
        Action[] result = classUnderTest.getActions(true);
        assertThat(result.length, is(3));
    }
    
}
