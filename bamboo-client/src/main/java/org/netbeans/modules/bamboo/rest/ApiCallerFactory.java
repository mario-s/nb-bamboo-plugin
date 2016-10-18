package org.netbeans.modules.bamboo.rest;

import java.util.Map;
import lombok.AllArgsConstructor;
import org.netbeans.modules.bamboo.model.InstanceValues;

import static java.util.Collections.emptyMap;

/**
 * This factory produces {@link ApiCaller} and subclasses.
 * @author spindizzy
 */
@AllArgsConstructor
class ApiCallerFactory {

    private final InstanceValues values;

    ApiCaller newCaller(Class clazz, final String path) {
        return newCaller(clazz, path, emptyMap());
    }

    ApiCaller newCaller(Class clazz, final String path, final Map<String, String> params) {
        return new ApiCaller<>(values, clazz, path, params);
    }

    RepeatApiCaller newRepeatCaller(Class clazz, final String path) {
        return newRepeatCaller(clazz, path, emptyMap());
    }

    RepeatApiCaller newRepeatCaller(Class clazz, final String path, final Map<String, String> params) {
        return new RepeatApiCaller<>(values, clazz, path, params);
    }

}
