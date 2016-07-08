package org.netbeans.modules.bamboo.ui.wizard;

import java.awt.EventQueue;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;
import org.netbeans.modules.bamboo.glue.BambooInstance;
import org.netbeans.modules.bamboo.glue.DefaultInstanceValues;
import org.netbeans.modules.bamboo.glue.InstanceManageable;
import org.netbeans.modules.bamboo.rest.BambooInstanceProduceable;
import static org.openide.util.Lookup.getDefault;

/**
 * This worker does the call to the server and creation of nodes in a background 
 * task.
 * 
 * TODO replace SwingWorker with RequestProcessor
 * 
 * @author spindizzy
 */
class AddInstanceWorker extends SwingWorker<BambooInstance, BambooInstance> {

    private final Logger log;
    private final AbstractDialogAction action;
    private final InstanceManageable manager;
    private BambooInstanceProduceable bambooInstanceProducer;
    private DefaultInstanceValues values;
    private InstancePropertiesForm form;

    public AddInstanceWorker(AbstractDialogAction action) {
        this.log = Logger.getLogger(getClass().getName());
        this.action = action;
        this.manager = action.getInstanceManager();

        bambooInstanceProducer = getDefault().lookup(
                BambooInstanceProduceable.class);
    }

    /**
     * Executes the worker with the values from the form
     *
     * @param form
     */
    void execute(InstancePropertiesForm form) {
        this.form = form;
        execute();
    }

    private DefaultInstanceValues createInstanceValues(
            InstancePropertiesForm form) {
        DefaultInstanceValues vals = new DefaultInstanceValues();
        vals.setName(form.getInstanceName());
        vals.setUrl(form.getInstanceUrl());
        vals.setSyncInterval(form.getSyncTime());
        vals.setUsername(form.getUsername());
        vals.setPassword(form.getPassword());
        return vals;
    }

    @Override
    protected BambooInstance doInBackground() throws Exception {
        values = createInstanceValues(form);
        return createInstance();
    }

    BambooInstance createInstance() {
        BambooInstance instance = null;
        if (values != null && !isCancelled()) {
            instance = bambooInstanceProducer.create(values);
        }
        return instance;
    }

    @Override
    protected void done() {
        EventQueue.invokeLater(() -> {
            if (!isCancelled()) {
                try {
                    BambooInstance instance = get();
                    if (instance != null) {
                        manager.addInstance(instance);
                    }
                } catch (InterruptedException | ExecutionException ex) {
                    log.log(Level.WARNING, ex.getMessage(), ex);
                    //TODO send feed back to dialog
                }
            } else if (values != null) {
                manager.removeInstance(values.getName());
            }
        });
        action.onDone();
    }

}
