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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;

import javax.swing.Action;
import org.netbeans.modules.bamboo.model.rcp.OpenableInBrowser;
import org.netbeans.modules.bamboo.ui.BrowserInstance;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.NbBundle.Messages;

/**
 * This action opens the url of the give openable in the prefered browser.
 *
 * @author Mario Schroeder
 */
@ActionID(
        category = "Bamboo",
        id = "org.netbeans.modules.bamboo.ui.actions.OpenUrlAction"
)
@ActionRegistration(
        displayName = "#CTL_OpenUrlAction", lazy = false
)
@ActionReference(path = ActionConstants.COMMON_ACTION_PATH, position = 600)
@Messages("CTL_OpenUrlAction=&Open in Browser")
public final class OpenUrlAction extends AbstractContextAction {


    private Lookup.Result<OpenableInBrowser> result;
    
    private BrowserInstance browser;

    public OpenUrlAction() {
    }

    OpenUrlAction(Lookup context) {
        super(Bundle.CTL_OpenUrlAction(), context);
        browser = BrowserInstance.Instance;
        init();
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        allInstances().forEach(o -> {
            try {
                browser.showURL(new URL(o.getUrl()));
            } catch (MalformedURLException ex) {
                Exceptions.printStackTrace(ex);
            }
        });
    }

    private Collection<? extends OpenableInBrowser> allInstances() {
        return result.allInstances();
    }

    @Override
    public Action createContextAwareInstance(Lookup actionContext) {
        return new OpenUrlAction(actionContext);
    }

    private void init() {
        result = getContext().lookupResult(OpenableInBrowser.class);
        result.addLookupListener(this);
        resultChanged(null);
    }

    @Override
    public void resultChanged(LookupEvent ev) {
        enableIfAvailable(allInstances());
    }
}
