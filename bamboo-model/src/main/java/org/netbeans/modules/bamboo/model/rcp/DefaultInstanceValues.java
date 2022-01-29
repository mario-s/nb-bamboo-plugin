/* 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.netbeans.modules.bamboo.model.rcp;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Arrays;

import static java.util.Arrays.copyOf;
import java.util.Objects;

/**
 * A wrapper for all parameters to be used to create a new BambooInstance.
 *
 * @author Mario Schroeder
 */
public class DefaultInstanceValues implements InstanceValues {

    private final PropertyChangeSupport changeSupport;

    private String name;

    private String url;

    private int syncInterval;
    
    @Deprecated
    private boolean useToken;

    @Deprecated
    private String username;

    @Deprecated
    private char[] password;
    
    private char[] token;

    public DefaultInstanceValues() {
        this(null);
    }

    public DefaultInstanceValues(final InstanceValues other) {
        useToken = true;
        
        if (other != null) {
            this.name = other.getName();
            this.url = other.getUrl();
            this.syncInterval = other.getSyncInterval();
            this.username = other.getUsername();
            this.password = other.getPassword();
            this.token = other.getToken();
            this.useToken = other.isUseToken();
        }

        this.changeSupport = new PropertyChangeSupport(this);
    }
    
    @Deprecated
    @Override
    public boolean isUseToken() {
        return useToken;
    }
    
    @Deprecated
    @Override
    public char[] getPassword() {
        return copyChars(password);
    }

    @Deprecated
    public void setPassword(char[] password) {
        this.password = copyChars(password);
    }
    
    @Override
    public char[] getToken() {
        return copyChars(token);
    }

    public void setToken(char[] token) {
        this.token = copyChars(token);
    }

    private char[] copyChars(char[] chars) {
        return (chars != null) ? copyOf(chars, chars.length) : new char[0];
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

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getSyncInterval() {
        return syncInterval;
    }

    public void setSyncInterval(int syncInterval) {
        this.syncInterval = syncInterval;
    }

    @Deprecated
    @Override
    public String getUsername() {
        return username;
    }

    @Deprecated
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + Objects.hash(name, url, username, useToken);
        hash = 83 * hash + this.syncInterval;
        hash = 83 * hash + Arrays.hashCode(this.password);
        hash = 83 * hash + Arrays.hashCode(this.token);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DefaultInstanceValues other = (DefaultInstanceValues) obj;
        if (this.useToken != other.useToken) {
            return false;
        }
        if (this.syncInterval != other.syncInterval) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.url, other.url)) {
            return false;
        }
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        if (!Arrays.equals(this.password, other.password)) {
            return false;
        }
        return Arrays.equals(this.token, other.token);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" + "name=" + name + '}';
    }
}
