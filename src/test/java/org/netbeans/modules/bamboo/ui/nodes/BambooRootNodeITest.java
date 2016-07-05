/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netbeans.modules.bamboo.ui.nodes;

import static org.hamcrest.CoreMatchers.is;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import org.netbeans.modules.bamboo.glue.BambooManager;
import org.netbeans.modules.bamboo.glue.InstanceManageable;
import org.netbeans.modules.bamboo.model.DefaultBambooInstance;

import org.openide.nodes.Node;

import static org.openide.util.Lookup.getDefault;

import javax.swing.Action;


/**
 * @author spindizzy
 */
public class BambooRootNodeITest {
    private BambooRootNode classUnderTest;

    private InstanceManageable manager;

    @Before
    public void setUp() {
        classUnderTest = new BambooRootNode();
        manager = getDefault().lookup(InstanceManageable.class);
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
        manager.getContent().add(new DefaultBambooInstance());

        Node[] result = classUnderTest.getChildren().getNodes();
        assertThat(result.length, is(1));
    }
}
