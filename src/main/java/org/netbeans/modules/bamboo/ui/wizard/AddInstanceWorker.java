package org.netbeans.modules.bamboo.ui.wizard;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import org.netbeans.modules.bamboo.glue.BambooInstance;
import org.netbeans.modules.bamboo.glue.DefaultInstanceValues;
import org.netbeans.modules.bamboo.glue.InstanceManageable;
import org.netbeans.modules.bamboo.rest.BambooInstanceProduceable;

import static org.openide.util.Lookup.getDefault;
import org.openide.util.RequestProcessor;

import java.util.logging.Logger;
import org.openide.util.Task;
import org.openide.util.TaskListener;

/**
 * This worker does the call to the server and creation of nodes in a background
 * task.
 *
 * <p>
 * TODO replace SwingWorker with RequestProcessor</p>
 *
 * @author spindizzy
 */
class AddInstanceWorker implements PropertyChangeListener, TaskListener {

    private static final String EVENT_INSTANCE = "instance";

    private static final RequestProcessor RP = new RequestProcessor("interrupt",
            1, true);

    private final Logger log;
    private final AbstractDialogAction action;
    private final InstanceManageable manager;
    private final BambooInstanceProduceable bambooInstanceProducer;

    private RequestProcessor.Task last;
    private Worker worker;

    private boolean cancel;

    public AddInstanceWorker(final AbstractDialogAction action) {
        this.log = Logger.getLogger(getClass().getName());
        this.action = action;
        this.manager = action.getInstanceManager();

        bambooInstanceProducer = getDefault().lookup(
                BambooInstanceProduceable.class);
    }

    void cancel() {
        cancel = true;
        if (last != null) {
            last.cancel();
        }

//        if (values != null) {
//            manager.removeInstance(values.getName());
//        }
    }

    void execute(final InstancePropertiesForm form) {
        cancel = false;
        DefaultInstanceValues values = createInstanceValues(form);
        worker = new Worker(values);
        worker.addPropertyChangeListener(this);
        last = RP.post(worker);
        last.addTaskListener(this);
    }

    private DefaultInstanceValues createInstanceValues(
            final InstancePropertiesForm form) {
        DefaultInstanceValues vals = new DefaultInstanceValues();
        vals.setName(form.getInstanceName());
        vals.setUrl(form.getInstanceUrl());
        vals.setSyncInterval(form.getSyncTime());
        vals.setUsername(form.getUsername());
        vals.setPassword(form.getPassword());

        return vals;
    }

    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        String prop = pce.getPropertyName();
        if (EVENT_INSTANCE.equals(prop) && !cancel) {
            BambooInstance instance = (BambooInstance) pce.getNewValue();
            manager.addInstance(instance);
            action.onDone();
        }
    }

    @Override
    public void taskFinished(Task task) {
        if (task.isFinished()) {
            if (worker != null) {
                worker.removePropertyChangeListener(this);
            }
            task.removeTaskListener(this);
        }
    }

    private class Worker extends PropertyChangeSupport implements Runnable {

        private final DefaultInstanceValues values;

        public Worker(final DefaultInstanceValues values) {
            super(values);
            this.values = values;
        }

        @Override
        public void run() {
            BambooInstance instance = bambooInstanceProducer.create(values);

            if ((instance != null) && !Thread.interrupted()) {
                firePropertyChange(EVENT_INSTANCE, null, instance);
            }
        }
    }
}
