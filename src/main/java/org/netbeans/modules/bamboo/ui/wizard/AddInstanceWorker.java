package org.netbeans.modules.bamboo.ui.wizard;

import org.netbeans.modules.bamboo.glue.BambooInstance;
import org.netbeans.modules.bamboo.glue.DefaultInstanceValues;
import org.netbeans.modules.bamboo.glue.InstanceManageable;
import org.netbeans.modules.bamboo.rest.BambooInstanceProduceable;

import static org.openide.util.Lookup.getDefault;
import org.openide.util.RequestProcessor;
import org.openide.util.Task;
import org.openide.util.TaskListener;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import java.util.Optional;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.Optional.ofNullable;
import java.util.logging.Logger;


/**
 * This currentWorker does the call to the server and creation of nodes in a
 * background task.
 *
 * <p>TODO replace SwingWorker with RequestProcessor</p>
 *
 * @author spindizzy
 */
class AddInstanceWorker implements PropertyChangeListener, TaskListener {
    private static final String EVENT_INSTANCE = "instance";

    private static final RequestProcessor RP = new RequestProcessor("interrupt", 1, true);

    private final Logger log;
    private final AbstractDialogAction action;
    private final InstanceManageable manager;
    private final BambooInstanceProduceable bambooInstanceProducer;

    private Optional<RequestProcessor.Task> currentTask;
    private Optional<Worker> currentWorker;
    private Optional<String> instanceName;

    private boolean cancel;

    public AddInstanceWorker(final AbstractDialogAction action) {
        this.log = Logger.getLogger(getClass().getName());
        this.action = action;
        this.manager = action.getInstanceManager();

        bambooInstanceProducer = getDefault().lookup(BambooInstanceProduceable.class);
        reset();
    }

    void cancel() {
        cancel = true;

        if (currentTask.isPresent()) {
            currentTask.get().cancel();
        }
    }

    void execute(final InstancePropertiesForm form) {
        reset();

        DefaultInstanceValues values = createInstanceValues(form);

        instanceName = ofNullable(values.getName());

        Worker worker = new Worker(values);
        worker.addPropertyChangeListener(this);

        RequestProcessor.Task task = RP.post(worker);
        task.addTaskListener(this);

        currentTask = of(task);
        currentWorker = of(worker);
    }

    private void reset() {
        cancel = false;
        instanceName = empty();
        currentTask = empty();
        currentWorker = empty();
    }

    private DefaultInstanceValues createInstanceValues(final InstancePropertiesForm form) {
        DefaultInstanceValues vals = new DefaultInstanceValues();
        vals.setName(form.getInstanceName());
        vals.setUrl(form.getInstanceUrl());
        vals.setSyncInterval(form.getSyncTime());
        vals.setUsername(form.getUsername());
        vals.setPassword(form.getPassword());

        return vals;
    }

    @Override
    public void propertyChange(final PropertyChangeEvent pce) {
        String prop = pce.getPropertyName();

        if (EVENT_INSTANCE.equals(prop) && !cancel) {
            BambooInstance instance = (BambooInstance) pce.getNewValue();

            if (instance != null) {
                manager.addInstance(instance);
            }

            action.onDone();
        }
    }

    @Override
    public void taskFinished(final Task task) {
        if (task.isFinished()) {
            if (currentWorker.isPresent()) {
                currentWorker.get().removePropertyChangeListener(this);
            }

            task.removeTaskListener(this);

            if (cancel && instanceName.isPresent()) {
                manager.removeInstance(instanceName.get());
            }
        }
    }

    private class Worker extends PropertyChangeSupport implements Runnable {
        /** Use serialVersionUID for interoperability. */
        private static final long serialVersionUID = 1L;

        private final DefaultInstanceValues values;

        public Worker(final DefaultInstanceValues values) {
            super(values);
            this.values = values;
        }

        @Override
        public void run() {
            BambooInstance instance = bambooInstanceProducer.create(values);

            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                log.info(ex.getMessage());
            }

            if ((instance != null) && !Thread.interrupted()) {
                firePropertyChange(EVENT_INSTANCE, null, instance);
            }
        }
    }
}
