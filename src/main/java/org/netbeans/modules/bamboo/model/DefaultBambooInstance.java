package org.netbeans.modules.bamboo.model;

import java.util.prefs.Preferences;


/**
 * @author spindizzy
 */
public final class DefaultBambooInstance implements BambooInstance {
    /** Use serialVersionUID for interoperability. */
    private static final long serialVersionUID = 1L;

    private String name;

    private String url;

    private int sync;

    private BambooInstanceProperties properties;

    public DefaultBambooInstance() {
    }

    public DefaultBambooInstance(final String name, final String url) {
        this.name = name;
        this.url = url;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public int getSyncInterval() {
        return sync;
    }

    public void setSyncInterval(final int sync) {
        this.sync = sync;
    }

    @Override
    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    @Override
    public Preferences getPreferences() {
        return properties.getPreferences();
    }

    public void setProperties(final BambooInstanceProperties properties) {
        this.properties = properties;
        updateFields(properties);
    }

    @Override
    public String getUsername() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getPassword() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void updateFields(final BambooInstanceProperties props) throws NumberFormatException {
        this.name = props.get(BambooInstanceConstants.INSTANCE_NAME);
        this.url = props.get(BambooInstanceConstants.INSTANCE_URL);

        String syncProp = props.get(BambooInstanceConstants.INSTANCE_SYNC);

        if (syncProp != null) {
            this.sync = Integer.parseInt(syncProp);
        }
    }
}
