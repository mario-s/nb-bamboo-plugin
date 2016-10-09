package org.netbeans.modules.bamboo.model;

import java.util.Arrays;
import lombok.Data;
import org.netbeans.modules.bamboo.glue.InstanceValues;

/**
 * A wrapper for all parameters to be used to create a new BambooInstance.
 *
 * @author spindizzy
 */
@Data
public class DefaultInstanceValues implements InstanceValues {
    private String name;

    private String url;

    private int syncInterval;

    private String username;

    private char[] password;

    public DefaultInstanceValues() {
    }

    public DefaultInstanceValues(final InstanceValues other) {
        if (other != null) {
            this.name = other.getName();
            this.url = other.getUrl();
            this.syncInterval = other.getSyncInterval();
            this.username = other.getUsername();
            this.password = other.getPassword();
        }
    }

    @Override
    public char[] getPassword() {
        return clonePassword(password);
    }

    public void setPassword(char[] password) {
        this.password = clonePassword(password);
    }

    private char[] clonePassword(char[] passwd) {
        return (passwd != null) ? Arrays.copyOf(passwd, passwd.length) : new char[0];
    }
    
}
