package org.netbeans.modules.bamboo.ui.wizard;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.AbstractAction;
import org.netbeans.modules.bamboo.glue.InstanceManageable;
import org.openide.NotifyDescriptor;

import static org.openide.util.Lookup.getDefault;
import static org.netbeans.modules.bamboo.ui.wizard.Bundle.TXT_ADD;

/**
 *
 * @author spindizzy
 */
abstract class AbstractDialogAction extends AbstractAction implements PropertyChangeListener {

    private InstanceManageable instanceManager;

    AbstractDialogAction(String name) {
        super(name);

        instanceManager = getDefault().lookup(InstanceManageable.class);
    }

    protected InstanceManageable getInstanceManager() {
        return instanceManager;
    }

    /**
     * Invoked when worker is done.
     */
    protected abstract void onDone();

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Object newValue = evt.getNewValue();

        if (NotifyDescriptor.CLOSED_OPTION.equals(newValue)) {
            onCancel();
        }
    }
    
    @Override
    public void actionPerformed(final ActionEvent e) {
        String cmd = (e != null) ? e.getActionCommand() : "";
        if(cmd.equals(TXT_ADD())){
            onOk();
        }else{
            onCancel();
        }
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
