package org.netbeans.modules.bamboo.rest;

import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import lombok.extern.java.Log;
import org.glassfish.jersey.logging.LoggingFeature;
import org.netbeans.modules.bamboo.model.InstanceValues;


/**
 *
 * @author spindizzy
 */
@Log
class WebTargetFactory {

    static final String REST_API = "/rest/api/latest";
    static final String AUTH_TYPE = "os_authType";
    static final String BASIC = "basic";
    static final String USER = "os_username";
    static final String PASS = "os_password";

    private final InstanceValues values;
    
    private Client client;

    public WebTargetFactory(InstanceValues values) {
        this.values = values;
        
        client = ClientBuilder.newClient();
        client = client.register(new LoggingFeature(log, Level.FINE, null, null));
    }
    
    WebTarget newTarget(final String path, final Map<String,String> params) {
        String url = values.getUrl();
        String user = values.getUsername();
        char[] chars = values.getPassword();
        String password = String.valueOf(chars);
        WebTarget target = newTarget(url, path).queryParam(AUTH_TYPE, BASIC).queryParam(USER, user).queryParam(
                PASS,
                password);
        
        return addParameters(params, target);
    }

    private WebTarget addParameters(final Map<String, String> params, WebTarget target) {
        if(params != null){
            for (Entry<String, String> entry : params.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                target = target.queryParam(key, value);
            }
        }
        return target;
    }
    
    private WebTarget newTarget(final String url, final String path) {
        return newTarget(url).path(REST_API).path(path);
    }

    private WebTarget newTarget(final String url) {
        return client.target(url);
    }
}
