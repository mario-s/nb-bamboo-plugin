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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Optional;
import org.netbeans.api.annotations.common.NonNull;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.progress.ProgressHandleFactory;
import org.netbeans.modules.bamboo.model.rcp.BambooInstance;
import org.netbeans.modules.bamboo.model.rcp.ModelChangedValues;

import static org.netbeans.modules.bamboo.ui.notification.Bundle.TXT_SYNC;

import org.openide.util.NbBundle;

import static java.util.Optional.empty;
import static java.util.Optional.of;

/**
 * This class listens for changes on the sychronization.
 *
 * @author Mario Schroeder
 */
class SynchronizationListener implements PropertyChangeListener {

    private final BambooInstance instance;

    private Optional<ProgressHandle> progressHandle;

    SynchronizationListener(@NonNull BambooInstance instance) {
        this.instance = instance;
        progressHandle = empty();
    }

    /**
     * This method registener the listener to the instance.
     */
    final void registerListener() {
        instance.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName();
        if (ModelChangedValues.Synchronizing.toString().equals(propertyName)) {
            boolean sync = (boolean) evt.getNewValue();
            if (sync) {
                startProgress();
            } else {
                finishProgress();
            }
        }
    }

    private void startProgress() {
        getProgressHandle().get().start();
    }

    private void finishProgress() {
        progressHandle.ifPresent(handle -> {
            handle.finish();
            progressHandle = empty();
        });
    }

    private Optional<ProgressHandle> getProgressHandle() {
        if (!progressHandle.isPresent()) {
            progressHandle = of(newProgressHandle());
        }
        return progressHandle;
    }

    @NbBundle.Messages({"TXT_SYNC=Synchronizing"})
    private ProgressHandle newProgressHandle() {
        return ProgressHandleFactory.createHandle(TXT_SYNC() + " " + getName());
    }

    private String getName() {
        return instance.getName();
    }
}
