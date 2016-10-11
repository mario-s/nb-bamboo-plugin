package org.netbeans.modules.bamboo.glue;

import java.util.Optional;

/**
 * A way to navigate to the parent of the instance.
 * @author spindizzy
 * @param <P> the parent
 */
public interface TraverseUp<P> {
    
    Optional<P> getParent();
    
    void setParent(P parent);
}
