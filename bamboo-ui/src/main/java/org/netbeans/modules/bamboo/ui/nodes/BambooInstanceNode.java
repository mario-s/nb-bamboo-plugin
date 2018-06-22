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

import java.beans.PropertyChangeEvent;
import org.netbeans.api.annotations.common.StaticResource;

import org.netbeans.modules.bamboo.client.glue.InstanceManageable;

import static org.openide.util.Lookup.getDefault;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;

import java.util.List;

import javax.swing.Action;
import org.netbeans.modules.bamboo.model.rcp.BambooInstance;
import org.netbeans.modules.bamboo.model.rcp.ModelChangedValues;
import org.netbeans.modules.bamboo.model.rcp.ProjectVo;
import org.netbeans.modules.bamboo.ui.actions.ActionConstants;
import org.openide.nodes.Sheet;
import org.openide.util.NbBundle.Messages;

import org.netbeans.modules.bamboo.client.glue.InstanceConstants;
import org.netbeans.modules.bamboo.model.event.ServerConnectionEvent;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.lookup.Lookups;

import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.netbeans.modules.bamboo.client.glue.InstanceConstants.PROP_SYNC_INTERVAL;

import static org.netbeans.modules.bamboo.ui.nodes.Bundle.DESC_Instance_Prop_Name;
import static org.netbeans.modules.bamboo.ui.nodes.Bundle.DESC_Instance_Prop_Projects;
import static org.netbeans.modules.bamboo.ui.nodes.Bundle.DESC_Instance_Prop_SyncInterval;
import static org.netbeans.modules.bamboo.ui.nodes.Bundle.DESC_Instance_Prop_Url;
import static org.netbeans.modules.bamboo.ui.nodes.Bundle.DESC_Instance_Prop_Version;
import static org.netbeans.modules.bamboo.ui.nodes.Bundle.TXT_Instance_Prop_Name;
import static org.netbeans.modules.bamboo.ui.nodes.Bundle.TXT_Instance_Prop_Projects;
import static org.netbeans.modules.bamboo.ui.nodes.Bundle.TXT_Instance_Prop_SnycInterval;
import static org.netbeans.modules.bamboo.ui.nodes.Bundle.TXT_Instance_Prop_Url;
import static org.netbeans.modules.bamboo.ui.nodes.Bundle.TXT_Instance_Prop_Version;
import static org.netbeans.modules.bamboo.ui.nodes.Bundle.Unavailable;
import static org.netbeans.modules.bamboo.ui.nodes.Bundle.Disconnected;

/**
 * This class is the node of a Bamboo CI server.
 *
 * @author Mario Schroeder
 */
@Messages({
    "Unavailable=Not Available",
    "Disconnected=Disconnected",
    "TXT_Instance_Prop_Name=Name",
    "DESC_Instance_Prop_Name=Bamboo instance name",
    "TXT_Instance_Prop_Url=URL",
    "DESC_Instance_Prop_Url=Bamboo instance URL",
    "TXT_Instance_Prop_Version=Version",
    "DESC_Instance_Prop_Version=Bamboo Version",
    "TXT_Instance_Prop_Projects=Projects",
    "DESC_Instance_Prop_Projects=number of all available build projects",
    "TXT_Instance_Prop_SnycInterval=Synchronization Interval",
    "DESC_Instance_Prop_SyncInterval=minutes for the next synchronization of the instance with the server"
})
public class BambooInstanceNode extends AbstractInstanceChildNode implements LookupListener {

    private static final String VERSION = "version";

    @StaticResource
    private static final String ICON_BASE = "org/netbeans/modules/bamboo/resources/instance.png";

    private static final String NO_CONTROL_SHADOW = "<font color='!controlShadow'>[%s]</font>";

    private final BambooInstance instance;

    private Lookup.Result<ServerConnectionEvent> connectionLookupResult;

    private String htmlDisplayName;

    public BambooInstanceNode(final BambooInstance instance) {
        super(new ProjectNodeFactory(instance), Lookups.singleton(instance));
        this.instance = instance;
        init();
    }

    private void init() {
        setName(instance.getName());
        setDisplayName(instance.getName());
        setShortDescription(instance.getUrl());
        setIconBaseWithExtension(ICON_BASE);

        connectionLookupResult = getLookup().lookupResult(ServerConnectionEvent.class);
        connectionLookupResult.addLookupListener(this);

        instance.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propName = evt.getPropertyName();
        if (ModelChangedValues.Projects.toString().equals(propName)) {
            refreshChildren();
        }else if(PROP_SYNC_INTERVAL.equals(propName)) {
            updateHtmlDisplayName();
        }
    }

    @Override
    public void resultChanged(LookupEvent ev) {
        updateHtmlDisplayName();
    }
    
    private boolean isConnected() {
        return instance.getSyncInterval() > 0;
    }

    private boolean isAvailable() {
        return instance.isAvailable();
    }

    @Override
    public Action[] getActions(final boolean context) {
        List<Action> actions = new ArrayList<>();
        
        actions.addAll(findActions(ActionConstants.COMMON_ACTION_PATH));
        actions.addAll(findActions(ActionConstants.ACTION_PATH));

        return actions.toArray(new Action[actions.size()]);
    }

    private void updateHtmlDisplayName() {
        String oldDisplayName = getHtmlDisplayName();
        StringBuilder builder = new StringBuilder(instance.getName());
        
        if(!isConnected()) {
            builder.append(SPACE).append(format(Disconnected()));
        } else if (!isAvailable()) {
            builder.append(SPACE).append(format(Unavailable()));
        }
        htmlDisplayName = builder.toString();

        fireDisplayNameChange(oldDisplayName, htmlDisplayName);
    }

    private String format(String state) {
        return String.format(NO_CONTROL_SHADOW, state);
    }

    @Override
    public String getHtmlDisplayName() {
        return htmlDisplayName;
    }

    @Override
    public boolean canDestroy() {
        return true;
    }

    @Override
    public void destroy() throws IOException {
        instance.removePropertyChangeListener(this);
        getDefault().lookup(InstanceManageable.class).removeInstance(instance);
        super.destroy();
    }

    @Override
    protected Sheet createSheet() {
        Sheet.Set set = Sheet.createPropertiesSet();
        set.setDisplayName(instance.getName());

        set.put(new StringReadPropertySupport(InstanceConstants.PROP_NAME, TXT_Instance_Prop_Name(),
                DESC_Instance_Prop_Name()) {
            @Override
            public String getValue() throws IllegalAccessException, InvocationTargetException {
                return instance.getName();
            }
        });

        set.put(new StringReadPropertySupport(InstanceConstants.PROP_URL, TXT_Instance_Prop_Url(),
                DESC_Instance_Prop_Url()) {
            @Override
            public String getValue() throws IllegalAccessException, InvocationTargetException {
                return instance.getUrl();
            }
        });

        set.put(new StringReadPropertySupport(VERSION, TXT_Instance_Prop_Version(), DESC_Instance_Prop_Version()) {
            @Override
            public String getValue() throws IllegalAccessException, InvocationTargetException {
                return (instance.getVersionInfo() != null) ? instance.getVersionInfo().getVersion() : "";
            }
        });

        set.put(new IntReadPropertySupport(ModelChangedValues.Projects.toString(), TXT_Instance_Prop_Projects(),
                DESC_Instance_Prop_Projects()) {
            @Override
            public Integer getValue() throws IllegalAccessException, InvocationTargetException {
                final Collection<ProjectVo> projects = instance.getChildren();
                return (projects != null) ? projects.size() : 0;
            }
        });

        set.put(new IntReadWritePropertySupport(InstanceConstants.PROP_SYNC_INTERVAL, TXT_Instance_Prop_SnycInterval(),
                DESC_Instance_Prop_SyncInterval()) {
            @Override
            public Integer getValue() throws IllegalAccessException, InvocationTargetException {
                return instance.getSyncInterval();
            }

            @Override
            public void setValue(Integer val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                instance.updateSyncInterval(val);
            }
        });

        Sheet sheet = Sheet.createDefault();
        sheet.put(set);

        return sheet;
    }

}
