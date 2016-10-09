package org.netbeans.modules.bamboo.ui.wizard;

import org.netbeans.modules.bamboo.rest.HttpUtility;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import static org.mockito.BDDMockito.given;

import org.mockito.InOrder;

import static org.mockito.Matchers.any;

import org.mockito.Mock;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;

import org.mockito.runners.MockitoJUnitRunner;

import org.netbeans.modules.bamboo.model.DefaultInstanceValues;
import org.netbeans.modules.bamboo.mock.MockInstanceFactory;
import org.netbeans.modules.bamboo.glue.BambooInstanceProduceable;

import static org.openide.util.Lookup.getDefault;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.netbeans.modules.bamboo.glue.BambooInstance;

import static java.util.Optional.empty;
import static java.util.Optional.of;


/**
 * @author spindizzy
 */
@RunWith(MockitoJUnitRunner.class)
public class RunnerTest {
    private static final String FOO = "foo";
    @Mock
    private BambooInstanceProduceable producer;
    @Mock
    private BambooInstance instance;
    @Mock
    private PropertyChangeListener listener;

    private DefaultInstanceValues values;

    private Runner classUnderTest;

    @Before
    public void setUp() {
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
    public void testRun_ServerExists() {
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
    public void testRun_ServerDoesNotExists() {
        given(producer.create(values)).willReturn(empty());
        classUnderTest.run();
        verify(listener).propertyChange(any(PropertyChangeEvent.class));
    }
}
