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

import org.netbeans.modules.bamboo.client.glue.BambooInstanceProduceable;
import org.netbeans.modules.bamboo.model.rcp.InstanceValues;
import org.netbeans.modules.bamboo.model.rcp.VersionInfo;

import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;

import java.util.Collection;
import java.util.Optional;
import org.netbeans.modules.bamboo.client.glue.BambooClientProduceable;
import org.netbeans.modules.bamboo.model.rcp.BambooInstance;
import org.netbeans.modules.bamboo.model.rcp.ProjectVo;

import static java.util.Optional.empty;
import static java.util.Optional.of;

import org.netbeans.modules.bamboo.client.glue.BambooClient;

/**
 * Factory for a new {@link BambooInstance}.
 * 
 * @author Mario Schroeder
 */
@ServiceProvider(service = BambooInstanceProduceable.class)
public class DefaultBambooInstanceFactory implements BambooInstanceProduceable {

    private final BambooClientProduceable clientFactory;

    public DefaultBambooInstanceFactory() {
        this.clientFactory = Lookup.getDefault().lookup(BambooClientProduceable.class);
    }

    @Override
    public Optional<BambooInstance> create(final InstanceValues values) {
        //create the client only when the url supplied by the values is valid
        Optional<BambooClient> optClient = clientFactory.newClient(values);
        if(optClient.isPresent()){
            AbstractBambooClient client = (AbstractBambooClient)optClient.get();
            DefaultBambooInstance instance = new DefaultBambooInstance(values, client);

            VersionInfo info = client.getVersionInfo();
            instance.setVersionInfo(info);

            Collection<ProjectVo> projects = client.getProjects();
            instance.setChildren(projects);
            
            return of(instance);
        }

        return empty();
    }
}
