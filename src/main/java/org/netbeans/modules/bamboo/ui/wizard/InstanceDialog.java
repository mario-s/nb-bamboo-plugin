package org.netbeans.modules.bamboo.ui.wizard;

import org.netbeans.modules.bamboo.glue.InstancePropertiesDisplayable;
import static org.netbeans.modules.bamboo.ui.wizard.Bundle.*;

import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;

import org.openide.util.NbBundle.Messages;

import java.awt.Dialog;
import java.awt.EventQueue;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import org.openide.NotificationLineSupport;


@Messages({ "LBL_DIALOG=Add Bamboo Instance", "ERR_URL=The Bamboo Server Address is invalid." })
public class InstanceDialog extends DialogDescriptor implements InstancePropertiesDisplayable,
    PropertyChangeListener {
    
    private final InstancePropertiesForm form;
    
    private Dialog dialog;

    private NotificationLineSupport notificationLineSupport;

    public InstanceDialog() {
        this(new InstancePropertiesForm());
    }

    private InstanceDialog(final InstancePropertiesForm form) {
        super(form, LBL_DIALOG());
        this.form = form;
        initializeGui();
    }

    private void initializeGui() {
        notificationLineSupport = createNotificationLineSupport();
        
        form.setNotificationSupport(notificationLineSupport);
        dialog = DialogDisplayer.getDefault().createDialog(this);

        AbstractDialogAction action = new AddAction(form);
        form.setApplyAction(action);
        addPropertyChangeListener(action);

        action.addPropertyChangeListener(this);

        setOptions(new Object[] { new JButton(action), NotifyDescriptor.CANCEL_OPTION });
        setClosingOptions(new Object[] { NotifyDescriptor.CANCEL_OPTION });
    }

    @Override
    public void propertyChange(final PropertyChangeEvent event) {
        EventQueue.invokeLater(() -> {
                String propName = event.getPropertyName();

                if (WorkerEvents.INSTANCE_CREATED.name().equals(propName) && dialog.isVisible()) {
                    dialog.dispose();
                }else if(WorkerEvents.INVALID_URL.name().equals(propName)) {
                    notificationLineSupport.setErrorMessage(ERR_URL());
                    form.unblock();
                    form.setFocus(1);
                }
            });
    }

    @Override
    public void show() {
        dialog.setVisible(true);
    }
}
