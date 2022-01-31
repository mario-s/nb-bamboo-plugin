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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.netbeans.modules.bamboo.model.rcp.BambooInstance;
import org.netbeans.modules.bamboo.LookupContext;
import org.netbeans.modules.bamboo.model.rcp.ModelChangedValues;
import org.netbeans.modules.bamboo.model.event.ServerConnectionEvent;

/**
 * Listener when the connection to an instance changes.
 * It fires an event, using the lookup.
 *
 * @author Mario Schroeder
 */
class InstanceConnectionListener implements PropertyChangeListener {

    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        String propName = pce.getPropertyName();
        if (ModelChangedValues.Available.toString().equals(propName)) {
            final BambooInstance source = (BambooInstance) pce.getSource();
            String name = source.getName();
            boolean available = source.isAvailable();
            ServerConnectionEvent event = new ServerConnectionEvent(name, available);
            //TODO per instance
            LookupContext.Instance.add(event);
        }
    }
}
