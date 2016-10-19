package org.netbeans.modules.bamboo.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import org.openide.util.Lookup;
import org.openide.util.lookup.InstanceContent;

/**
 *
 * @author spindizzy
 */
@EqualsAndHashCode(of = "key")
public abstract class AbstractVo implements Lookup.Provider{
    
    private final LookupContext lookupContext;
    
    @Getter
    private final String key;
    
    private final PropertyChangeSupport changeSupport;

    public AbstractVo(@NonNull String key) {
        this.key = key;
        this.changeSupport = new PropertyChangeSupport(this);
        lookupContext = LookupContext.Instance;
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

    @Override
    public Lookup getLookup() {
        return lookupContext.getLookup();
    }
    
    protected InstanceContent getContent() {
        return lookupContext.getContent();
    }   
}
