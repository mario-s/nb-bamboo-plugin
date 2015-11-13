package org.netbeans.bamboo.ui.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.netbeans.bamboo.LookupProvider;
import org.netbeans.bamboo.model.BambooInstance;
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
