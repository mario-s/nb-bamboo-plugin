package org.netbeans.modules.bamboo.ui.wizard;

import java.awt.Dialog;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.netbeans.modules.bamboo.BambooManager;
import org.netbeans.modules.bamboo.InstanceValues;
import static org.netbeans.modules.bamboo.ui.wizard.Bundle.TXT_ADD;
import org.openide.util.NbBundle;

/**
 *
 * @author spindizzy
 */
@NbBundle.Messages({
    "TXT_ADD=OK"
})
class AddAction extends AbstractAction {

    private final Dialog dialog;

    private final InstancePropertiesForm form;

    public AddAction(Dialog dialog, InstancePropertiesForm form) {
        super(TXT_ADD());
        this.form = form;
        this.dialog = dialog;
        setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        addInstance();
        dispose();
    }

    private void addInstance() {
        InstanceValues vals = new InstanceValues();
        vals.setName(form.getInstanceName());
        vals.setUrl(form.getInstanceUrl());
        vals.setSyncTime(form.getSyncTime());
        BambooManager.addInstance(vals);
    }

    void dispose() {
        EventQueue.invokeLater(() -> {
            dialog.dispose();
        });
    }
}
