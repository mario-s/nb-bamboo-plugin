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
package org.netbeans.modules.bamboo.ui.wizard;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Optional;

import org.netbeans.modules.bamboo.client.glue.InstanceManageable;
import org.netbeans.modules.bamboo.model.rcp.BambooInstance;
import org.netbeans.modules.bamboo.model.rcp.DefaultInstanceValues;
import org.openide.NotifyDescriptor;
import org.openide.util.RequestProcessor;
import org.openide.util.Task;
import org.openide.util.TaskListener;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.Optional.ofNullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This worker does the call to the server and creation of nodes in a
 * background task.
 *
 * @author Mario Schroeder
 */
class AddInstanceWorker implements PropertyChangeListener, TaskListener {
    
    private static final Logger LOG = LoggerFactory.getLogger(AddInstanceWorker.class);
    private static final RequestProcessor RP = new RequestProcessor("interrupt", 1, true);

    private final AbstractDialogAction action;
    private final InstanceManageable manager;

    private Optional<RequestProcessor.Task> currentTask;
    private Optional<Runner> currentRunner;
    private Optional<String> instanceName;

    private boolean cancel;

    public AddInstanceWorker(final AbstractDialogAction action) {
        this.action = action;
        this.manager = action.getInstanceManager();

        reset();
    }

    void cancel() {
        cancel = true;

        currentTask.ifPresent(t -> t.cancel());
        
        LOG.info("The task to add an instance was canceled by user.");
    }

    void execute(final InstancePropertiesForm form) {
        reset();

        DefaultInstanceValues values = createInstanceValues(form);

        instanceName = ofNullable(values.getName());

        Runner runner = newRunner(values);
        runner.addPropertyChangeListener(this);

        RequestProcessor.Task task = RP.post(runner);
        task.addTaskListener(this);

        currentTask = of(task);
        currentRunner = of(runner);
    }

    Runner newRunner(DefaultInstanceValues values) {
        return new Runner(values);
    }

    private void reset() {
        cancel = false;
        instanceName = empty();
        currentTask = empty();
        currentRunner = empty();
    }

    private DefaultInstanceValues createInstanceValues(final InstancePropertiesForm form) {
        var values = new DefaultInstanceValues();
        values.setName(form.getInstanceName());
        values.setUrl(form.getInstanceUrl());
        values.setToken(form.getToken());
        values.setSyncInterval(form.getSyncTime());

        return values;
    }

    @Override
    public void propertyChange(final PropertyChangeEvent event) {
        String prop = event.getPropertyName();

        if (WorkerEvents.INSTANCE_CREATED.name().equals(prop) && !cancel) {
            LOG.info("Instance created successful.");
            BambooInstance instance = (BambooInstance) event.getNewValue();

            if (instance != null) {
                manager.persist(instance);
                manager.addInstance(instance);
            }

            action.firePropertyChange(WorkerEvents.INSTANCE_CREATED.name(),
                null,
                NotifyDescriptor.OK_OPTION);
        } else if (WorkerEvents.INVALID_URL.name().equals(prop)) {
            LOG.warn("invalid URL for instance");
            action.firePropertyChange(event);
        }
    }

    @Override
    public void taskFinished(final Task task) {
        if (task.isFinished()) {
            if (currentRunner.isPresent()) {
                currentRunner.get().removePropertyChangeListener(this);
            }

            task.removeTaskListener(this);

            if (cancel && instanceName.isPresent()) {
                manager.removeInstance(instanceName.get());
            }
        }
    }
}
