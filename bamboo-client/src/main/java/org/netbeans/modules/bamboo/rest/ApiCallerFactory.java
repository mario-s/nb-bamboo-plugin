package org.netbeans.modules.bamboo.rest;

import java.util.Map;
import lombok.AllArgsConstructor;
import org.netbeans.modules.bamboo.model.InstanceValues;

/**
 * This factory produces {@link ApiCaller} and subclasses.
 *
 * @author spindizzy
 */
@AllArgsConstructor
class ApiCallerFactory {
    static final String JSON_PATH = ".json";

    private final InstanceValues values;

    ApiCaller newCaller(Class clazz, final String path) {
        return new ApiCaller<>(create(clazz, path));
    }

    ApiCaller newCaller(Class clazz, final String path, final Map<String, String> params) {
        return new ApiCaller<>(create(clazz, path, params));
    }

    ApiCallRepeater newRepeatCaller(Class clazz, final String path) {
        return new ApiCallRepeater<>(create(clazz, path));
    }

    ApiCallRepeater newRepeatCaller(Class clazz, final String path, final Map<String, String> params) {
        return new ApiCallRepeater<>(create(clazz, path, params));
    }

    private CallParameters create(Class clazz, String path, Map<String, String> params) {
        CallParameters callParams = create(clazz, path);
        callParams.setParameters(params);
        return callParams;
    }

    private CallParameters create(Class clazz, final String path) {
        CallParameters params = new CallParameters(clazz, values);
        params.setPath(path);
        params.setJson(path.endsWith(JSON_PATH));
        return params;
    }

}
