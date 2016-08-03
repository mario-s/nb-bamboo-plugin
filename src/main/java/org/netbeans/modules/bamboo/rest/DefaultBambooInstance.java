package org.netbeans.modules.bamboo.rest;

import lombok.Getter;
import lombok.Setter;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import org.netbeans.modules.bamboo.glue.BuildProject;
import org.netbeans.modules.bamboo.glue.DefaultInstanceValues;
import org.netbeans.modules.bamboo.glue.InstanceValues;
import org.netbeans.modules.bamboo.glue.ProjectsProvideable;

import org.openide.util.RequestProcessor;
import org.openide.util.RequestProcessor.Task;

import java.util.Collection;
import java.util.Optional;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import java.util.prefs.Preferences;


/**
 * @author spindizzy
 */
@Getter
@Setter
public class DefaultBambooInstance extends DefaultInstanceValues implements ProjectsProvideable {
    /** Use serialVersionUID for interoperability. */
    private static final long serialVersionUID = 1L;
    private static final RequestProcessor RP = new RequestProcessor(DefaultBambooInstance.class);

    private Optional<Task> synchronizationTask = empty();

    private Collection<BuildProject> projects;

    private BambooInstanceProperties properties;

    public DefaultBambooInstance() {
    }

    public DefaultBambooInstance(final InstanceValues values) {
        super(values);
        prepareSynchronization();
    }

    @Override
    public Preferences getPreferences() {
        return properties.getPreferences();
    }

    @Override
    public void remove() {
        if (properties != null) {
            properties.clear();
        }
    }

    @Override
    public void synchronize() {
        RP.post(() -> { doSynchronization(); });
    }

    private void doSynchronization() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void applyProperties(final BambooInstanceProperties properties) {
        this.properties = properties;
        copyProperties(properties);
        prepareSynchronization();
    }

    private void prepareSynchronization() {
        int interval = getSyncInterval(); //TODO convert to minutes

        if (interval > 0) {
            Task task = RP.create(() -> { doSynchronization(); });
            task.schedule(interval);
            synchronizationTask = of(task);
        }
    }

    private void copyProperties(final BambooInstanceProperties props) throws NumberFormatException {
        setName(props.get(BambooInstanceConstants.INSTANCE_NAME));
        setUrl(props.get(BambooInstanceConstants.INSTANCE_URL));
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
}
