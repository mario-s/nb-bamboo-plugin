package org.netbeans.modules.bamboo.rest;

import static org.apache.commons.lang3.ArrayUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import org.glassfish.jersey.logging.LoggingFeature;

import org.netbeans.modules.bamboo.glue.InstanceValues;
import static org.netbeans.modules.bamboo.rest.BambooRestClient.REST_API;
import org.netbeans.modules.bamboo.rest.model.AbstractResponse;

import java.util.Optional;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Feature;


/**
 * @author spindizzy
 */
class ApiCaller<T> {
    static final String AUTH_TYPE = "os_authType";
    static final String BASIC = "basic";
    static final String USER = "os_username";
    static final String PASS = "os_password";
    static final String START = "start-index";
    static final String MAX = "max-results";

    protected final Logger log;
    private final Feature logFeature;

    private final Class<T> clazz;
    protected final String path;
    protected final InstanceValues values;

    private Client client;

    ApiCaller(final InstanceValues values, final Class<T> clazz, final String path) {
        this.client = ClientBuilder.newClient();
        this.values = values;
        this.clazz = clazz;
        this.path = path;
        this.log = Logger.getLogger(getClass().getName());
        this.logFeature = new LoggingFeature(log, Level.INFO, null, null);

        client = client.register(logFeature);
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

        return newTarget(url, path).queryParam(AUTH_TYPE, BASIC).queryParam(USER, user).queryParam(
                PASS,
                password);
    }

    private WebTarget newTarget(final String url, final String path) {
        return newTarget(url).path(REST_API).path(path);
    }

    private WebTarget newTarget(final String url) {
        return client.target(url);
    }

    T request(final WebTarget target) {
        return target.request().get(clazz);
    }
}
