package org.netbeans.modules.bamboo.rest;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import org.netbeans.modules.bamboo.glue.BuildProject;
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
import static java.util.Optional.empty;
import static java.util.Optional.of;
import java.util.Random;
import java.util.prefs.Preferences;
import org.netbeans.modules.bamboo.glue.SharedConstants;


/**
 * @author spindizzy
 */
public class DefaultBambooInstance extends DefaultInstanceValues implements ProjectsProvideable {
    
    /** Use serialVersionUID for interoperability. */
    private static final long serialVersionUID = 1L;
    private static final RequestProcessor RP = new RequestProcessor(DefaultBambooInstance.class);

    private final PropertyChangeSupport changeSupport;

    private final BambooServiceAccessable client;

    private Optional<Task> synchronizationTask = empty();

    private Collection<BuildProject> projects;

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
        setName(props.get(SharedConstants.INSTANCE_NAME));
        setUrl(props.get(SharedConstants.INSTANCE_URL));
        setUsername(props.get(BambooInstanceConstants.INSTANCE_USER));

        String passwd = props.get(BambooInstanceConstants.INSTANCE_PASSWORD);

        if (isNotBlank(passwd)) {
            setPassword(passwd.toCharArray());
        }

        String syncProp = props.get(BambooInstanceConstants.INSTANCE_SYNC);

        if (isNotBlank(syncProp)) {
            setSyncInterval(Integer.parseInt(syncProp));
        }
    }

    private synchronized void doSynchronization() {
        Collection<BuildProject> oldProjects = this.projects;
        Collection<BuildProject> newProjects = client.getProjects(this);
        
        this.projects = newProjects;
        firePropertyChange(PROJECTS, oldProjects, newProjects);
    }

    @Override
    public Preferences getPreferences() {
        return properties.getPreferences();
    }

    @Override
    public Collection<BuildProject> getProjects() {
        return projects;
    }

    Optional<Task> getSynchronizationTask() {
        return synchronizationTask;
    }

    @Override
    public void setProjects(final Collection<BuildProject> projects) {
        this.projects = projects;
        prepareSynchronization();
    }

    private void prepareSynchronization() {
        int interval = getSyncIntervalInMillis();

        if (interval > 0) {
            Task task = RP.create(() -> {
                        doSynchronization();

                        if (synchronizationTask.isPresent() && (interval > 0)) {
                            synchronizationTask.get().schedule(interval);
                        }
                    });
            synchronizationTask = of(task);
            task.schedule(toMillis(interval));
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
        return RP.post(() -> { doSynchronization(); });
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
