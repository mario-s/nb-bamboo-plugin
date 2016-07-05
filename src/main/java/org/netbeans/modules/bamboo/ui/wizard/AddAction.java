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


/**
 * @author spindizzy
 */
@NbBundle.Messages({ "TXT_ADD=OK" })
class AddAction extends AbstractAction {
    /** Use serialVersionUID for interoperability. */
    private static final long serialVersionUID = 1L;

    private final Dialog dialog;

    private final InstancePropertiesForm form;

    private InstanceManageable manager;

    public AddAction(final Dialog dialog, final InstancePropertiesForm form) {
        super(TXT_ADD());
        manager = getDefault().lookup(InstanceManageable.class);
        this.form = form;
        this.dialog = dialog;
        disable();
    }

    private void disable() {
        setEnabled(false);
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        disable(); // block from clicking again
        addInstance();
        dispose();
    }

    private void addInstance() {
        form.block();
        DefaultInstanceValues vals = new DefaultInstanceValues();
        vals.setName(form.getInstanceName());
        vals.setUrl(form.getInstanceUrl());
        vals.setSyncInterval(form.getSyncTime());
        vals.setUsername(form.getUsername());
        vals.setPassword(form.getPassword());
        manager.addInstance(vals);
    }

    void dispose() {
        EventQueue.invokeLater(() -> { dialog.dispose(); });
    }
}
