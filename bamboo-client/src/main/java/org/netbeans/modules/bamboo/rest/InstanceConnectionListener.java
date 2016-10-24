package org.netbeans.modules.bamboo.rest;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.netbeans.modules.bamboo.model.BambooInstance;
import org.netbeans.modules.bamboo.model.LookupContext;
import org.netbeans.modules.bamboo.model.ModelChangedValues;
import org.netbeans.modules.bamboo.model.event.ServerConnectionEvent;

/**
 * Listener when the connection to an instance changes.
 * It fires that a event using the lookup.
 *
 * @author spindizzy
 */
class InstanceConnectionListener implements PropertyChangeListener {

    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        String propName = pce.getPropertyName();
        if (ModelChangedValues.Available.toString().equals(propName)) {
            final BambooInstance source = (BambooInstance) pce.getSource();
            ServerConnectionEvent event = new ServerConnectionEvent(source);
            LookupContext.Instance.add(event);
        }
    }
}
