package org.netbeans.modules.bamboo.model.rcp;

import java.util.Optional;

/**
 * A way to navigate to the parent of the instance.
 *
 * @author spindizzy
 * @param <P> the parent
 */
public interface TraverseUp<P extends OpenableInBrowser> {

    Optional<P> getParent();

    void setParent(P parent);
}
