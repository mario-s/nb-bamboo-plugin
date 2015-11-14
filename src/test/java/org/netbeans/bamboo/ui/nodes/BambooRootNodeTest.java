/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netbeans.bamboo.ui.nodes;

import javax.swing.Action;
import static org.hamcrest.CoreMatchers.is;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.netbeans.bamboo.LookupProvider;
import org.netbeans.bamboo.model.BambooInstance;
import org.openide.nodes.Node;

/**
 *
 * @author schroeder
 */
public class BambooRootNodeTest {
    
    private BambooRootNode classUnderTest;
    
    @Before
    public void setUp() {
        classUnderTest = new BambooRootNode();
    }
    

    /**
     * Test of getActions method, of class BambooRootNode.
     */
    @Test
    public void testGetActions() {
        Action[] result = classUnderTest.getActions(false);
        assertThat(result.length == 0, is(false));
    }
    
    @Test
    public void testCreateChild() {
        LookupProvider.Instance.getContent().add(new BambooInstance());
        Node [] result = classUnderTest.getChildren().getNodes();
        assertThat(result.length, is(1));
    }
    
}
