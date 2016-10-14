package org.netbeans.modules.bamboo.rest;

import java.util.Map;
import org.netbeans.modules.bamboo.glue.InstanceValues;

import static org.netbeans.modules.bamboo.rest.ApiCaller.MAX;

import org.netbeans.modules.bamboo.model.rest.AbstractResponse;

import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;

import javax.ws.rs.client.WebTarget;
import lombok.extern.java.Log;


/**
 * @author spindizzy
 */
@Log
class RepeatApiCaller<T extends AbstractResponse> extends ApiCaller<T> {

    RepeatApiCaller(InstanceValues values, Class<T> clazz, String path) {
        super(values, clazz, path);
    }

    RepeatApiCaller(InstanceValues values, Class<T> clazz, String path, Map<String, String> params) {
        super(values, clazz, path, params);
    }
    
    Optional<T> doSecondCall(final AbstractResponse initial) {
        int max = initial.getMaxResult();
        int size = initial.getSize();

        Optional<T> opt = empty();

        if (size > max) {
            WebTarget target = newTarget(values, path).queryParam(MAX, size);
            T response = get(target);
            log.fine(String.format("got all items: %s", response));
            opt = of(response);
        }

        return opt;
    }
}
