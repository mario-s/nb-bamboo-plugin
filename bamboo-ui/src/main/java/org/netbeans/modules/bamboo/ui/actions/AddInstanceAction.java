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
package org.netbeans.modules.bamboo.ui.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import static org.netbeans.modules.bamboo.ui.actions.Bundle.*;
import org.netbeans.modules.bamboo.ui.wizard.InstanceDialog;
import org.netbeans.modules.bamboo.client.glue.InstancePropertiesDisplayable;
import org.openide.util.NbBundle.Messages;

/**
 * This action shows the dialog to add a new Bamboo instance.
 * @author Mario Schroeder
 */
@Messages({
    "LBL_Add_Instance=Add New Instance..."
})
public class AddInstanceAction extends AbstractAction {

    public AddInstanceAction() {
        super(LBL_Add_Instance());
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        createDialog().show();
    }

    InstancePropertiesDisplayable createDialog() {
        return new InstanceDialog();
    }

}
