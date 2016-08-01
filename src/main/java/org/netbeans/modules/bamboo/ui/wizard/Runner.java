package org.netbeans.modules.bamboo.ui.wizard;

import org.netbeans.modules.bamboo.glue.BambooInstance;
import org.netbeans.modules.bamboo.glue.InstanceValues;
import org.netbeans.modules.bamboo.rest.BambooInstanceProduceable;

import static org.openide.util.Lookup.getDefault;

import java.beans.PropertyChangeSupport;

import java.util.logging.Logger;


/**
 * @author spindizzy
 */
class Runner extends PropertyChangeSupport implements Runnable {
    /** Use serialVersionUID for interoperability. */
    private static final long serialVersionUID = 1L;
    private final Logger log;

    private final InstanceValues values;

    Runner(final InstanceValues values) {
        super(values);
        this.log = Logger.getLogger(getClass().getName());
        this.values = values;
    }

    @Override
    public void run() {
        //TODO add a check if server exists

        BambooInstanceProduceable producer = getDefault().lookup(BambooInstanceProduceable.class);
        BambooInstance instance = producer.create(values);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            log.info(ex.getMessage());
        }

        if ((instance != null) && !Thread.interrupted()) {
            firePropertyChange(WorkerEvents.INSTANCE_CREATED.name(), null, instance);
        }
    }
}
