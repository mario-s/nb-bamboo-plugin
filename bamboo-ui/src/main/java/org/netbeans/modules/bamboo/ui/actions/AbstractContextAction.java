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

import java.util.Collection;
import java.util.Optional;
import javax.swing.AbstractAction;
import org.netbeans.modules.bamboo.model.rcp.Availability;
import org.openide.util.ContextAwareAction;
import org.openide.util.Lookup;
import org.openide.util.LookupListener;
import org.openide.util.Utilities;

/**
 *
 * @author Mario Schroeder
 */
abstract class AbstractContextAction extends AbstractAction implements LookupListener, ContextAwareAction {

    private Lookup context;

    public AbstractContextAction() {
        this("", Utilities.actionsGlobalContext());
    }

    AbstractContextAction(String name, Lookup context) {
        super(name);
        this.context = context;
    }

    protected Lookup getContext() {
        return context;
    }

    protected void enableIfAvailable(Collection<? extends Availability> instances) {
        Optional<? extends Availability> opt = instances.stream().filter(p -> p.isAvailable()).findAny();
        setEnabled(opt.isPresent());
    }
}
