package org.netbeans.modules.bamboo.model;

import java.util.Collection;
import java.util.Optional;
import lombok.NonNull;

/**
 * A way to get the children of the instance.
 * @author spindizzy
 */
public interface TraverseDown<C> {
    
    Collection<C> getChildren();
    
    void setChildren(@NonNull Collection<C> children);
    
    /**
     * This method verifies if the given child is part of the children.
     * @param child child that might be a in the collection of children.
     * @return <code>true</code> when the parameter is a child, otherwhise <code>false</code>
     */
    default boolean isChild(C child) {
        Collection<C> children = getChildren();
        return (children != null && !children.isEmpty()) ? children.contains(child) : false;
    }
    
    /**
     * This method verifies if the given child is part of the children.
     * @param child optional child that might be a in the collection of children.
     * @return <code>true</code> when the parameter is present and is a child, otherwhise <code>false</code>
     */
    default boolean isChild(Optional<C> child) {
        return (child.isPresent()) ? isChild(child.get()) : false;
    }
    
    
  
}
