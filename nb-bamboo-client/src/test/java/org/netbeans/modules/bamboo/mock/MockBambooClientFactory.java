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
package org.netbeans.modules.bamboo.mock;

import java.util.Optional;
import org.openide.util.lookup.ServiceProvider;

import org.netbeans.modules.bamboo.client.glue.BambooClientProduceable;
import org.netbeans.modules.bamboo.model.rcp.InstanceValues;

import static java.util.Optional.empty;

import org.netbeans.modules.bamboo.client.glue.BambooClient;

/**
 * @author Mario Schroeder
 */
@ServiceProvider(service = BambooClientProduceable.class, position = 10)
public class MockBambooClientFactory implements BambooClientProduceable {
    private BambooClientProduceable delegate;

    @Override
    public Optional<BambooClient> newClient(InstanceValues values) {
       if(delegate != null){
           return delegate.newClient(values);
       }
       return empty();
    }

    public void setDelegate(BambooClientProduceable delegate) {
        this.delegate = delegate;
    }
}
