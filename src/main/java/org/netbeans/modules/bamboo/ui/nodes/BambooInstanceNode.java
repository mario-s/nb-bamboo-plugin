package org.netbeans.modules.bamboo.ui.nodes;

import org.netbeans.api.annotations.common.StaticResource;

import org.netbeans.modules.bamboo.glue.InstanceManageable;
import org.netbeans.modules.bamboo.glue.ProjectsProvideable;
import org.netbeans.modules.bamboo.glue.SharedConstants;

import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;

import static org.openide.util.Lookup.getDefault;
import org.openide.util.Utilities;
import org.openide.util.lookup.Lookups;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import java.util.List;

import javax.swing.Action;
import org.netbeans.modules.bamboo.model.ModelProperties;
import org.netbeans.modules.bamboo.model.ProjectVo;
import org.openide.nodes.Sheet;
import org.openide.util.NbBundle.Messages;

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

/**
 * This class is the node of a Bamboo CI server.
 *
 * @author spindizzy
 */
public class BambooInstanceNode extends AbstractNode  {

    private static final String VERSION = "version";
    
    @StaticResource
    private static final String ICON_BASE = "org/netbeans/modules/bamboo/resources/instance.png";

    private final ProjectsProvideable instance;

    private final ProjectNodeFactory projectNodeFactory;

    public BambooInstanceNode(final ProjectsProvideable instance) {
        super(Children.LEAF, Lookups.singleton(instance));
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
    }

    @Override
    public Action[] getActions(final boolean context) {
        List<? extends Action> actions = Utilities.actionsForPath(SharedConstants.ACTION_PATH);

        return actions.toArray(new Action[actions.size()]);
    }

    @Override
    public boolean canDestroy() {
        return true;
    }

    @Override
    public void destroy() throws IOException {
        projectNodeFactory.removePropertyChangeListener();
        getDefault().lookup(InstanceManageable.class).removeInstance(instance);
        super.destroy();
    }

    @Override
    @Messages({
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
    protected Sheet createSheet() {
        Sheet sheet = Sheet.createDefault();
        Sheet.Set set = Sheet.createPropertiesSet();
        set.setDisplayName(instance.getName());

        set.put(new StringReadPropertySupport(SharedConstants.PROP_NAME, TXT_Instance_Prop_Name(), DESC_Instance_Prop_Name()) {
            @Override
            public String getValue() throws IllegalAccessException, InvocationTargetException {
                return instance.getName();
            }
        });

        set.put(new StringReadPropertySupport(SharedConstants.PROP_URL, TXT_Instance_Prop_Url(), DESC_Instance_Prop_Url()) {
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
        
        set.put(new IntReadPropertySupport(ModelProperties.Projects.toString(), TXT_Instance_Prop_Projects(), DESC_Instance_Prop_Projects()) {
            @Override
            public Integer getValue() throws IllegalAccessException, InvocationTargetException {
                final Collection<ProjectVo> projects = instance.getProjects();
                return (projects != null) ? projects.size() : 0;
            }
        });
        
        //TODO change to write property and update synchonization
        set.put(new IntReadPropertySupport(SharedConstants.PROP_SYNC_INTERVAL, TXT_Instance_Prop_SnycInterval(), DESC_Instance_Prop_SyncInterval()) {
            @Override
            public Integer getValue() throws IllegalAccessException, InvocationTargetException {
                return instance.getSyncInterval();
            }
        });

        sheet.put(set);
        
        return sheet;
    }

}
