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
import javax.ws.rs.core.Feature;
import org.glassfish.jersey.client.oauth2.OAuth2ClientSupport;
import org.netbeans.modules.bamboo.model.rcp.InstanceValues;

import static org.apache.commons.lang3.ArrayUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import org.glassfish.jersey.logging.LoggingFeature;

/**
 * This factory creates WebTraget which uses athorization in the header.
 * @author Mario Schroeder
 */
class WebTargetFactory {
        
    static final String AUTH_TYPE = "os_authType";
    static final String REST_API = "/rest/api/latest";
    
    private final InstanceValues values;
    private Client client;

    WebTargetFactory(InstanceValues values) {
        this(values, Level.FINE);
    }

    WebTargetFactory(InstanceValues values, Level level) {
        this.values = values;
       
        createClient(level);
    }

    private void createClient(Level level) {
        char[] token = values.getToken();
        Feature oAuthFeature = OAuth2ClientSupport.feature(new String(token));
        
        var log = Logger.getLogger(this.getClass().getName());
        this.client = ClientBuilder.newBuilder()
                .register(new LoggingFeature(log, level, null, null))
                .register(oAuthFeature)
                .build();
    }

    WebTarget create(String path, Map<String, String> params) {
        WebTarget target = create(path);
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                target = target.queryParam(key, value);
            }
        }
        return target;
    }
    
    private WebTarget create(final String path) {
        String url = values.getUrl();
        return create(url, path);
    }
    
    private WebTarget create(final String url, final String path) {
        return client.target(url).path(REST_API).path(path);
    }

    boolean isValid() {
        String url = values.getUrl();
        char[] chars = values.getToken();

        return isNotBlank(url) && isNotEmpty(chars);
    }
}
