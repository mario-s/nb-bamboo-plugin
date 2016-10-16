package org.netbeans.modules.bamboo.rest;

import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import org.netbeans.modules.bamboo.model.InstanceValues;

import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;

import java.util.logging.Level;

import javax.ws.rs.client.WebTarget;
import lombok.extern.java.Log;

import static org.apache.commons.lang3.ArrayUtils.isNotEmpty;

/**
 * @author spindizzy
 */
@Log
class ApiCaller<T> {

    static final String START = "start-index";
    static final String MAX = "max-results";

    private final Class<T> clazz;
    private final String path;
    private final InstanceValues values;
    private Map<String, String> parameters;

    private WebTargetFactory webTargetFactory;

    ApiCaller(final InstanceValues values, final Class<T> clazz, final String path) {
        this(values, clazz, path, new HashMap<>());
    }

    ApiCaller(final InstanceValues values, final Class<T> clazz, final String path, final Map<String, String> params) {
        this.values = values;
        this.clazz = clazz;
        this.path = path;
        this.parameters = params;

        webTargetFactory = new WebTargetFactory(values);
    }

    Optional<WebTarget> createTarget() {
        Optional<WebTarget> opt = empty();
        String url = values.getUrl();
        String user = values.getUsername();
        char[] chars = values.getPassword();

        if (isNotBlank(url) && isNotBlank(user) && isNotEmpty(chars)) {
            opt = of(newTarget());
        } else if (log.isLoggable(Level.WARNING)) {
            log.warning("Invalid values for instance");
        }

        return opt;
    }

    protected WebTarget newTarget() {
        return webTargetFactory.newTarget(path, parameters);
    }

    T get(final WebTarget target) {
        return target.request().get(clazz);
    }
}
