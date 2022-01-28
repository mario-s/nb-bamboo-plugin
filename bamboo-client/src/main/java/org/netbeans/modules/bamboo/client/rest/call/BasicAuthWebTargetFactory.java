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

import org.netbeans.modules.bamboo.model.rcp.InstanceValues;

import javax.ws.rs.client.WebTarget;
import java.util.Map;
import java.util.logging.Level;
import org.slf4j.LoggerFactory;

import static org.apache.commons.lang3.ArrayUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * Factory for a new {@link WebTarget}.
 *
 * @author Mario Schroeder
 */
class BasicAuthWebTargetFactory extends AbstractWebTargetFactory {
    
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(AuthHeaderWebTargetFactory.class);

    static final String BASIC = "basic";
    static final String USER = "os_username";
    static final String PASS = "os_password";

    BasicAuthWebTargetFactory(InstanceValues values) {
        this(values, Level.FINE);
    }

    BasicAuthWebTargetFactory(InstanceValues values, Level level) {
        super(values, level);
    }
    
    @Override
    WebTarget create(final String path, final Map<String,String> params) {
        String user = values.getUsername();
        String password = String.valueOf(values.getPassword());
        
        WebTarget target = create(path)
                .queryParam(AUTH_TYPE, BASIC)
                .queryParam(USER, user)
                .queryParam(PASS, password);
        target = addParameters(target, params);
        LOG.info("created target for {}", target.getUri());
        return target;
    }

    @Override
    boolean isValid() {
        String url = values.getUrl();
        String user = values.getUsername();
        char[] chars = values.getPassword();

        return isNotBlank(url) && isNotBlank(user) && isNotEmpty(chars);
    }
    
}
