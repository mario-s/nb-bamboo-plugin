package org.netbeans.modules.bamboo.ui.wizard;

import org.netbeans.modules.bamboo.glue.BambooInstance;
import org.netbeans.modules.bamboo.glue.InstanceValues;
import org.netbeans.modules.bamboo.glue.BambooInstanceProduceable;

import static org.openide.util.Lookup.getDefault;

import java.beans.PropertyChangeSupport;
import java.util.Optional;
import lombok.extern.java.Log;


/**
 * @author spindizzy
 */
@Log
class Runner extends PropertyChangeSupport implements Runnable {
    /** Use serialVersionUID for interoperability. */
    private static final long serialVersionUID = 1L;

    private final transient InstanceValues values;

    Runner(final InstanceValues values) {
        super(values);
        this.values = values;
    }

    @Override
    public void run() {
        BambooInstanceProduceable producer = getDefault().lookup(
                    BambooInstanceProduceable.class);
        Optional<BambooInstance> instance = producer.create(values);
        if (instance.isPresent()) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                log.info(ex.getMessage());
            }

            if (!Thread.interrupted()) {
                firePropertyChange(WorkerEvents.INSTANCE_CREATED.name(), null, instance.get());
            }
        } else {
            firePropertyChange(WorkerEvents.INVALID_URL.name(), null, values.getUrl());
        }
    }
}
