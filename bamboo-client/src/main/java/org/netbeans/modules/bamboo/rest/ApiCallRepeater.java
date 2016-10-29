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
class ApiCallRepeater<T extends AbstractResponse> extends ApiCaller<T> implements ApiCallRepeatable{

    static final String MAX = "max-results";

    private Optional<T> opt = empty();

    ApiCallRepeater(CallParameters<T> params) {
        super(params);
    }

    @Override
    public Optional<T> repeat(final AbstractResponse initial) {
        int max = initial.getMaxResult();
        int size = initial.getSize();

        opt = empty();
        if (size > max) {
            WebTarget target = newTarget().queryParam(MAX, size);
            T response = doGet(target);
            log.fine(String.format("got all items: %s", response));
            opt = of(response);
        }

        return opt;
    }
}
