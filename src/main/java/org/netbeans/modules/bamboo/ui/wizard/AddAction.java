package org.netbeans.modules.bamboo.ui.wizard;

import java.awt.Dialog;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.netbeans.modules.bamboo.BambooManager;
import org.netbeans.modules.bamboo.model.BambooInstance;
import org.netbeans.modules.bamboo.model.DefaultBambooInstance;
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
        final String name = form.getInstanceName();
        final String url = form.getInstanceUrl();
        int sync = form.getSyncTime();
        DefaultBambooInstance instance = new DefaultBambooInstance(name, url);
        instance.setSync(sync);
        BambooManager.addInstance(instance);
    }

    void dispose() {
        EventQueue.invokeLater(() -> {
            dialog.dispose();
        });
    }
}
