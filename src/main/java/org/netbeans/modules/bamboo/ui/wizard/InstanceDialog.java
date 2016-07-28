package org.netbeans.modules.bamboo.ui.wizard;

import org.netbeans.modules.bamboo.glue.InstancePropertiesDisplayable;
import org.netbeans.modules.bamboo.glue.SharedConstants;
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


@Messages({ "LBL_DIALOG=Add Bamboo Instance" })
public class InstanceDialog extends DialogDescriptor implements InstancePropertiesDisplayable,
    PropertyChangeListener {
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
        form.setNotificationSupport(createNotificationLineSupport());
        dialog = DialogDisplayer.getDefault().createDialog(this);

        AbstractDialogAction action = new AddAction(form);
        form.setApplyAction(action);
        addPropertyChangeListener(action);

        action.addPropertyChangeListener(this);

        setOptions(new Object[] { new JButton(action), NotifyDescriptor.CANCEL_OPTION });
        setClosingOptions(new Object[] { NotifyDescriptor.CANCEL_OPTION });
    }

    @Override
    public void propertyChange(final PropertyChangeEvent pce) {
        EventQueue.invokeLater(() -> {
                String propName = pce.getPropertyName();

                if (SharedConstants.PROCESS_DONE.equals(propName) && dialog.isVisible()) {
                    dialog.dispose();
                }
            });
    }

    @Override
    public void show() {
        dialog.setVisible(true);
    }
}
