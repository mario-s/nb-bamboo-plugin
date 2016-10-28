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

    private final InstanceValues values;

    ApiCaller newCaller(Class clazz, final String path) {
        return new ApiCaller<>(create(clazz, path));
    }

    ApiCaller newCaller(Class clazz, final String path, final Map<String, String> params) {
        return new ApiCaller<>(create(clazz, path, params));
    }

    RepeatApiCaller newRepeatCaller(Class clazz, final String path) {
        return new RepeatApiCaller<>(create(clazz, path));
    }

    RepeatApiCaller newRepeatCaller(Class clazz, final String path, final Map<String, String> params) {
        return new RepeatApiCaller<>(create(clazz, path, params));
    }

    private CallParameters create(Class clazz, String path, Map<String, String> params) {
        CallParameters callParams = create(clazz, path);
        callParams.setParameters(params);
        return callParams;
    }

    private CallParameters create(Class clazz, final String path) {
        CallParameters params = new CallParameters(clazz, values);
        params.setPath(path);
        return params;
    }

}
