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
package org.netbeans.modules.bamboo.ui.notification;

import java.util.HashMap;
import java.util.Map;
import org.netbeans.modules.bamboo.model.rcp.BambooInstance;
import org.openide.util.lookup.ServiceProvider;
import org.netbeans.modules.bamboo.client.glue.BuildStatusWatchable;

/**
 * This class observes the plans of all the instances.
 * 
 * @author Mario Schroeder
 */
@ServiceProvider(service = BuildStatusWatchable.class, position = 1)
public class DefaultBuildStatusWatcher implements BuildStatusWatchable {
    
    private final Map<BambooInstance, PlanResultNotify> notifiers;

    public DefaultBuildStatusWatcher() {
        this.notifiers = new HashMap<>();
    }

    @Override
    public void addInstance(BambooInstance instance) {
        notifiers.put(instance, new PlanResultNotify(instance));
    }

    @Override
    public void removeInstance(BambooInstance instance) {
        notifiers.remove(instance);
    }

    Map<BambooInstance, PlanResultNotify> getNotifiers() {
        return notifiers;
    }
    
}
