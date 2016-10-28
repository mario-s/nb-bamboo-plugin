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
    private Map<String, String> parameters;
    private final InstanceValues values;
    
    private final WebTargetFactory webTargetFactory;

    ApiCaller(final CallParameters<T> params) {
        this.clazz = params.getResponseClass();
        this.values = params.getValues();
        this.path = params.getPath();
        
        webTargetFactory = new WebTargetFactory(this.values);
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
        return target.request().accept(MediaType.APPLICATION_XML).get(clazz);
    }

    /**
     * Simple post without any values
     * @param target the target to be called
     * @return the response code
     */
    Response post(WebTarget target) {
        Form form = new Form();
        Entity<Form> entity = entity(form, MediaType.WILDCARD_TYPE);
        return target.request().post(entity);
    }

}
