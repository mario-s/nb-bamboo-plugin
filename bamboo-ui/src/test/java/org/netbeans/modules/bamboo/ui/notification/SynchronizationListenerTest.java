package org.netbeans.modules.bamboo.ui.notification;

import java.beans.PropertyChangeEvent;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.modules.bamboo.model.rcp.BambooInstance;
import org.netbeans.modules.bamboo.model.rcp.ModelChangedValues;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.internal.util.reflection.Whitebox.getInternalState;

/**
 *
 * @author spindizzy
 */
@RunWith(MockitoJUnitRunner.class)
public class SynchronizationListenerTest {
    @Mock
    private BambooInstance instance;
    
    private SynchronizationListener classUnderTest;
    
    @Before
    public void setUp() {
        classUnderTest = new SynchronizationListener(instance);
    }

    /**
     * Test of propertyChange method, of class SynchronizationListener.
     */
    @Test
    public void testPropertyChange_StartSynchronize() {
        PropertyChangeEvent event = newChangeEvent(true);
        classUnderTest.propertyChange(event);
        Optional<ProgressHandle> opt = getProgressHandle();
        assertThat(opt.isPresent(), is(true));
    }

    private PropertyChangeEvent newChangeEvent(boolean val) {
        return new PropertyChangeEvent(this, ModelChangedValues.Synchronizing.toString(), !val, val);
    }
    
      /**
     * Test of propertyChange method, of class SynchronizationListener.
     */
    @Test
    public void testPropertyChange_StartAndStopSynchronize() {
        PropertyChangeEvent event = newChangeEvent(true);
        classUnderTest.propertyChange(event);
        event = newChangeEvent(false);
        classUnderTest.propertyChange(event);
        Optional<ProgressHandle> opt = getProgressHandle();
        assertThat(opt.isPresent(), is(false));
    }

    private Optional<ProgressHandle> getProgressHandle() {
        return (Optional<ProgressHandle>) getInternalState(classUnderTest, "progressHandle");
    }
    
}
