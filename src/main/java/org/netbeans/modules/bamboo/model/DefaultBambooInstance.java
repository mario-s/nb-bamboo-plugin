package org.netbeans.modules.bamboo.model;

import org.netbeans.modules.bamboo.glue.BambooInstance;
import org.netbeans.modules.bamboo.glue.DefaultInstanceValues;
import org.netbeans.modules.bamboo.glue.InstanceValues;

import java.util.prefs.Preferences;


/**
 * @author spindizzy
 */
public final class DefaultBambooInstance extends DefaultInstanceValues implements BambooInstance {
    /** Use serialVersionUID for interoperability. */
    private static final long serialVersionUID = 1L;

    private int sync;

    private BambooInstanceProperties properties;

    public DefaultBambooInstance() {
    }

    public DefaultBambooInstance(final InstanceValues values) {
        super(values);
    }

    @Override
    public Preferences getPreferences() {
        return properties.getPreferences();
    }

    public void setProperties(final BambooInstanceProperties properties) {
        this.properties = properties;
        updateFields(properties);
    }

    private void updateFields(final BambooInstanceProperties props) throws NumberFormatException {
        setName(props.get(BambooInstanceConstants.INSTANCE_NAME));
        setUrl(props.get(BambooInstanceConstants.INSTANCE_URL));

        String syncProp = props.get(BambooInstanceConstants.INSTANCE_SYNC);

        if (syncProp != null) {
            this.sync = Integer.parseInt(syncProp);
        }
    }
}
