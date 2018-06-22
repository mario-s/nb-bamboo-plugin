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

import java.beans.PropertyChangeListener;
import java.util.List;
import javax.swing.Action;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.PropertySupport;
import org.openide.util.Lookup;

import static org.openide.util.Utilities.actionsForPath;

/**
 * This is a parant class for all nodes which a children of a bamboo instance.
 *
 * @author Mario Schroeder
 */
abstract class AbstractInstanceChildNode extends AbstractNode implements PropertyChangeListener {

    private AbstractRefreshChildFactory factory;

    AbstractInstanceChildNode(Lookup lookup) {
        super(Children.LEAF, lookup);
    }

    AbstractInstanceChildNode(AbstractRefreshChildFactory factory, Lookup lookup) {
        super(Children.create(factory, true), lookup);
        this.factory = factory;
    }

    void refreshChildren() {
        factory.refreshNodes();
    }

    protected List<? extends Action> findActions(String path) {
        return actionsForPath(path);
    }

    /**
     * Abstract parent for read only support of strings.
     */
    abstract class StringReadPropertySupport extends PropertySupport.ReadOnly<String> {

        public StringReadPropertySupport(String name, String displayName, String shortDescription) {
            super(name, String.class, displayName, shortDescription);
        }
    }

    /**
     * Abstract parent for read only support of integers.
     */
    abstract class IntReadPropertySupport extends PropertySupport.ReadOnly<Integer> {

        public IntReadPropertySupport(String name, String displayName, String shortDescription) {
            super(name, Integer.class, displayName, shortDescription);
        }

    }

    /**
     * Abstract parent for read only support of longs.
     */
    abstract class LongReadPropertySupport extends PropertySupport.ReadOnly<Long> {

        public LongReadPropertySupport(String name, String displayName, String shortDescription) {
            super(name, Long.class, displayName, shortDescription);
        }
    }

    /**
     * Abstract parent for read and write support of integers.
     */
    abstract class IntReadWritePropertySupport extends PropertySupport.ReadWrite<Integer> {

        public IntReadWritePropertySupport(String name, String displayName, String shortDescription) {
            super(name, Integer.class, displayName, shortDescription);
        }

    }

    /**
     * Abstract parent for read and write support of boolean.
     */
    abstract class BooleanReadWritePropertySupport extends PropertySupport.ReadWrite<Boolean> {

        public BooleanReadWritePropertySupport(String name, String displayName, String shortDescription) {
            super(name, Boolean.class, displayName, shortDescription);
        }

    }
}
