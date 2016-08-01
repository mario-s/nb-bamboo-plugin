package org.netbeans.modules.bamboo.ui.wizard;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import static org.mockito.BDDMockito.given;

import static org.mockito.Matchers.any;

import org.mockito.Mock;

import static org.mockito.Mockito.verify;

import org.mockito.runners.MockitoJUnitRunner;

import org.netbeans.modules.bamboo.glue.DefaultInstanceValues;
import org.netbeans.modules.bamboo.mock.MockInstanceFactory;
import org.netbeans.modules.bamboo.rest.BambooInstanceProduceable;

import static org.openide.util.Lookup.getDefault;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.netbeans.modules.bamboo.glue.ProjectsProvideable;


/**
 * @author spindizzy
 */
@RunWith(MockitoJUnitRunner.class)
public class RunnerTest {
    @Mock
    private BambooInstanceProduceable producer;
    @Mock
    private ProjectsProvideable instance;
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
        classUnderTest = new Runner(values);
        classUnderTest.addPropertyChangeListener(listener);
    }

    /**
     * Test of run method, of class Runner.
     */
    @Test
    public void testRun_InstanceNotNull() {
        given(producer.create(values)).willReturn(instance);
        classUnderTest.run();
        verify(listener).propertyChange(any(PropertyChangeEvent.class));
    }
}
