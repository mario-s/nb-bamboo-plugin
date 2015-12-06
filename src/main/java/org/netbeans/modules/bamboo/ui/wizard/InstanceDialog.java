package org.netbeans.modules.bamboo.ui.wizard;

import java.awt.Dialog;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import org.openide.DialogDescriptor;
import org.openide.util.NbBundle.Messages;
import static org.netbeans.modules.bamboo.ui.wizard.Bundle.*;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;

@Messages({
    "LBL_DIALOG=Add Bamboo Instance"
})
public class InstanceDialog extends DialogDescriptor implements InstancePropertiesDisplayable{

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
        form.setNotificationSupport(createNotificationLineSupport());
        
        AbstractAction action = new AddAction(dialog, form);
        form.setApplyAction(action);
        
        setOptions(new Object[]{new JButton(action), NotifyDescriptor.CANCEL_OPTION});
        setClosingOptions(new Object[]{NotifyDescriptor.CANCEL_OPTION});
    }

    @Override
    public void show() {
        dialog.setVisible(true);
    }
}
