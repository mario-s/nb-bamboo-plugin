package org.netbeans.modules.bamboo.client.rest;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.netbeans.modules.bamboo.model.rcp.BambooInstance;
import org.netbeans.modules.bamboo.LookupContext;
import org.netbeans.modules.bamboo.model.rcp.ModelChangedValues;
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
            String name = source.getName();
            boolean available = source.isAvailable();
            ServerConnectionEvent event = new ServerConnectionEvent(name, available);
            //TODO per instance
            LookupContext.Instance.add(event);
        }
    }
}
