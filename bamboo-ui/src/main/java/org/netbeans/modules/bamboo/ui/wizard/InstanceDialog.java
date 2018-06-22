/* 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.netbeans.modules.bamboo.ui.wizard;

import org.netbeans.modules.bamboo.client.glue.InstancePropertiesDisplayable;
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
import org.openide.NotificationLineSupport;


@Messages({ "LBL_DIALOG=Add Bamboo Instance", "ERR_URL=The Bamboo Server Address is invalid." })
public class InstanceDialog extends DialogDescriptor implements InstancePropertiesDisplayable,
    PropertyChangeListener {
    
    private final InstancePropertiesForm form;
    
    private Dialog dialog;

    private NotificationLineSupport notificationLineSupport;

    public InstanceDialog() {
        this(new InstancePropertiesForm());
    }

    InstanceDialog(final InstancePropertiesForm form) {
        super(form, LBL_DIALOG());
        this.form = form;
        initializeGui();
    }

    private void initializeGui() {
        notificationLineSupport = createNotificationLineSupport();
        
        form.setNotificationSupport(notificationLineSupport);
        dialog = createDialog();

        AbstractDialogAction action = new AddAction(form);
        form.setApplyAction(action);
        addPropertyChangeListener(action);

        action.addPropertyChangeListener(this);

        setOptions(new Object[] { new JButton(action), NotifyDescriptor.CANCEL_OPTION });
        setClosingOptions(new Object[] { NotifyDescriptor.CANCEL_OPTION });
    }

    Dialog createDialog() {
        return DialogDisplayer.getDefault().createDialog(this);
    }

    @Override
    public void propertyChange(final PropertyChangeEvent event) {
        EventQueue.invokeLater(() -> {
                String propName = event.getPropertyName();

                if (WorkerEvents.INSTANCE_CREATED.name().equals(propName) && dialog.isVisible()) {
                    dialog.dispose();
                }else if(WorkerEvents.INVALID_URL.name().equals(propName)) {
                    notificationLineSupport.setErrorMessage(ERR_URL());
                    form.unblock();
                    form.setFocus(1);
                }
            });
    }

    @Override
    public void show() {
        dialog.setVisible(true);
    }
}
