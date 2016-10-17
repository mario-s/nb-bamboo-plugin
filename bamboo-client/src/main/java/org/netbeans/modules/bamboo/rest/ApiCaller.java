package org.netbeans.modules.bamboo.rest;

import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import org.netbeans.modules.bamboo.model.InstanceValues;

import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;

import java.util.logging.Level;
import javax.ws.rs.client.Entity;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import lombok.extern.java.Log;

import static javax.ws.rs.client.Entity.entity;
import static org.apache.commons.lang3.ArrayUtils.isNotEmpty;

/**
 * This class performs a a call to the REST API of Bamboo.
 * @author spindizzy
 */
@Log
class ApiCaller<T> {

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

    /**
     * This method creates a new target. It is empty if the required fields (url, user, password) are blank.
     * @return a possible new {@link WebTarget}
     */
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

    /**
     * Performs a GET request and returns the expected object of T.
     * @param target the target to be called
     * @return the result
     */
    T get(final WebTarget target) {
        return target.request().get(clazz);
    }

    /**
     * Simple post without any values
     * @param target the target to be called
     * @return the response code
     */
    int post(WebTarget target) {
        Form form = new Form();
        Entity<Form> entity = entity(form, MediaType.WILDCARD_TYPE);
        Response response = target.request().post(entity);
        return response.getStatus();
    }

}
