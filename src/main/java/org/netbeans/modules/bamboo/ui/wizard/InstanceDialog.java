package org.netbeans.modules.bamboo.ui.wizard;

import java.awt.Dialog;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import org.netbeans.modules.bamboo.BambooManager;
import org.netbeans.modules.bamboo.model.BambooInstance;
import org.netbeans.modules.bamboo.model.DefaultBambooInstance;
import org.openide.DialogDescriptor;
import org.openide.util.NbBundle.Messages;
import static org.netbeans.modules.bamboo.ui.wizard.Bundle.*;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;

@Messages({
    "LBL_DIALOG=Add Bamboo Instance",
    "TXT_ADD=OK"
})
public class InstanceDialog extends DialogDescriptor {

    private Dialog dialog;

    private final InstancePropertiesForm form;


    public InstanceDialog() {
        this(new InstancePropertiesForm());
    }

    private InstanceDialog(final InstancePropertiesForm form) {
        super(form, LBL_DIALOG());
        this.form = form;
        initializeGui();
    }

    private void initializeGui() {
        this.dialog = DialogDisplayer.getDefault().createDialog(this);
        
        AbstractAction action = new AddAction();
        JButton addButton = new JButton(action);
        form.setApplyAction(action);

        setOptions(new Object[]{addButton, NotifyDescriptor.CANCEL_OPTION});
        setClosingOptions(new Object[]{NotifyDescriptor.CANCEL_OPTION});
    }

    public void show() {
        dialog.setVisible(true);
    }

    private class AddAction extends AbstractAction {

        AddAction() {
            super(TXT_ADD());
            setEnabled(false);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            final String name = form.getInstanceName();
            final String url = form.getInstanceUrl();
            BambooManager.addInstance(name, url, 0);//TODO sync time
            EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    dialog.dispose();
                }
            });
        }
    }
}
