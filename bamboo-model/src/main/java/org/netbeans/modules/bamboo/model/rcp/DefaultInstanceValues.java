package org.netbeans.modules.bamboo.model.rcp;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import static java.util.Arrays.copyOf;

/**
 * A wrapper for all parameters to be used to create a new BambooInstance.
 *
 * @author Mario Schroeder
 */
@Data
@EqualsAndHashCode(exclude = "changeSupport")
public class DefaultInstanceValues implements InstanceValues {

    @Getter(AccessLevel.NONE)
    private final PropertyChangeSupport changeSupport;

    private String name;

    private String url;

    private int syncInterval;

    private String username;

    private char[] password;

    public DefaultInstanceValues() {
        this(null);
    }

    public DefaultInstanceValues(final InstanceValues other) {
        if (other != null) {
            this.name = other.getName();
            this.url = other.getUrl();
            this.syncInterval = other.getSyncInterval();
            this.username = other.getUsername();
            this.password = other.getPassword();
        }

        this.changeSupport = new PropertyChangeSupport(this);
    }

    @Override
    public char[] getPassword() {
        return clonePassword(password);
    }

    public void setPassword(char[] password) {
        this.password = clonePassword(password);
    }

    private char[] clonePassword(char[] passwd) {
        return (passwd != null) ? copyOf(passwd, passwd.length) : new char[0];
    }
    
    protected final int getSyncIntervalInMillis() {
        return toMillis(syncInterval);
    }

    private int toMillis(final int minutes) {
        return minutes * 60000;
    }
    
    @Override
    public void addPropertyChangeListener(final PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }
    
    @Override
    public void removePropertyChangeListener(
            final PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }

    protected final void firePropertyChange(final String propertyName,
            final Object oldValue,
            final Object newValue) {
        changeSupport.firePropertyChange(propertyName, oldValue, newValue);
    }
}
