package org.netbeans.modules.bamboo.model;

import java.util.Collection;

/**
 * A way to get the children of the instance.
 * @author spindizzy
 */
public interface TraverseDown<C> {
    
    Collection<C> getChildren();
    
    void setChildren(Collection<C> children);
    
    default boolean isChildOf(C child) {
        Collection<C> children = getChildren();
        return (children != null && !children.isEmpty()) ? children.contains(child) : false;
    }
  
}
