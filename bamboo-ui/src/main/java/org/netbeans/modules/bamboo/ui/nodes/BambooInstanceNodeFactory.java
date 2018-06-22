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
package org.netbeans.modules.bamboo.ui.nodes;

import java.io.Serializable;
import java.util.Collection;
import org.netbeans.modules.bamboo.model.rcp.BambooInstance;

import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;

import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;

import static java.util.Collections.sort;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import lombok.extern.java.Log;

import static java.util.Optional.empty;
import static java.util.Optional.of;

/**
 * This is a factory for a {@link BambooInstanceNode}.
 *
 * @author Mario Schroeder
 */
@Log
class BambooInstanceNodeFactory extends ChildFactory<BambooInstance>
        implements LookupListener {

    private static final BambooInstanceComparator COMPARATOR = new BambooInstanceComparator();

    private final Lookup lookup;

    private Lookup.Result<BambooInstance> instanceResult;

    private volatile Optional<CountDownLatch> blocker;

    public BambooInstanceNodeFactory(Lookup lookup) {
        this.blocker = empty();
        this.lookup = lookup;
        lookupResult();
    }

    private void updateBlocker() {
        Collection<? extends BambooInstance> instances = instanceResult.allInstances();
        if (!instances.isEmpty()) {
            blocker.ifPresent(c -> c.countDown());
        }
    }

    private void lookupResult() {
        instanceResult = lookup.lookupResult(BambooInstance.class);
        instanceResult.addLookupListener(this);
    }

    @Override
    protected Node createNodeForKey(final BambooInstance key) {
        return new BambooInstanceNode(key);
    }

    @Override
    protected boolean createKeys(final List<BambooInstance> toPopulate) {

        updateBlocker();

        blocker.ifPresent(c -> {
            try {
                c.await();
            } catch (InterruptedException ex) {
                log.info(ex.getMessage());
            }
        });

        toPopulate.addAll(instanceResult.allInstances());
        sort(toPopulate, COMPARATOR);

        blocker = empty();

        return true;
    }

    @Override
    public void resultChanged(final LookupEvent ev) {
        updateBlocker();

        refresh(false);
    }
    
    void block() {
        this.blocker = of(new CountDownLatch(1));
    }

    private static class BambooInstanceComparator implements Comparator<BambooInstance>, Serializable {

        private static final long serialVersionUID = 1L;

        @Override
        public int compare(final BambooInstance o1, final BambooInstance o2) {
            int val = 0;

            final String left = o1.getName();
            final String right = o2.getName();

            if (left != null && right != null) {
                val = left.compareToIgnoreCase(right);
            }
            return val;
        }
    }
}
