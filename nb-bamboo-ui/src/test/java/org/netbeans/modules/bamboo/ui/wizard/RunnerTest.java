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

import org.mockito.InOrder;
import org.mockito.Mock;
import org.netbeans.modules.bamboo.model.rcp.DefaultInstanceValues;
import org.netbeans.modules.bamboo.mock.MockInstanceFactory;
import org.netbeans.modules.bamboo.client.glue.BambooInstanceProduceable;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.netbeans.modules.bamboo.model.rcp.BambooInstance;

import static java.util.Optional.empty;
import static java.util.Optional.of;

import static org.openide.util.Lookup.getDefault;

import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;

/**
 * @author Mario Schroeder
 */
@ExtendWith(MockitoExtension.class)
class RunnerTest {
    private static final String FOO = "foo";
    @Mock
    private BambooInstanceProduceable producer;
    @Mock
    private BambooInstance instance;
    @Mock
    private PropertyChangeListener listener;

    private DefaultInstanceValues values;

    private Runner classUnderTest;

    @BeforeEach
    void setUp() {
        MockInstanceFactory factory =
            (MockInstanceFactory) getDefault().lookup(BambooInstanceProduceable.class);
        factory.setDelegate(producer);

        values = new DefaultInstanceValues();
        values.setUrl(FOO);

        classUnderTest = new Runner(values);
        classUnderTest.addPropertyChangeListener(listener);
    }

    /**
     * Test of run method, of class Runner.
     */
    @Test
    void testRun_ServerExists() {
        given(producer.create(values)).willReturn(of(instance));
        classUnderTest.run();

        InOrder order = inOrder(producer, listener);
        order.verify(producer).create(any(DefaultInstanceValues.class));
        order.verify(listener).propertyChange(any(PropertyChangeEvent.class));
    }

    /**
     * Test of run method, of class Runner.
     */
    @Test
    void testRun_ServerDoesNotExists() {
        given(producer.create(values)).willReturn(empty());
        classUnderTest.run();
        verify(listener).propertyChange(any(PropertyChangeEvent.class));
    }
}
