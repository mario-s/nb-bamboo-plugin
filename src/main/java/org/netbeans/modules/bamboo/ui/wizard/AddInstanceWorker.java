package org.netbeans.modules.bamboo.ui.wizard;

import javax.swing.SwingWorker;
import org.netbeans.modules.bamboo.glue.DefaultInstanceValues;
import org.netbeans.modules.bamboo.glue.InstanceManageable;

/**
 *
 * @author spindizzy
 */
class AddInstanceWorker extends SwingWorker<Object, Object> {
    
    private final AbstractDialogAction action;
    private final InstanceManageable manager;
    private final InstancePropertiesForm form;

    public AddInstanceWorker(AbstractDialogAction action) {
        this.action = action;
        this.manager = action.getInstanceManager();
        this.form = action.getForm();
    }
    
    @Override
    protected Object doInBackground() throws Exception {
        DefaultInstanceValues vals = new DefaultInstanceValues();
        vals.setName(form.getInstanceName());
        vals.setUrl(form.getInstanceUrl());
        vals.setSyncInterval(form.getSyncTime());
        vals.setUsername(form.getUsername());
        vals.setPassword(form.getPassword());
        manager.addInstance(vals);
        return null;
    }

    @Override
    protected void done() {
        super.done();
        action.onDone();
    }
    
}
