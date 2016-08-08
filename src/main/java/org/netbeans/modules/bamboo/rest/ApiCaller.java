package org.netbeans.modules.bamboo.rest;

import java.util.Optional;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Feature;

import org.glassfish.jersey.logging.LoggingFeature;
import org.netbeans.modules.bamboo.glue.InstanceValues;

import static org.netbeans.modules.bamboo.rest.BambooRestClient.REST_API;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import org.netbeans.modules.bamboo.rest.model.AbstractResponse;
import static org.apache.commons.lang3.ArrayUtils.isNotEmpty;

/**
 *
 * @author spindizzy
 */
class ApiCaller<T extends AbstractResponse> {

    static final String AUTH_TYPE = "os_authType";
    static final String BASIC = "basic";
    static final String USER = "os_username";
    static final String PASS = "os_password";
    static final String START = "start-index";
    static final String MAX = "max-results";

    private final Logger log;
    private final Feature logFeature;
    
    private final Class<T> clazz;
    private final String path;
    private final InstanceValues values;

    ApiCaller(final InstanceValues values, final Class<T> clazz, final String path) {
        this.values = values;
        this.clazz = clazz;
        this.path = path;
        this.log = Logger.getLogger(getClass().getName());
        this.logFeature = new LoggingFeature(log, Level.INFO, null, null);
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

    private WebTarget newTarget(final InstanceValues values, final String path) {
        String url = values.getUrl();
        String user = values.getUsername();
        char[] chars = values.getPassword();
        String password = String.valueOf(chars);

        return newTarget(url, path).queryParam(AUTH_TYPE, BASIC)
                .queryParam(USER, user).queryParam(PASS, password);
    }

    private WebTarget newTarget(String url, final String path) {
        return newTarget(url).path(REST_API).path(path);
    }

    WebTarget newTarget(final String url) {
        return ClientBuilder.newClient().register(logFeature).target(url);
    }

    T request(final WebTarget target) {
        return target.request().get(clazz);
    }
}
