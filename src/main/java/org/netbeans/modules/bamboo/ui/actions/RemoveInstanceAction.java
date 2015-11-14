package org.netbeans.modules.bamboo.ui.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.netbeans.modules.bamboo.LookupProvider;
import org.netbeans.modules.bamboo.model.BambooInstance;
import org.openide.util.NbBundle;

/**
 *
 * @author spindizzy
 */
@NbBundle.Messages({
  "LBL_Remove_Instance=Delete"
})
public class RemoveInstanceAction extends AbstractAction {

    private BambooInstance instance;
    
    public RemoveInstanceAction(BambooInstance instance) {
        super(Bundle.LBL_Remove_Instance());
        this.instance = instance;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        LookupProvider.Instance.getContent().remove(instance);
    }

}
