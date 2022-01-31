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
import org.netbeans.modules.bamboo.client.glue.BuildStatusWatchable;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Mario Schroeder
 */
@ServiceProvider(service = BuildStatusWatchable.class, position = 10)
public class MockBuildStateWatcher implements BuildStatusWatchable {

    private BuildStatusWatchable delegate;

    @Override
    public void addInstance(BambooInstance projectsProvider) {
        if (delegate != null) {
            delegate.addInstance(projectsProvider);
        } else {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    @Override
    public void removeInstance(BambooInstance projectsProvider) {
        if (delegate != null) {
            delegate.removeInstance(projectsProvider);
        } else {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    public void setDelegate(BuildStatusWatchable delegate) {
        this.delegate = delegate;
    }
}
