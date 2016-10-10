package org.netbeans.modules.bamboo.ui.notification;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.netbeans.api.annotations.common.NonNull;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.progress.ProgressHandleFactory;
import org.netbeans.modules.bamboo.glue.BambooInstance;
import org.netbeans.modules.bamboo.model.ChangeEvents;
import static org.netbeans.modules.bamboo.ui.notification.Bundle.TXT_SYNC;
import org.openide.util.NbBundle;

/**
 * This calss listenes for changes on the sychronization.
 *
 * @author spindizzy
 */
class SynchronizationListener implements PropertyChangeListener {

    private final BambooInstance instance;

    private ProgressHandle progressHandle;

    SynchronizationListener(@NonNull BambooInstance instance) {
        this.instance = instance;
        progressHandle = createProgressHandle();
        instance.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName();
        if (ChangeEvents.Synchronizing.toString().equals(propertyName)) {
            boolean progress = (boolean) evt.getNewValue();
            if(progress){
                progressHandle.start();
            }else{
                progressHandle.finish();
            }
        }
    }

    @NbBundle.Messages({"TXT_SYNC=Synchronizing"})
    private ProgressHandle createProgressHandle() {
        return ProgressHandleFactory.createHandle(TXT_SYNC() + " " + getName());
    }

    private String getName() {
        return instance.getName();
    }

}
