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

import org.netbeans.modules.bamboo.model.rcp.BambooInstance;
import org.netbeans.modules.bamboo.client.glue.InstanceManageable;

import org.openide.util.lookup.ServiceProvider;

import java.util.Collection;
import org.netbeans.modules.bamboo.client.rest.DefaultInstanceManager;


/**
 * @author Mario Schroeder
 */
@ServiceProvider(service = InstanceManageable.class, position = 10)
public class MockInstanceManager extends DefaultInstanceManager {
    private InstanceManageable delegate;

    @Override
    public void addInstance(final BambooInstance instance) {
        if (delegate != null) {
            delegate.addInstance(instance);
        } else {
            super.addInstance(instance);
        }
    }

    @Override
    public void removeInstance(final String name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removeInstance(final BambooInstance instance) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean existsInstanceName(final String name) {
        if(delegate != null) {
            return delegate.existsInstanceName(name);
        }
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Collection<BambooInstance> loadInstances() {
        return delegate.loadInstances();
    }

    public void setDelegate(final InstanceManageable delegate) {
        this.delegate = delegate;
    }
}
