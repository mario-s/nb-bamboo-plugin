package org.netbeans.modules.bamboo.ui.notification;

import java.awt.Component;
import java.awt.EventQueue;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.JLabel;
import org.netbeans.api.annotations.common.StaticResource;
import org.netbeans.modules.bamboo.glue.InstanceManageable;
import org.netbeans.modules.bamboo.glue.ProjectsProvideable;
import org.netbeans.modules.bamboo.model.ModelProperties;
import org.netbeans.modules.bamboo.model.PlanVo;
import org.netbeans.modules.bamboo.model.ProjectVo;
import org.netbeans.modules.bamboo.model.ResultVo;
import org.openide.awt.NotificationDisplayer;
import org.openide.awt.StatusLineElementProvider;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;
import static org.openide.util.Lookup.getDefault;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author spindizzy
 */
@ServiceProvider(service = StatusLineElementProvider.class, position = 1)
public class BuildResultStatusBar implements StatusLineElementProvider, LookupListener, PropertyChangeListener {
    
    private static final Logger LOG = Logger.getLogger(BuildResultStatusBar.class.getName());

    @StaticResource
    private static final String ICON_BASE = "org/netbeans/modules/bamboo/resources/instance.png";

    private final InstanceManageable manager;

    private Lookup.Result<ProjectsProvideable> projectResult;
    
    private Collection<? extends ProjectsProvideable> projectsProviders;
    

    public BuildResultStatusBar() {
        manager = getDefault().lookup(InstanceManageable.class);
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
    public Component getStatusLineElement() {
        return null;
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
