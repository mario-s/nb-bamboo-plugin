package org.netbeans.modules.bamboo.client.rest.call;

import java.util.Optional;
import org.netbeans.modules.bamboo.model.rest.Responseable;

/**
 * This interface describes a way to repeat the previous call to the API but with differerent parameters.
 * @author Mario Schroeder
 */
public interface ApiCallRepeatable<T extends Responseable> extends ApiCallable<T>{
    
    /**
     * Max number of results.
     */
    String MAX = "max-results";

    /**
     * Repeat a call to the endpoint based on the given initial response, if there are more items available (size is 
     * greater than results of first call).
     *
     * @param initial the initial response.
     * @return an empty {@link Optional} if there are no more result, otherwhise the complete amount of results.
     */
    Optional<T> repeat(final Responseable initial);
    
}
