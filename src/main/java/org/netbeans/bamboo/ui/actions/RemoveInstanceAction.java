package org.netbeans.bamboo.ui.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.netbeans.bamboo.BambooManager;
import org.openide.nodes.Node;
import org.openide.util.NbBundle;

/**
 *
 * @author spindizzy
 */
@NbBundle.Messages({
  "LBL_Remove_Instance=Delete"
})
public class RemoveInstanceAction extends AbstractAction {

    private Node node;
    
    public RemoveInstanceAction(Node node) {
        super(Bundle.LBL_Remove_Instance());
        this.node = node;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        BambooManager.removeInstance(node.getName());
    }

}
