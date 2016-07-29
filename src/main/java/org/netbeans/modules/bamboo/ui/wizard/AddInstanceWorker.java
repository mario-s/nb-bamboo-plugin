package org.netbeans.modules.bamboo.ui.wizard;

import org.netbeans.modules.bamboo.glue.BambooInstance;
import org.netbeans.modules.bamboo.glue.DefaultInstanceValues;
import org.netbeans.modules.bamboo.glue.InstanceManageable;
import org.netbeans.modules.bamboo.rest.BambooInstanceProduceable;

import org.openide.util.Exceptions;
import static org.openide.util.Lookup.getDefault;
import org.openide.util.RequestProcessor;
import org.openide.util.Task;
import org.openide.util.TaskListener;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import java.util.logging.Logger;


/**
 * This worker does the call to the server and creation of nodes in a background
 * task.
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

    private RequestProcessor.Task last;
    private Worker worker;

    private boolean cancel;

    BambooInstance instance;

    public AddInstanceWorker(final AbstractDialogAction action) {
        this.log = Logger.getLogger(getClass().getName());
        this.action = action;
        this.manager = action.getInstanceManager();

        bambooInstanceProducer = getDefault().lookup(BambooInstanceProduceable.class);
    }

    void cancel() {
        cancel = true;

        if (last != null) {
            last.cancel();
        }
    }

    void execute(final InstancePropertiesForm form) {
        cancel = false;
        instance = null;

        DefaultInstanceValues values = createInstanceValues(form);
        worker = new Worker(values);
        worker.addPropertyChangeListener(this);
        last = RP.post(worker);
        last.addTaskListener(this);
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
            instance = (BambooInstance) pce.getNewValue();
        }
    }

    @Override
    public void taskFinished(final Task task) {
        if (task.isFinished()) {
            if (worker != null) {
                worker.removePropertyChangeListener(this);
            }

            task.removeTaskListener(this);

            if (instance != null) {
                manager.addInstance(instance);
            }

            action.onDone();
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
                Exceptions.printStackTrace(ex);
            }

            if ((instance != null) && !Thread.interrupted()) {
                firePropertyChange(EVENT_INSTANCE, null, instance);
            }
        }
    }
}
