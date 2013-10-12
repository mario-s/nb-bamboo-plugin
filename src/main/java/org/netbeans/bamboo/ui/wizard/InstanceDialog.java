package org.netbeans.bamboo.ui.wizard;

import java.awt.Dialog;
import javax.swing.JButton;
import org.openide.DialogDescriptor;
import org.openide.util.NbBundle.Messages;
import static org.netbeans.bamboo.ui.wizard.Bundle.*;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;

@Messages({
  "LBL_DIALOG=Add Bamboo Instance",
  "TXT_ADD=OK"
})
public class InstanceDialog extends DialogDescriptor {

  private Dialog dialog;

  public InstanceDialog() {
    this(new InstancePropertiesForm());
  }

  public InstanceDialog(final InstancePropertiesForm form) {
    super(form, LBL_DIALOG());

    final JButton addButton = new JButton(TXT_ADD());
    setOptions(new Object[]{addButton, NotifyDescriptor.CANCEL_OPTION});
    setClosingOptions(new Object[]{NotifyDescriptor.CANCEL_OPTION});
  }

  public void show() {
    dialog = DialogDisplayer.getDefault().createDialog(this);
    dialog.setVisible(true);
  }
}
