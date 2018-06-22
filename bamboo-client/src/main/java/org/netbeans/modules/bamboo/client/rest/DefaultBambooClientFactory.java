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
package org.netbeans.modules.bamboo.client.rest;

import org.netbeans.modules.bamboo.client.glue.HttpUtility;
import java.util.Optional;
import org.netbeans.modules.bamboo.client.glue.BambooClientProduceable;
import org.netbeans.modules.bamboo.model.rcp.InstanceValues;
import org.openide.util.lookup.ServiceProvider;

import static java.util.Optional.empty;
import static java.util.Optional.of;

import org.netbeans.modules.bamboo.client.glue.BambooClient;


/**
 *
 * @author Mario Schroeder
 */
@ServiceProvider(service = BambooClientProduceable.class)
public class DefaultBambooClientFactory implements BambooClientProduceable {
    
    private final HttpUtility httpUtility;

    public DefaultBambooClientFactory() {
        this(new HttpUtility());
    }

    DefaultBambooClientFactory(HttpUtility httpUtility) {
        this.httpUtility = httpUtility;
    }

    @Override
    public Optional<BambooClient> newClient(InstanceValues values) {
        Optional<BambooClient> opt = empty();
        String url = values.getUrl();
        if (httpUtility.exists(url)) {
            opt = of(new DefaultBambooClient(values, httpUtility));
        }
        return opt;
    }
}
