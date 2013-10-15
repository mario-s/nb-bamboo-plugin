package org.netbeans.bamboo.ui.wizard;

import java.awt.Dialog;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import org.netbeans.bamboo.BambooManager;
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

  private InstancePropertiesForm form;

  public InstanceDialog() {
    this(new InstancePropertiesForm());
  }

  public InstanceDialog(final InstancePropertiesForm form) {
    super(form, LBL_DIALOG());
    this.form = form;

    final JButton addButton = new JButton(TXT_ADD());
    addButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        final String name = form.getInstanceName();
        final String url = form.getInstanceUrl();
        BambooManager.addInstance(name, url);
        EventQueue.invokeLater(new Runnable() {
          @Override
          public void run() {
            dialog.dispose();
          }
        });
      }
    });

    setOptions(new Object[]{addButton, NotifyDescriptor.CANCEL_OPTION});
    setClosingOptions(new Object[]{NotifyDescriptor.CANCEL_OPTION});
  }

  public void show() {
    dialog = DialogDisplayer.getDefault().createDialog(this);
    dialog.setVisible(true);
  }
}
