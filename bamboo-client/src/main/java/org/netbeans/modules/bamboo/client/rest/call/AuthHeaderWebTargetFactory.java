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
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Feature;
import org.glassfish.jersey.client.oauth2.OAuth2ClientSupport;
import org.netbeans.modules.bamboo.model.rcp.InstanceValues;
import org.slf4j.LoggerFactory;

import static org.apache.commons.lang3.ArrayUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
/**
 * This factory creates WebTraget which uses athorization in the header.
 * @author Mario Schroeder
 */
class AuthHeaderWebTargetFactory extends AbstractWebTargetFactory {
    
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(AuthHeaderWebTargetFactory.class);

    AuthHeaderWebTargetFactory(InstanceValues values) {
        this(values, Level.FINE);
    }

    AuthHeaderWebTargetFactory(InstanceValues values, Level level) {
        super(values, level);
        registerOuthFeature();
    }

    final void registerOuthFeature() {
        char[] token = values.getToken();
        Feature feature = OAuth2ClientSupport.feature(new String(token));
        registerFeature(feature);
    }

    @Override
    WebTarget create(String path, Map<String, String> params) {
        WebTarget target = addParameters(create(path), params);
        LOG.info("created target for {}", target.getUri());
        return target;
    }

    @Override
    boolean isValid() {
        String url = values.getUrl();
        char[] chars = values.getToken();

        return isNotBlank(url) && isNotEmpty(chars);
    }
    
}
