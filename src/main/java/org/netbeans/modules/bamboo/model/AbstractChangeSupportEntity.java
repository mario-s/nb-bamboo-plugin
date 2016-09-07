package org.netbeans.modules.bamboo.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 *
 * @author spindizzy
 */
public class AbstractChangeSupportEntity {
    
    private final PropertyChangeSupport changeSupport;

    public AbstractChangeSupportEntity() {
        this.changeSupport = new PropertyChangeSupport(this);
    }
    
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }
    
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
}
