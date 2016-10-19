package org.netbeans.modules.bamboo.rest;

import java.util.Map;
import org.netbeans.modules.bamboo.model.InstanceValues;

import org.netbeans.modules.bamboo.model.rest.AbstractResponse;

import java.util.Optional;
import javax.ws.rs.client.WebTarget;

import static java.util.Optional.empty;
import static java.util.Optional.of;

import lombok.extern.java.Log;

/**
 * This class can be used to perform a second call to the REST API. It is used in cases where we don't know the
 * maximum of expected result upfront.
 *
 * @author spindizzy
 */
@Log
class RepeatApiCaller<T extends AbstractResponse> extends ApiCaller<T> {

    static final String MAX = "max-results";

    private Optional<T> opt = empty();

    RepeatApiCaller(InstanceValues values, Class<T> clazz, String path) {
        super(values, clazz, path);
    }

    RepeatApiCaller(InstanceValues values, Class<T> clazz, String path, Map<String, String> params) {
        super(values, clazz, path, params);
    }

    /**
     * Repeat a call to the endpoint based on the given initial response, if there are more items available (size >
     * results of first call).
     *
     * @param initial the initial response.
     * @return an empty {@link Optional} if there are no more result, otherwhise the complete amount of results.
     */
    Optional<T> repeat(final AbstractResponse initial) {
        int max = initial.getMaxResult();
        int size = initial.getSize();

        opt = empty();
        if (size > max) {
            WebTarget target = newTarget().queryParam(MAX, size);
            T response = get(target);
            log.fine(String.format("got all items: %s", response));
            opt = of(response);
        }

        return opt;
    }
}
