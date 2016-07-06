package org.netbeans.modules.bamboo.ui.wizard;

import org.netbeans.modules.bamboo.glue.DefaultInstanceValues;
import org.netbeans.modules.bamboo.glue.InstanceManageable;
import static org.netbeans.modules.bamboo.ui.wizard.Bundle.TXT_ADD;

import static org.openide.util.Lookup.getDefault;
import org.openide.util.NbBundle;

import java.awt.Dialog;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.SwingWorker;
import org.netbeans.modules.bamboo.glue.PlansProvideable;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;

/**
 * @author spindizzy
 */
@NbBundle.Messages({"TXT_ADD=OK"})
class AddAction extends AbstractAction implements LookupListener {

    /**
     * Use serialVersionUID for interoperability.
     */
    private static final long serialVersionUID = 1L;

    private final Dialog dialog;

    private final InstancePropertiesForm form;

    private InstanceManageable manager;

    private Lookup.Result<PlansProvideable> result;

    public AddAction(final Dialog dialog, final InstancePropertiesForm form) {
        super(TXT_ADD());
        this.form = form;
        this.dialog = dialog;
        addLookup();
        disable();
    }

    private void addLookup() {
        manager = getDefault().lookup(InstanceManageable.class);
        result = manager.getLookup().lookupResult(PlansProvideable.class);
        result.addLookupListener(this);
        resultChanged(null);
    }

    private void disable() {
        setEnabled(false);
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        disable(); // block from clicking again
        addInstance();
    }

    private void addInstance() {
        form.block();
        Worker worker = new Worker();
        worker.execute();
    }

    void dispose() {
        EventQueue.invokeLater(() -> {
            dialog.dispose();
        });
    }

    @Override
    public void resultChanged(LookupEvent le) {
        if (le != null) {
            form.unblock();
        }
    }

    private class Worker extends SwingWorker<Object, Object> {

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
            dispose();
        }
    }
}
