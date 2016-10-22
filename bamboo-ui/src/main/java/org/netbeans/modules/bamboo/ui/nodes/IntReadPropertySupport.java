package org.netbeans.modules.bamboo.ui.nodes;

import org.openide.nodes.PropertySupport;

/**
 * Abstract parent for read only support of integers.
 * 
 * @author spindizzy
 */
abstract class IntReadPropertySupport extends PropertySupport.ReadOnly<Integer> {
    
    public IntReadPropertySupport(String name, String displayName, String shortDescription) {
        super(name, Integer.class, displayName, shortDescription);
    }
    
}
