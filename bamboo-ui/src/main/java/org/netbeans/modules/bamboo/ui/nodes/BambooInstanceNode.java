package org.netbeans.modules.bamboo.ui.nodes;

import java.beans.PropertyChangeEvent;
import org.netbeans.api.annotations.common.StaticResource;

import org.netbeans.modules.bamboo.glue.InstanceManageable;

import org.openide.nodes.Children;

import static org.openide.util.Lookup.getDefault;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import java.util.List;
import java.util.Optional;

import javax.swing.Action;
import org.netbeans.modules.bamboo.model.BambooInstance;
import org.netbeans.modules.bamboo.model.ModelChangedValues;
import org.netbeans.modules.bamboo.model.ProjectVo;
import org.netbeans.modules.bamboo.ui.actions.ActionConstants;
import org.openide.nodes.Sheet;
import org.openide.util.NbBundle.Messages;

import org.netbeans.modules.bamboo.glue.InstanceConstants;
import org.openide.util.LookupEvent;

import static java.util.Optional.of;
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

import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.netbeans.modules.bamboo.ui.nodes.Bundle.Disconnected;


/**
 * This class is the node of a Bamboo CI server.
 *
 * @author spindizzy
 */
@Messages({
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
public class BambooInstanceNode extends AbstractInstanceChildNode {

    private static final String VERSION = "version";

    @StaticResource
    private static final String ICON_BASE = "org/netbeans/modules/bamboo/resources/instance.png";

    private static final String NO_CONTROL_SHADOW = "<font color='!controlShadow'>[%s]</font>";

    private final BambooInstance instance;

    private final ProjectNodeFactory projectNodeFactory;

    private String htmlDisplayName;

    public BambooInstanceNode(final BambooInstance instance) {
        super(instance.getLookup());
        this.instance = instance;
        this.projectNodeFactory = new ProjectNodeFactory(instance);
        init();
    }

    private void init() {
        setName(instance.getUrl());
        setDisplayName(instance.getName());
        setShortDescription(instance.getUrl());
        setIconBaseWithExtension(ICON_BASE);

        setChildren(Children.create(projectNodeFactory, true));

        instance.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String eventName = evt.getPropertyName();
        if (ModelChangedValues.Projects.toString().equals(eventName)) {
            projectNodeFactory.refreshNodes();
        }
    }

    @Override
    public void resultChanged(LookupEvent ev) {
        super.resultChanged(ev);
        updateHtmlDisplayName();
    }

    private boolean isAvailable() {
        return instance.isAvailable();
    }

    @Override
    protected Optional<BambooInstance> getInstance() {
        return of(instance);
    }

    @Override
    protected List<? extends Action> getToogleableActions() {
        return findActions(ActionConstants.ACTION_PATH);
    }

    @Override
    public Action[] getActions(final boolean context) {
        List<? extends Action> actions = findActions(ActionConstants.ACTION_PATH);

        return actions.toArray(new Action[actions.size()]);
    }

    private void updateHtmlDisplayName() {
        String oldDisplayName = getHtmlDisplayName();
        StringBuilder builder = new StringBuilder(instance.getName());
        if (!isAvailable()) {
            builder.append(SPACE).append(String.format(NO_CONTROL_SHADOW, Disconnected()));
        }
        htmlDisplayName = builder.toString();

        fireDisplayNameChange(oldDisplayName, htmlDisplayName);
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
