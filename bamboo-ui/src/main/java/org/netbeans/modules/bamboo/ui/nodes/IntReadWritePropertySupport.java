package org.netbeans.modules.bamboo.ui.nodes;

import org.openide.nodes.PropertySupport;

/**
 *
 * @author spindizzy
 */
abstract class IntReadWritePropertySupport extends PropertySupport.ReadWrite<Integer> {
    
    public IntReadWritePropertySupport(String name, String displayName, String shortDescription) {
        super(name, Integer.class, displayName, shortDescription);
    }
    
}
