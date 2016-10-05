package org.netbeans.modules.bamboo.ui.nodes;

import org.openide.nodes.PropertySupport;

/**
 *
 * @author spindizzy
 */
abstract class StringReadPropertySupport extends PropertySupport.ReadOnly<String> {
    
    public StringReadPropertySupport(String name, String displayName, String shortDescription) {
        super(name, String.class, displayName, shortDescription);
    }
    
}
