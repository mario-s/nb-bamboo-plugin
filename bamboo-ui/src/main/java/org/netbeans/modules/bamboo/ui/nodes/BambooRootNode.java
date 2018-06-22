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
package org.netbeans.modules.bamboo.ui.nodes;

import java.util.Collection;
import org.netbeans.api.core.ide.ServicesTabNodeRegistration;

import org.netbeans.modules.bamboo.client.glue.InstanceManageable;
import org.netbeans.modules.bamboo.ui.actions.AddInstanceAction;

import static org.netbeans.modules.bamboo.ui.nodes.Bundle.*;

import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;

import org.openide.util.NbBundle.Messages;

import javax.swing.Action;
import org.netbeans.modules.bamboo.model.event.InstancesLoadEvent;

import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;

import static org.openide.util.Lookup.getDefault;
import static org.netbeans.modules.bamboo.ui.RootNodeConstants.*;

/**
 * Root node for the Bamboo Builder
 *
 * @author Mario Schroeder
 */
@ServicesTabNodeRegistration(
        name = BAMBOO_NODE_NAME, displayName = "#LBL_BambooNode",
        shortDescription = "#TIP_BambooNode", iconResource = ICON_BASE, position = 450
)
@Messages(
        {"LBL_BambooNode=Bamboo Builders", "TIP_BambooNode=Bamboo continuous integration servers."}
)
public final class BambooRootNode extends AbstractNode implements LookupListener {

    private final BambooInstanceNodeFactory nodeFactory;

    private Lookup.Result<InstancesLoadEvent> eventResult;

    private boolean lazy;

    private final InstanceManageable instanceManager;

    BambooRootNode() {
        this(true);
    }

    BambooRootNode(boolean lazy) {
        super(Children.LEAF, getDefault());
        this.lazy = lazy;

        instanceManager = getDefault().lookup(InstanceManageable.class);
        nodeFactory = new BambooInstanceNodeFactory(instanceManager.getLookup());

        init();
    }

    @Override
    public Action[] getActions(final boolean context) {
        return new Action[]{new AddInstanceAction()};
    }

    private void init() {
        eventResult = instanceManager.getLookup().lookupResult(InstancesLoadEvent.class);
        resultChanged(null);

        setName(BAMBOO_NODE_NAME);
        setDisplayName(LBL_BambooNode());
        setShortDescription(TIP_BambooNode());
        setIconBaseWithExtension(ICON_BASE);

        setChildren(Children.create(nodeFactory, lazy));
    }

    @Override
    public void resultChanged(final LookupEvent ev) {
        Collection<? extends InstancesLoadEvent> events = eventResult.allInstances();
        if (!events.isEmpty()) {
            nodeFactory.block();
        }
    }
    
}
