package org.netbeans.modules.bamboo.client.rest;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import org.netbeans.modules.bamboo.model.rcp.DefaultInstanceValues;
import org.netbeans.modules.bamboo.model.rcp.InstanceValues;
import org.netbeans.modules.bamboo.model.rcp.VersionInfo;
import org.openide.util.RequestProcessor;
import org.openide.util.RequestProcessor.Task;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import java.util.Optional;
import java.util.Set;

import java.util.logging.Level;
import java.util.prefs.Preferences;
import javax.ws.rs.core.Response;
import org.netbeans.api.annotations.common.NonNull;
import org.apache.commons.lang3.time.StopWatch;

import static java.util.Optional.empty;
import static java.util.Optional.of;

import lombok.extern.java.Log;
import org.netbeans.modules.bamboo.model.rcp.BambooInstance;

import static org.netbeans.modules.bamboo.client.glue.InstanceConstants.PROP_SYNC_INTERVAL;

import org.netbeans.modules.bamboo.model.rcp.ModelChangedValues;
import org.netbeans.modules.bamboo.model.rcp.ProjectVo;
import org.openide.util.Lookup;
import org.netbeans.modules.bamboo.client.glue.InstanceConstants;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

import org.netbeans.modules.bamboo.model.rcp.PlanVo;
import org.netbeans.modules.bamboo.model.event.QueueEvent;
import org.netbeans.modules.bamboo.model.event.QueueEvent.QueueEventBuilder;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;

import static org.netbeans.modules.bamboo.client.rest.BambooInstanceConstants.INSTANCE_SUPPRESSED_PLANS;
import static java.lang.String.format;

import org.netbeans.modules.bamboo.client.glue.ExpandParameter;
import org.netbeans.modules.bamboo.model.rcp.ResultVo;

/**
 * @author spindizzy
 */
@Log
class DefaultBambooInstance extends DefaultInstanceValues implements BambooInstance {

    /**
     * Use serialVersionUID for interoperability.
     */
    private static final long serialVersionUID = 1L;

    private static final RequestProcessor RP = new RequestProcessor(
            DefaultBambooInstance.class);

    private final transient StopWatch stopWatch = new StopWatch();

    private final PropertyChangeSupport changeSupport;

    private final Lookup lookup;

    private final InstanceContent content;

    private transient AbstractBambooClient client;

    private transient Optional<Task> synchronizationTask = empty();

    private transient Collection<ProjectVo> projects;

    private transient VersionInfo version;

    private transient boolean available = true;

    private final List<String> suppressedPlans;

    private BambooInstanceProperties properties;

    DefaultBambooInstance(final BambooInstanceProperties properties) {
        this(null, null);
        copyProperties(properties);
        this.client = new DefaultBambooClient(this);
    }

    DefaultBambooInstance(final InstanceValues values, final AbstractBambooClient client) {
        super(values);

        this.changeSupport = new PropertyChangeSupport(this);
        this.content = new InstanceContent();
        this.lookup = new AbstractLookup(content);
        this.suppressedPlans = new ArrayList<>();
        this.client = client;

        addConnectionListener();
    }

    @Override
    public Collection<String> getSuppressedPlans() {
        return suppressedPlans;
    }

    private void addConnectionListener() {
        InstanceConnectionListener listener = new InstanceConnectionListener();
        addPropertyChangeListener(listener);
    }

    @Override
    public void addPropertyChangeListener(final PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    @Override
    public VersionInfo getVersionInfo() {
        return version;
    }

    @Override
    public Lookup getLookup() {
        return lookup;
    }

    private void copyProperties(final BambooInstanceProperties props) throws NumberFormatException {
        setName(props.get(InstanceConstants.PROP_NAME));
        setUrl(props.get(InstanceConstants.PROP_URL));
        setUsername(props.get(BambooInstanceConstants.INSTANCE_USER));

        String passwd = props.get(BambooInstanceConstants.INSTANCE_PASSWORD);

        if (isNotBlank(passwd)) {
            setPassword(passwd.toCharArray());
        }

        String syncProp = props.get(InstanceConstants.PROP_SYNC_INTERVAL);

        if (isNotBlank(syncProp)) {
            setSyncInterval(Integer.parseInt(syncProp));
        }

        String saved = props.get(INSTANCE_SUPPRESSED_PLANS);
        suppressedPlans.addAll(StringUtil.split(saved));

        this.properties = props;
    }

    private void doSynchronization(boolean showProgress) {
        if (log.isLoggable(Level.INFO)) {
            stopWatch.start();
        }

        if (showProgress) {
            fireSynchronizationChange(true);
        }

        try {
            synchronizeProjects();
        } finally {

            if (showProgress) {
                fireSynchronizationChange(false);
            }

            if (log.isLoggable(Level.INFO)) {
                stopWatch.stop();
                log.info(
                        String.format("synchronized %s in %s", getName(), stopWatch));
                stopWatch.reset();
            }

            synchronized (this) {
                notifyAll();
            }
        }
    }

    private void fireSynchronizationChange(boolean value) {
        firePropertyChange(ModelChangedValues.Synchronizing.toString(), !value, value);
    }

    private void synchronizeProjects() {
        Collection<ProjectVo> oldProjects = this.projects;
        if (oldProjects == null || oldProjects.isEmpty()) {
            Collection<ProjectVo> newProjects = client.getProjects();
            setChildren(newProjects);
            fireProjectsChanged(oldProjects, newProjects);
        } else {
            client.updateProjects(this.projects);
            updateParent(this.projects);
            fireProjectsChanged(oldProjects, this.projects);
        }
    }

    //set the parent if not present
    private void updateParent(Collection<ProjectVo> children) {
        children.parallelStream().forEach(child -> {
            if (!child.getParent().isPresent()) {
                child.setParent(this);
            }
        });
    }

    private void synchronizeVersion() {
        version = client.getVersionInfo();
    }

    private void fireProjectsChanged(Collection<ProjectVo> oldProjects, Collection<ProjectVo> newProjects) {
        firePropertyChange(ModelChangedValues.Projects.toString(), oldProjects,
                newProjects);
    }

    @Override
    public Preferences getPreferences() {
        return properties.getPreferences();
    }

    /**
     * Returns all the projects for the bamboo instance.
     *
     * @return a collection where no element can be added or removed.
     */
    @Override
    public Collection<ProjectVo> getChildren() {
        return (projects == null) ? emptyList() : asList(projects.toArray(new ProjectVo[projects.size()]));
    }

    Optional<Task> getSynchronizationTask() {
        return synchronizationTask;
    }

    @Override
    public void setChildren(final Collection<ProjectVo> projects) {
        this.projects = projects;
        suppress(projects);
        updateParent(projects);
        prepareSynchronization();
    }

    private void suppress(Collection<ProjectVo> projects) {
        if (!suppressedPlans.isEmpty()) {
            final Set<String> keys = new HashSet<>(suppressedPlans);
            projects.forEach(project -> {
                project.getChildren().forEach(plan -> {
                    String key = plan.getKey();
                    if (keys.contains(key)) {
                        plan.setNotify(false);
                    }
                });
            });
        }
    }

    private void prepareSynchronization() {
        int interval = getSyncIntervalInMillis();

        log.info(String.format("interval: %s", interval));
        if (interval > 0) {
            scheduleTask(interval);
        }
    }

    private void scheduleTask(int interval) {
        Task task = RP.create(() -> {
            if (verifyAvailibility()) {
                doSynchronization(true);
            }

            if (synchronizationTask.isPresent() && (interval > 0)) {
                synchronizationTask.get().schedule(interval);
            }
        });
        synchronizationTask = of(task);
        task.schedule(interval);
    }

    private int getSyncIntervalInMillis() {
        return toMillis(getSyncInterval());
    }

    @Override
    public boolean isAvailable() {
        return available;
    }

    private boolean verifyAvailibility() {
        boolean oldVal = this.available;
        available = client.existsService();

        if (log.isLoggable(Level.INFO)) {
            log.info(format("service is available: %s", available));
        }

        firePropertyChange(ModelChangedValues.Available.toString(), oldVal, available);

        return available;
    }

    @Override
    public void queue(@NonNull PlanVo plan) {
        RP.post(() -> {
            final Optional<ProjectVo> parent = plan.getParent();
            if (isChild(parent) && verifyAvailibility()) {
                Response response = client.queue(plan);
                QueueEventBuilder eventBuilder = QueueEvent.builder().plan(plan).response(response);
                content.add(eventBuilder.build());
            }
        });
    }

    @Override
    public void attachChanges(ResultVo result) {
        attach(result, ExpandParameter.RESULT_CHANGED_FILES);
    }
    
    private void attach(ResultVo result, String expandParam) {
        if(verifyAvailibility()){
            client.attach(result, expandParam);
        }
    }

    @Override
    public void remove() {
        if (properties != null) {
            properties.clear();
        }
    }

    @Override
    public void removePropertyChangeListener(
            final PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }

    @Override
    public void updateNotify(PlanVo plan) {
        List<String> old = new ArrayList<>(suppressedPlans);
        String key = plan.getKey();
        boolean notify = plan.isNotify();

        if (notify) {
            suppressedPlans.remove(key);
        } else if (!suppressedPlans.contains(key)) {
            suppressedPlans.add(key);
        }

        firePropertyChange(INSTANCE_SUPPRESSED_PLANS, old, suppressedPlans);
    }

    @Override
    public Task synchronize() {
        return RP.post(() -> {
            if (verifyAvailibility()) {
                synchronizeVersion();
                doSynchronization(false);
            }
        });
    }

    private int toMillis(final int minutes) {
        return minutes * 60000;
    }

    protected void firePropertyChange(final String propertyName,
            final Object oldValue,
            final Object newValue) {
        changeSupport.firePropertyChange(propertyName, oldValue, newValue);
    }

    void setVersionInfo(final VersionInfo version) {
        this.version = version;
    }

    @Override
    public void updateSyncInterval(int minutes) {
        int oldInterval = getSyncInterval();
        setSyncInterval(minutes);
        if (synchronizationTask.isPresent()) {
            synchronizationTask.get().cancel();
        }
        firePropertyChange(PROP_SYNC_INTERVAL, oldInterval, minutes);
        prepareSynchronization();
    }
}
