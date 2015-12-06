package org.netbeans.modules.bamboo.model;

import org.netbeans.modules.bamboo.BambooInstanceConstants;
import java.util.prefs.Preferences;

/**
 *
 * @author spindizzy
 */
public final class DefaultBambooInstance implements BambooInstance {

    private String name;

    private String url;

    private BambooInstanceProperties properties;

    public DefaultBambooInstance() {
    }

    public DefaultBambooInstance(String name, String url) {
        this.name = name;
        this.url = url;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public Preferences getPreferences() {
        return properties.getPreferences();
    }

    public void setProperties(BambooInstanceProperties properties) {
        this.properties = properties;
        this.name = properties.get(BambooInstanceConstants.INSTANCE_NAME);
        this.url = properties.get(BambooInstanceConstants.INSTANCE_URL);
    }

}