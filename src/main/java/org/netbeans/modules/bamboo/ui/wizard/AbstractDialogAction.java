package org.netbeans.modules.bamboo.ui.wizard;

import javax.swing.AbstractAction;
import org.netbeans.modules.bamboo.glue.InstanceManageable;
import static org.openide.util.Lookup.getDefault;

/**
 *
 * @author spindizzy
 */
abstract class AbstractDialogAction extends AbstractAction{
    
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
}
