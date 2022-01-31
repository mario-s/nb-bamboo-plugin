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

import java.util.Map;

import org.netbeans.modules.bamboo.model.rcp.InstanceValues;
import java.util.Optional;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.Optional.empty;
import static java.util.Optional.of;

import javax.ws.rs.WebApplicationException;
import org.openide.util.Exceptions;

/**
 * This class performs a a call to the REST API of Bamboo.
 *
 * @author Mario Schroeder
 */
class ApiCaller<T> implements ApiCallable {

    private static final Logger LOG = LoggerFactory.getLogger(ApiCaller.class);

    private final Class<T> clazz;
    private final String path;
    private final Map<String, String> parameters;
    private final InstanceValues values;

    private final WebTargetFactory webTargetFactory;

    private String media = MediaType.APPLICATION_XML;

    ApiCaller(final CallParameters<T> params) {
        this.clazz = params.getResponseClass();
        this.values = params.getValues();
        this.path = params.getPath();
        this.parameters = params.getParameters();

        webTargetFactory = new WebTargetFactory(this.values);

        setMediaType(params.isJson());
    }

    private void setMediaType(boolean isJson) {
        if (isJson) {
            media = MediaType.APPLICATION_JSON;
        }
    }

    @Override
    public Optional<WebTarget> createTarget() {
        
        if (webTargetFactory.isValid()) {
            return of(newTarget());
        } 
            
        LOG.warn("Invalid values for instance");
        return empty();
    }

    protected WebTarget newTarget() {
        return webTargetFactory.create(path, parameters);
    }

    @Override
    public Optional<T> doGet(final WebTarget target) {
        LOG.info("GET: {}", target.getUri());

        try {
            return of(target.request().accept(media).get(clazz));
        } catch (WebApplicationException ex) {
            Exceptions.printStackTrace(ex);
        }

        return empty();
    }
}
