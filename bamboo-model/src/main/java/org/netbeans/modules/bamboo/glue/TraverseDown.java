package org.netbeans.modules.bamboo.glue;

import java.util.Collection;

/**
 * A way to get the children of the instance.
 * @author spindizzy
 */
public interface TraverseDown<C> {
    
    Collection<C> getChildren();
    
    void setChildren(Collection<C> children);
}
