package org.netbeans.modules.bamboo.ui.notification;

import static org.hamcrest.CoreMatchers.is;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import org.junit.runner.RunWith;

import static org.mockito.BDDMockito.given;

import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.netbeans.modules.bamboo.model.rcp.BambooInstance;
import org.openide.util.Lookup;

/**
 *
 * @author spindizzy
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultBuildStatusWatcherTest {
    
    private DefaultBuildStatusWatcher classUnderTest;
    
    @Mock
    private BambooInstance instance;
    
    @Before
    public void setUp() {
        classUnderTest = new DefaultBuildStatusWatcher();
        
        given(instance.getLookup()).willReturn(Lookup.getDefault());
    }

    /**
     * Test of addInstance method, of class DefaultBuildStatusWatcher.
     */
    @Test
    public void testAddInstance_ExpectMapNotEmpty() {
        classUnderTest.addInstance(instance);
        assertThat(classUnderTest.getNotifiers().isEmpty(), is(false));
    }

    /**
     * Test of removeInstance method, of class DefaultBuildStatusWatcher.
     */
    @Test
    public void testRemoveInstance_ExpectMapEmpty() {
        classUnderTest.addInstance(instance);
        classUnderTest.removeInstance(instance);
        assertThat(classUnderTest.getNotifiers().isEmpty(), is(true));
    }
    
}
