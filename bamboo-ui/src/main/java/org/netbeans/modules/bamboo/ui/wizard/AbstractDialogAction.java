package org.netbeans.modules.bamboo.ui.wizard;

import org.netbeans.modules.bamboo.client.glue.InstanceManageable;
import static org.netbeans.modules.bamboo.ui.wizard.Bundle.TXT_ADD;

import org.openide.NotifyDescriptor;

import static org.openide.util.Lookup.getDefault;

import java.awt.event.ActionEvent;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractAction;


/**
 * @author spindizzy
 */
abstract class AbstractDialogAction extends AbstractAction implements PropertyChangeListener {
    private InstanceManageable instanceManager;

    AbstractDialogAction(final String name) {
        super(name);

        instanceManager = getDefault().lookup(InstanceManageable.class);
    }

    protected InstanceManageable getInstanceManager() {
        return instanceManager;
    }


    @Override
    public void propertyChange(final PropertyChangeEvent evt) {
        Object newValue = evt.getNewValue();

        if (NotifyDescriptor.CLOSED_OPTION.equals(newValue) ||
                NotifyDescriptor.CANCEL_OPTION.equals(newValue)) {
            onCancel();
        }
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        String cmd = (e != null) ? e.getActionCommand() : "";

        if (cmd.equals(TXT_ADD())) {
            onOk();
        }
    }

    @Override
    public void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        super.firePropertyChange(propertyName, oldValue, newValue);
    }  
    
    void firePropertyChange(PropertyChangeEvent event) {
        firePropertyChange(event.getPropertyName(), event.getOldValue(), event.getNewValue());
    }

    /**
     * This method is called when the user presses ok.
     */
    protected void onOk() {
    }

    /**
     * This method is called when the user cancels the dialog.
     */
    protected void onCancel() {
    }
}
