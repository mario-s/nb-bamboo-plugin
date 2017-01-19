package org.netbeans.modules.bamboo.model.rcp;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.netbeans.api.annotations.common.NonNull;

/**
 *
 * @author Mario Schroeder
 */
@EqualsAndHashCode(of = "key")
public abstract class AbstractVo {
    
    @Getter
    private final String key;
    
    private final PropertyChangeSupport changeSupport;

    public AbstractVo(@NonNull String key) {
        this.key = key;
        this.changeSupport = new PropertyChangeSupport(this);
    }
    
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }
    
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }

    public void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        changeSupport.firePropertyChange(propertyName, oldValue, newValue);
    }     
}
