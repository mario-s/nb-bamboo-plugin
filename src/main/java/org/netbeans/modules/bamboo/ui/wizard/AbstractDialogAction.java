package org.netbeans.modules.bamboo.ui.wizard;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.AbstractAction;
import org.netbeans.modules.bamboo.glue.InstanceManageable;
import org.openide.NotifyDescriptor;
import static org.openide.util.Lookup.getDefault;

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

    protected abstract InstancePropertiesForm getForm();

    /**
     * Invoked when worker is done.
     */
    protected abstract void onDone();

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Object newValue = evt.getNewValue();

        if (NotifyDescriptor.CLOSED_OPTION.equals(newValue) || NotifyDescriptor.CANCEL_OPTION.equals(newValue)) {
            onCanceled();
        }
    }
    
    /**
     * This method is called when the user canceld the dialog.
     */
    protected void onCanceled() {
    }
}
