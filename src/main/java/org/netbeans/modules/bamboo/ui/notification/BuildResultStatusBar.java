package org.netbeans.modules.bamboo.ui.notification;

import java.awt.EventQueue;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collection;
import java.util.logging.Logger;
import javax.swing.Icon;
import lombok.NonNull;
import org.netbeans.api.annotations.common.StaticResource;
import org.netbeans.modules.bamboo.glue.BuildStatusNotifyable;
import org.netbeans.modules.bamboo.glue.InstanceManageable;
import org.netbeans.modules.bamboo.glue.ProjectsProvideable;
import org.netbeans.modules.bamboo.model.ModelProperties;
import org.netbeans.modules.bamboo.model.PlanVo;
import org.netbeans.modules.bamboo.model.ResultVo;
import org.openide.awt.NotificationDisplayer;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author spindizzy
 */
@ServiceProvider(service = BuildStatusNotifyable.class, position = 1)
public class BuildResultStatusBar implements BuildStatusNotifyable, LookupListener, PropertyChangeListener {
    
    private static final Logger LOG = Logger.getLogger(BuildResultStatusBar.class.getName());

    @StaticResource
    private static final String ICON_BASE = "org/netbeans/modules/bamboo/resources/instance.png";

    private InstanceManageable manager;

    private Lookup.Result<ProjectsProvideable> projectResult;
    
    private Collection<? extends ProjectsProvideable> projectsProviders;

    @Override
    public void setManager(@NonNull InstanceManageable manager) {
        this.manager = manager;
        init();
    }
    
    private void init() {
        projectResult = manager.getLookup().lookupResult(ProjectsProvideable.class);
        projectResult.addLookupListener(this);
    }

    private Icon getIcon() {
        return ImageUtilities.loadImageIcon(ICON_BASE, true);
    }

    @Override
    public void resultChanged(LookupEvent ev) {
        projectsProviders = projectResult.allInstances();
        registerChangeListener();
    }

    private void registerChangeListener() {
        projectsProviders.forEach(provider -> {
            provider.getProjects().forEach(project -> {
                project.getPlans().forEach(plan -> {
                    plan.addPropertyChangeListener(this);
                });
            });
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals(ModelProperties.Result.toString())){
            add((PlanVo) evt.getSource());
        }
    }

    private void add(PlanVo plan) {
        EventQueue.invokeLater(() -> {
            String name = plan.getName();
            ResultVo resVo = plan.getResult();
            String details = resVo.getState().toString();
            LOG.info(String.format("state of plan %s has changed to %s", name, details));
            NotificationDisplayer.getDefault().notify(name, getIcon(), details, null);
        });
    }
}
