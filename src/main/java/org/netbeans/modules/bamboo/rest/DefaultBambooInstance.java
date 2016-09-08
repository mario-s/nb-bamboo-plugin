package org.netbeans.modules.bamboo.rest;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import org.netbeans.modules.bamboo.model.rest.Project;
import org.netbeans.modules.bamboo.glue.DefaultInstanceValues;
import org.netbeans.modules.bamboo.glue.InstanceValues;
import org.netbeans.modules.bamboo.glue.ProjectsProvideable;
import org.netbeans.modules.bamboo.glue.VersionInfo;

import static org.openide.util.Lookup.getDefault;
import org.openide.util.RequestProcessor;
import org.openide.util.RequestProcessor.Task;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import java.util.Collection;
import java.util.Optional;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import org.apache.commons.lang3.time.StopWatch;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.progress.ProgressHandleFactory;
import org.netbeans.modules.bamboo.glue.SharedConstants;
import org.openide.util.NbBundle.Messages;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.netbeans.modules.bamboo.rest.Bundle.TXT_SYNC;

/**
 * @author spindizzy
 */
public class DefaultBambooInstance extends DefaultInstanceValues implements ProjectsProvideable {

    private static final Logger LOG = Logger.getLogger(DefaultBambooInstance.class.getName());

    /**
     * Use serialVersionUID for interoperability.
     */
    private static final long serialVersionUID = 1L;
    private static final RequestProcessor RP = new RequestProcessor(DefaultBambooInstance.class);

    private final StopWatch stopWatch = new StopWatch();

    private final PropertyChangeSupport changeSupport;

    private final BambooServiceAccessable client;

    private Optional<Task> synchronizationTask = empty();

    private Collection<Project> projects;

    private BambooInstanceProperties properties;

    private VersionInfo version;

    public DefaultBambooInstance() {
        this(null);
    }

    public DefaultBambooInstance(final InstanceValues values) {
        super(values);
        changeSupport = new PropertyChangeSupport(this);
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

    private void copyProperties(final BambooInstanceProperties props) throws NumberFormatException {
        setName(props.get(SharedConstants.PROP_NAME));
        setUrl(props.get(SharedConstants.PROP_URL));
        setUsername(props.get(BambooInstanceConstants.INSTANCE_USER));

        String passwd = props.get(BambooInstanceConstants.INSTANCE_PASSWORD);

        if (isNotBlank(passwd)) {
            setPassword(passwd.toCharArray());
        }

        String syncProp = props.get(SharedConstants.PROP_SYNC_INTERVAL);

        if (isNotBlank(syncProp)) {
            setSyncInterval(Integer.parseInt(syncProp));
        }
    }

    private void doSynchronization(boolean showProgress) {
        if (LOG.isLoggable(Level.INFO)) {
            stopWatch.start();
        }
        
        ProgressHandle progressHandle = null;
        if (showProgress) {
            progressHandle = createProgressHandle();
            progressHandle.start();
        }

        Collection<Project> oldProjects = this.projects;
        Collection<Project> newProjects = client.getProjects(this);

        this.projects = newProjects;
        firePropertyChange(PROJECTS, oldProjects, newProjects);

        if (progressHandle != null) {
            progressHandle.finish();
        }

        if (LOG.isLoggable(Level.INFO)) {
            stopWatch.stop();
            LOG.info(String.format("synchronized %s in %s", getName(), stopWatch));
            stopWatch.reset();
        }
        
        synchronized (this) {
            notifyAll();
        }
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
    public Collection<Project> getProjects() {
        return projects;
    }

    Optional<Task> getSynchronizationTask() {
        return synchronizationTask;
    }

    @Override
    public void setProjects(final Collection<Project> projects) {
        this.projects = projects;
        prepareSynchronization();
    }

    private void prepareSynchronization() {
        int interval = getSyncIntervalInMillis();

        LOG.info(String.format("interval: %s", interval));
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
    public void removePropertyChangeListener(final PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }

    @Override
    public Task synchronize() {
        return RP.post(() -> {
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
}
