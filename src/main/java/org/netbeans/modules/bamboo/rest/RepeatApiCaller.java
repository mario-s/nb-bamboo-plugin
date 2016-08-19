package org.netbeans.modules.bamboo.rest;

import org.netbeans.modules.bamboo.glue.InstanceValues;
import static org.netbeans.modules.bamboo.rest.ApiCaller.MAX;
import org.netbeans.modules.bamboo.rest.model.AbstractResponse;

import java.util.Optional;
import static java.util.Optional.empty;
import static java.util.Optional.of;

import javax.ws.rs.client.WebTarget;


/**
 * @author spindizzy
 */
class RepeatApiCaller<T extends AbstractResponse> extends ApiCaller<T> {
    RepeatApiCaller(final InstanceValues values, final Class<T> clazz, final String path) {
        super(values, clazz, path);
    }

    Optional<T> doSecondCall(final T initial) {
        int max = initial.getMaxResult();
        int size = initial.getSize();

        Optional<T> opt = empty();

        if (size > max) {
            WebTarget target = newTarget(values, path).queryParam(MAX, size);
            T response = request(target);
            log.fine(String.format("got all items: %s", response));
            opt = of(response);
        }

        return opt;
    }
}
