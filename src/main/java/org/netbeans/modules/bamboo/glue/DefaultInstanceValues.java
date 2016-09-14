package org.netbeans.modules.bamboo.glue;

import java.util.Arrays;
import lombok.Getter;
import lombok.Setter;

/**
 * A wrapper for all parameters to be used to create a new BambooInstance.
 *
 * @author spindizzy
 */
@Getter
@Setter
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
        return (passwd != null) ? Arrays.copyOf(passwd, passwd.length) : null;
    }
    
}
