package org.netbeans.modules.bamboo.rest;

import java.util.Optional;
import org.netbeans.modules.bamboo.model.rest.AbstractResponse;

/**
 * This interface describes a way to repeat the previous call to the API but with differerent parameters.
 * @author spindizzy
 */
public interface ApiCallRepeatable<T extends AbstractResponse> extends ApiCallable<T>{

    /**
     * Repeat a call to the endpoint based on the given initial response, if there are more items available (size >
     * results of first call).
     *
     * @param initial the initial response.
     * @return an empty {@link Optional} if there are no more result, otherwhise the complete amount of results.
     */
    Optional<T> repeat(final AbstractResponse initial);
    
}
