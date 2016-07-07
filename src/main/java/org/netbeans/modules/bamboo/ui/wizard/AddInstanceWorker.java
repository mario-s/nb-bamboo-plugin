package org.netbeans.modules.bamboo.ui.wizard;

import java.util.Optional;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import java.util.concurrent.ExecutionException;
import javax.swing.SwingWorker;
import org.netbeans.modules.bamboo.glue.BambooInstance;
import org.netbeans.modules.bamboo.glue.DefaultInstanceValues;
import org.netbeans.modules.bamboo.glue.InstanceManageable;
import org.netbeans.modules.bamboo.rest.BambooInstanceProduceable;
import org.openide.util.Exceptions;
import static org.openide.util.Lookup.getDefault;

/**
 *
 * @author spindizzy
 */
class AddInstanceWorker extends SwingWorker<BambooInstance, BambooInstance> {

    private final AbstractDialogAction action;
    private final InstanceManageable manager;
    private BambooInstanceProduceable bambooInstanceProducer;
    private DefaultInstanceValues values;
    private InstancePropertiesForm form;

    public AddInstanceWorker(AbstractDialogAction action) {
        this.action = action;
        this.manager = action.getInstanceManager();

        bambooInstanceProducer = getDefault().lookup(BambooInstanceProduceable.class);
        
    }
    
    /**
     * Executes the worker with the values from the form
     * @param form 
     */
    void execute(InstancePropertiesForm form) {
        this.form = form;
        execute();
    }

    private DefaultInstanceValues createInstanceValues(InstancePropertiesForm form) {
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
        if (!isCancelled()) {
            try {
                BambooInstance instance = get();
                if (instance != null) {
                    manager.addInstance(instance);
                }
            } catch (InterruptedException | ExecutionException ex) {
                Exceptions.printStackTrace(ex);
            }
        } else if(values != null) {
            manager.removeInstance(values.getName());
        }
        action.onDone();
    }

}
