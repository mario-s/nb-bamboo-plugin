package org.netbeans.modules.bamboo.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import org.netbeans.modules.bamboo.glue.LookupContext;
import org.openide.util.Lookup;
import org.openide.util.lookup.InstanceContent;

/**
 *
 * @author spindizzy
 */
public abstract class AbstractVo implements Lookup.Provider{
    
    private final LookupContext lookupContext;
    
    private String key;
    
    private final PropertyChangeSupport changeSupport;

    public AbstractVo() {
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public Lookup getLookup() {
        return lookupContext.getLookup();
    }
    
    protected InstanceContent getContent() {
        return lookupContext.getContent();
    }
}
