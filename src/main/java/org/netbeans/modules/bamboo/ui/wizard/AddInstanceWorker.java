package org.netbeans.modules.bamboo.ui.wizard;

import org.netbeans.modules.bamboo.glue.BambooInstance;
import org.netbeans.modules.bamboo.glue.DefaultInstanceValues;
import org.netbeans.modules.bamboo.glue.InstanceManageable;
import org.netbeans.modules.bamboo.rest.BambooInstanceProduceable;

import static org.openide.util.Lookup.getDefault;
import org.openide.util.RequestProcessor;

import java.util.logging.Logger;


/**
 * This worker does the call to the server and creation of nodes in a background
 * task.
 *
 * <p>TODO replace SwingWorker with RequestProcessor</p>
 *
 * @author spindizzy
 */
class AddInstanceWorker {
    private static final RequestProcessor RP = new RequestProcessor("interrupt", 1, true);

    private final Logger log;
    private final AbstractDialogAction action;
    private final InstanceManageable manager;
    private final BambooInstanceProduceable bambooInstanceProducer;
    private RequestProcessor.Task last;

    private DefaultInstanceValues values;

    public AddInstanceWorker(final AbstractDialogAction action) {
        this.log = Logger.getLogger(getClass().getName());
        this.action = action;
        this.manager = action.getInstanceManager();

        bambooInstanceProducer = getDefault().lookup(BambooInstanceProduceable.class);
    }

    /**
     * Executes the worker with the values from the form.
     *
     * @param form
     */
    void execute(final InstancePropertiesForm form) {
        values = createInstanceValues(form);
        last = RP.post(new Worker(values));
    }

    void cancel() {
        if (last != null) {
            last.cancel();
        }

        if (values != null) {
            manager.removeInstance(values.getName());
        }
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

    private class Worker implements Runnable {
        private final DefaultInstanceValues values;

        public Worker(final DefaultInstanceValues values) {
            this.values = values;
        }

        @Override
        public void run() {
            if ((values != null)) {
                BambooInstance instance = bambooInstanceProducer.create(values);

                if ((instance != null) && !Thread.interrupted()) {
                    manager.addInstance(instance);
                }

                if (!Thread.interrupted()) {
                    action.onDone();
                }
            }
        }
    }
}
