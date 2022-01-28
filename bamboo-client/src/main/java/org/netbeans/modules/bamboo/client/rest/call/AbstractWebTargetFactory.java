/*
 * Copyright 2022 NetBeans.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.netbeans.modules.bamboo.client.rest.call;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import org.glassfish.jersey.logging.LoggingFeature;
import org.netbeans.modules.bamboo.model.rcp.InstanceValues;

/**
 *
 * @author Mario Schroeder
 */
abstract class AbstractWebTargetFactory {
    
    static final String AUTH_TYPE = "os_authType";
    static final String REST_API = "/rest/api/latest";
    
    protected final InstanceValues values;
    protected Client client;

    AbstractWebTargetFactory(InstanceValues values, Level level) {
        this.values = values;
        
        var log = Logger.getLogger(this.getClass().getName());
        var feature = new LoggingFeature(log, level, null, null);
        this.client = ClientBuilder.newBuilder().register(feature).build();
    }

    protected WebTarget addParameters(final WebTarget target, Map<String, String> params) {
        WebTarget out = target;
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                out = out.queryParam(key, value);
            }
        }
        return out;
    }
    
    protected WebTarget create(final String path) {
        String url = values.getUrl();
        return create(url, path);
    }
    
    private WebTarget create(final String url, final String path) {
        return client.target(url).path(REST_API).path(path);
    }

    protected final void registerFeature(Object feature) {
        client = client.register(feature);
    }
    
    abstract WebTarget create(String path, Map<String,String> params);
    
    /**
     * Retruns <code>true</code> when url and authentication are not null and not empty.
     * @return boolean
     */
    abstract boolean isValid();
    
}
