package org.netbeans.modules.bamboo.ui.wizard;

import org.netbeans.modules.bamboo.glue.BambooInstance;
import org.netbeans.modules.bamboo.glue.HttpUtility;
import org.netbeans.modules.bamboo.glue.InstanceValues;
import org.netbeans.modules.bamboo.rest.BambooInstanceProduceable;

import static org.openide.util.Lookup.getDefault;

import java.beans.PropertyChangeSupport;
import lombok.extern.java.Log;


/**
 * @author spindizzy
 */
@Log
class Runner extends PropertyChangeSupport implements Runnable {
    /** Use serialVersionUID for interoperability. */
    private static final long serialVersionUID = 1L;
    private final HttpUtility httpUtility;

    private final InstanceValues values;

    Runner(final InstanceValues values) {
        this(values, new HttpUtility());
    }

    Runner(final InstanceValues values, final HttpUtility httpUtility) {
        super(values);
        this.httpUtility = httpUtility;
        this.values = values;
    }

    @Override
    public void run() {
        if (httpUtility.exists(values.getUrl())) {
            BambooInstanceProduceable producer = getDefault().lookup(
                    BambooInstanceProduceable.class);
            BambooInstance instance = producer.create(values);

            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                log.info(ex.getMessage());
            }

            if ((instance != null) && !Thread.interrupted()) {
                firePropertyChange(WorkerEvents.INSTANCE_CREATED.name(), null, instance);
            }
        } else {
            firePropertyChange(WorkerEvents.INVALID_URL.name(), null, values.getUrl());
        }
    }
}
