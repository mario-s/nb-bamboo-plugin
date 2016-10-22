package org.netbeans.modules.bamboo.ui.nodes;

import org.openide.nodes.PropertySupport;

/**
 * Abstract parent for read ans write support of integers.
 * 
 * @author spindizzy
 */
abstract class IntReadWritePropertySupport extends PropertySupport.ReadWrite<Integer> {
    
    public IntReadWritePropertySupport(String name, String displayName, String shortDescription) {
        super(name, Integer.class, displayName, shortDescription);
    }
    
}
