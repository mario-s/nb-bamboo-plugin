package org.netbeans.modules.bamboo.rest;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import org.netbeans.modules.bamboo.model.DefaultInstanceValues;
import org.netbeans.modules.bamboo.model.InstanceValues;
import org.netbeans.modules.bamboo.model.VersionInfo;

import org.openide.util.RequestProcessor;
import org.openide.util.RequestProcessor.Task;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import java.util.Collection;

import java.util.Optional;

import java.util.logging.Level;
import java.util.prefs.Preferences;
import lombok.NonNull;
import org.apache.commons.lang3.time.StopWatch;

import static java.util.Optional.empty;
import static java.util.Optional.of;

import lombok.extern.java.Log;
import org.netbeans.modules.bamboo.model.BambooInstance;
import org.netbeans.modules.bamboo.model.LookupContext;

import static org.netbeans.modules.bamboo.glue.InstanceConstants.PROP_SYNC_INTERVAL;

import org.netbeans.modules.bamboo.model.ChangeEvents;
import org.netbeans.modules.bamboo.model.ProjectVo;

import org.openide.util.Lookup;
import org.netbeans.modules.bamboo.glue.InstanceConstants;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

import org.netbeans.modules.bamboo.glue.BambooClient;
import org.netbeans.modules.bamboo.model.PlanVo;

import static java.lang.String.format;
import static java.lang.String.format;

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

    private final LookupContext lookupContext;

    private transient Optional<BambooClient> optClient;

    private transient Optional<Task> synchronizationTask = empty();

    private transient Collection<ProjectVo> projects;

    private transient VersionInfo version;

    private transient boolean available;

    private BambooInstanceProperties properties;

    DefaultBambooInstance(final BambooInstanceProperties properties) {
        this(null, empty());
        copyProperties(properties);
        optClient = of(new DefaultBambooClient(this));
    }

    DefaultBambooInstance(final InstanceValues values, final Optional<BambooClient> optClient) {
        super(values);
        this.optClient = optClient;
        changeSupport = new PropertyChangeSupport(this);
        lookupContext = LookupContext.Instance;
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
        return lookupContext.getLookup();
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
        firePropertyChange(ChangeEvents.Synchronizing.toString(), !value, value);
    }

    private void synchronizeProjects() {
        optClient.ifPresent(client -> {
            Collection<ProjectVo> oldProjects = this.projects;
            if (oldProjects == null || oldProjects.isEmpty()) {
                Collection<ProjectVo> newProjects = client.getProjects();
                this.projects = newProjects;
                fireProjectsChanged(oldProjects, newProjects);
            } else {
                client.updateProjects(this.projects);
                fireProjectsChanged(oldProjects, this.projects);
            }
        });
    }

    private void synchronizeVersion() {
        optClient.ifPresent(client -> {
            version = client.getVersionInfo();
        });
    }

    private void fireProjectsChanged(Collection<ProjectVo> oldProjects, Collection<ProjectVo> newProjects) {
        firePropertyChange(ChangeEvents.Projects.toString(), oldProjects,
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
        this.projects.parallelStream().forEach(pr -> pr.setParent(this));
        prepareSynchronization();
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
        if (log.isLoggable(Level.INFO)) {
            log.info(format("client is present: %s", optClient.isPresent()));
        }

        available = (optClient.isPresent()) ? optClient.get().existsService() : false;

        return available;
    }

    @Override
    public boolean queue(@NonNull PlanVo plan) {
        boolean queued = false;
        final Optional<ProjectVo> parent = plan.getParent();
        if (isChild(parent) && verifyAvailibility()) {
            int status = optClient.get().queue(plan);
            HttpResponseCode code = HttpResponseCode.getCode(status);
            queued = code.equals(HttpResponseCode.Successful);
        }

        return queued;
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
    public Task synchronize() {
        return RP.post(() -> {
            if (verifyAvailibility()) {
                synchronizeVersion();
                doSynchronization(false);
                prepareSynchronization();
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
