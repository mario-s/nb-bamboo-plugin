package org.netbeans.modules.bamboo.rest;

import java.util.Map;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import org.netbeans.modules.bamboo.model.InstanceValues;

import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;

import java.util.logging.Level;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import lombok.extern.java.Log;

import static org.apache.commons.lang3.ArrayUtils.isNotEmpty;

/**
 * This class performs a a call to the REST API of Bamboo.
 * @author spindizzy
 */
@Log
class ApiCaller<T> implements ApiCallable{

    private final Class<T> clazz;
    private final String path;
    private Map<String, String> parameters;
    private final InstanceValues values;
    
    private final WebTargetFactory webTargetFactory;
    
    private String media = MediaType.APPLICATION_XML;

    ApiCaller(final CallParameters<T> params) {
        this.clazz = params.getResponseClass();
        this.values = params.getValues();
        this.path = params.getPath();
        
        webTargetFactory = new WebTargetFactory(this.values);
        
        setMediaType(params.isJson());
    }
    
    private void setMediaType(boolean isJson) {
        if(isJson){
            media = MediaType.APPLICATION_JSON;
        }
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

    @Override
    public T doGet(final WebTarget target) {
        return target.request().accept(media).get(clazz);
    }
}
