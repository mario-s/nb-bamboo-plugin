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
        instance.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName();
        if (ChangeEvents.Synchronizing.toString().equals(propertyName)) {
            boolean progress = (boolean) evt.getNewValue();
            progressHandle = getProgressHandle();
            if(progress){
                progressHandle.start();
            }else{
                progressHandle.finish();
                progressHandle = null;
            }
        }
    }

    @NbBundle.Messages({"TXT_SYNC=Synchronizing"})
    private ProgressHandle getProgressHandle() {
        if(progressHandle == null){
            progressHandle = ProgressHandleFactory.createHandle(TXT_SYNC() + " " + getName());
        }
        return progressHandle;
    }

    private String getName() {
        return instance.getName();
    }

}
