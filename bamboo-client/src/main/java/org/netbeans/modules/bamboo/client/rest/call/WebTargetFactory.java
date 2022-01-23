/* 
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

import org.glassfish.jersey.logging.LoggingFeature;
import org.netbeans.modules.bamboo.model.rcp.InstanceValues;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Factory for a new {@link WebTarget}.
 *
 * @author Mario Schroeder
 */
class WebTargetFactory {

    static final String REST_API = "/rest/api/latest";
    static final String AUTH_TYPE = "os_authType";
    static final String BASIC = "basic";
    static final String USER = "os_username";
    static final String PASS = "os_password";


    private final InstanceValues values;
    
    private Client client;

    WebTargetFactory(InstanceValues values) {
        this(values, Level.FINE);
    }

    WebTargetFactory(InstanceValues values, Level level) {
        this.values = values;
        
        var log = Logger.getLogger(this.getClass().getName());
        var feature = new LoggingFeature(log, level, null, null);
        this.client = ClientBuilder.newBuilder().register(feature).build();
    }
    
    void registerFeature(Object feature) {
        client = client.register(feature);
    }

    WebTarget create(final String path, final Map<String,String> params) {
        String url = values.getUrl();
        String user = values.getUsername();
        String password = String.valueOf(values.getPassword());
        
        WebTarget target = create(url, path)
                .queryParam(AUTH_TYPE, BASIC)
                .queryParam(USER, user)
                .queryParam(PASS, password);
        
        return addParameters(params, target);
    }
    
    WebTarget create(final String url, final String path) {
        return create(url).path(REST_API).path(path);
    }

    private WebTarget create(final String url) {
        return client.target(url);
    }
    
    WebTarget addParameters(final Map<String, String> params, WebTarget target) {
        if(params != null){
            for (Entry<String, String> entry : params.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                target = target.queryParam(key, value);
            }
        }
        return target;
    }
}
