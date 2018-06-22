/* 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.netbeans.modules.bamboo.ui.wizard;

import org.netbeans.modules.bamboo.model.rcp.BambooInstance;
import org.netbeans.modules.bamboo.model.rcp.InstanceValues;
import org.netbeans.modules.bamboo.client.glue.BambooInstanceProduceable;

import static org.openide.util.Lookup.getDefault;

import java.beans.PropertyChangeSupport;
import java.util.Optional;
import lombok.extern.java.Log;


/**
 * @author Mario Schroeder
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
