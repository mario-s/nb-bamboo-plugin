package org.netbeans.modules.bamboo.rest;

import org.netbeans.modules.bamboo.glue.BambooServiceAccessable;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import org.netbeans.modules.bamboo.glue.DefaultInstanceValues;
import org.netbeans.modules.bamboo.glue.InstanceValues;
import org.netbeans.modules.bamboo.glue.VersionInfo;

import static org.openide.util.Lookup.getDefault;

import org.openide.util.RequestProcessor;
import org.openide.util.RequestProcessor.Task;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import java.util.Collection;

import static java.util.Collections.emptyList;

import java.util.Optional;

import java.util.logging.Level;
import java.util.prefs.Preferences;
import org.apache.commons.lang3.time.StopWatch;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.progress.ProgressHandleFactory;
import org.openide.util.NbBundle.Messages;

import static java.util.Optional.empty;
import static java.util.Optional.of;

import lombok.extern.java.Log;
import org.netbeans.modules.bamboo.glue.BambooInstance;
import org.netbeans.modules.bamboo.glue.LookupContext;

import static org.netbeans.modules.bamboo.glue.InstanceConstants.PROP_SYNC_INTERVAL;

import org.netbeans.modules.bamboo.model.ModelProperties;
import org.netbeans.modules.bamboo.model.ProjectVo;

import static org.netbeans.modules.bamboo.rest.Bundle.TXT_SYNC;

import org.openide.util.Lookup;
import org.netbeans.modules.bamboo.glue.InstanceConstants;

/**
 * @author spindizzy
 */
@Log
public class DefaultBambooInstance extends DefaultInstanceValues implements BambooInstance {

    /**
     * Use serialVersionUID for interoperability.
     */
    private static final long serialVersionUID = 1L;
    private static final RequestProcessor RP = new RequestProcessor(
            DefaultBambooInstance.class);

    private final transient StopWatch stopWatch = new StopWatch();

    private final PropertyChangeSupport changeSupport;

    private final transient BambooServiceAccessable client;

    private final LookupContext lookupContext;

    private transient Optional<Task> synchronizationTask = empty();

    private transient Collection<ProjectVo> projects;

    private BambooInstanceProperties properties;

    private transient VersionInfo version;

    public DefaultBambooInstance() {
        this(null);
    }

    public DefaultBambooInstance(final InstanceValues values) {
        super(values);
        changeSupport = new PropertyChangeSupport(this);
        lookupContext = LookupContext.Instance;
        client = getDefault().lookup(BambooServiceAccessable.class);
    }

    @Override
    public void addPropertyChangeListener(final PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void applyProperties(final BambooInstanceProperties properties) {
        this.properties = properties;
        copyProperties(properties);
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
    }

    private void doSynchronization(boolean showProgress) {
        if (log.isLoggable(Level.INFO)) {
            stopWatch.start();
        }

        ProgressHandle progressHandle = null;
        if (showProgress) {
            progressHandle = createProgressHandle();
            progressHandle.start();
        }

        try {
            synchronizeProjects();
        } finally {

            if (progressHandle != null) {
                progressHandle.finish();
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

    private void synchronizeProjects() {
        Collection<ProjectVo> oldProjects = this.projects;
        if (oldProjects == null || oldProjects.isEmpty()) {
            Collection<ProjectVo> newProjects = client.getProjects(this);
            this.projects = newProjects;
            fireProjectsChanged(oldProjects, newProjects);
        } else {
            client.updateProjects(this.projects, this);
            fireProjectsChanged(oldProjects, this.projects);
        }
    }
    
    private void synchronizeVersion() {
        version = client.getVersionInfo(this);
    }

    private void fireProjectsChanged(Collection<ProjectVo> oldProjects, Collection<ProjectVo> newProjects) {
        firePropertyChange(ModelProperties.Projects.toString(), oldProjects,
                newProjects);
    }

    @Messages({"TXT_SYNC=Synchronizing"})
    private ProgressHandle createProgressHandle() {
        return ProgressHandleFactory.createHandle(TXT_SYNC() + " " + getName());
    }

    @Override
    public Preferences getPreferences() {
        return properties.getPreferences();
    }

    @Override
    public Collection<ProjectVo> getProjects() {
        return (projects == null) ? emptyList() : projects;
    }

    Optional<Task> getSynchronizationTask() {
        return synchronizationTask;
    }

    @Override
    public void setProjects(final Collection<ProjectVo> projects) {
        this.projects = projects;
        prepareSynchronization();
    }

    private void prepareSynchronization() {
        int interval = getSyncIntervalInMillis();

        log.info(String.format("interval: %s", interval));
        if (interval > 0) {
            Task task = RP.create(() -> {
                doSynchronization(true);

                if (synchronizationTask.isPresent() && (interval > 0)) {
                    synchronizationTask.get().schedule(interval);
                }
            });
            synchronizationTask = of(task);
            task.schedule(interval);
        }
    }

    private int getSyncIntervalInMillis() {
        return toMillis(getSyncInterval());
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
            //TODO catch exception when server not available and set error flag
            synchronizeVersion();
            doSynchronization(false);
            prepareSynchronization();
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
        if(synchronizationTask.isPresent()){
            synchronizationTask.get().cancel();
        }
        firePropertyChange(PROP_SYNC_INTERVAL, oldInterval, minutes);
        prepareSynchronization();
    }
}
