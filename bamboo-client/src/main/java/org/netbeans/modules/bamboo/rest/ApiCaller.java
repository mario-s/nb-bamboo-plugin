package org.netbeans.modules.bamboo.rest;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import org.glassfish.jersey.logging.LoggingFeature;

import org.netbeans.modules.bamboo.model.InstanceValues;

import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;

import java.util.logging.Level;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import lombok.extern.java.Log;

import static org.apache.commons.lang3.ArrayUtils.isNotEmpty;

/**
 * @author spindizzy
 */
@Log
class ApiCaller<T> {
    
    static final String REST_API = "/rest/api/latest";

    static final String AUTH_TYPE = "os_authType";
    static final String BASIC = "basic";
    static final String USER = "os_username";
    static final String PASS = "os_password";
    static final String START = "start-index";
    static final String MAX = "max-results";

    private final Class<T> clazz;
    protected final String path;
    protected final InstanceValues values;
    private Map<String, String> params;

    private Client client;
    
    ApiCaller(final InstanceValues values, final Class<T> clazz, final String path) {
        this(values, clazz, path, new HashMap<>());
    }

    ApiCaller(final InstanceValues values, final Class<T> clazz, final String path, final Map<String, String> params) {
        this.client = ClientBuilder.newClient();
        this.values = values;
        this.clazz = clazz;
        this.path = path;
        this.params = params;
        
        client = client.register(new LoggingFeature(log, Level.FINE, null, null));
    }

    Optional<WebTarget> createTarget() {
        Optional<WebTarget> opt = empty();
        String url = values.getUrl();
        String user = values.getUsername();
        char[] chars = values.getPassword();

        if (isNotBlank(url) && isNotBlank(user) && isNotEmpty(chars)) {
            opt = of(newTarget(values, path));
        } else if (log.isLoggable(Level.WARNING)) {
            log.warning("Invalid values for instance");
        }

        return opt;
    }

    protected WebTarget newTarget(final InstanceValues values, final String path) {
        String url = values.getUrl();
        String user = values.getUsername();
        char[] chars = values.getPassword();
        String password = String.valueOf(chars);
        WebTarget target = newTarget(url, path).queryParam(AUTH_TYPE, BASIC).queryParam(USER, user).queryParam(
                PASS,
                password);
        
        if(params != null){
            for (Entry<String, String> entry : params.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                target = target.queryParam(key, value);
            }
        }

        return target;
    }

    protected WebTarget newTarget(final String url, final String path) {
        return newTarget(url).path(REST_API).path(path);
    }

    private WebTarget newTarget(final String url) {
        return client.target(url);
    }

    T get(final WebTarget target) {
        return target.request().get(clazz);
    }
}
