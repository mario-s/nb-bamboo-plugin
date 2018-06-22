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
package org.netbeans.modules.bamboo;

import org.netbeans.modules.bamboo.model.rcp.BambooInstance;
import org.netbeans.modules.bamboo.client.glue.InstanceManageable;

import static org.openide.util.Lookup.getDefault;

import org.openide.util.Task;
import org.openide.util.TaskListener;

import org.openide.windows.OnShowing;

import java.util.Collection;
import org.netbeans.modules.bamboo.model.event.InstancesLoadEvent;


@OnShowing
public final class Installer implements Runnable {
    @Override
    public void run() {
        InstanceManageable manager = getDefault().lookup(InstanceManageable.class);
        Collection<BambooInstance> instances = manager.loadInstances();

        if (!instances.isEmpty()) {
            manager.getContent().add(new InstancesLoadEvent(instances));
            
            instances.parallelStream().forEach(instance -> {
                    TaskListener listener = new SyncTaskListener(manager, instance);
                    Task task = instance.synchronize(false); //silent synchonize
                    task.addTaskListener(listener);
                });
        }
    }
}
